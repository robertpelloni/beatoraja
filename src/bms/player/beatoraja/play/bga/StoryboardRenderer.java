package bms.player.beatoraja.play.bga;

import bms.model.storyboard.*;
import bms.player.beatoraja.PixmapResourcePool;
import bms.player.beatoraja.skin.Skin.SkinObjectRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Color;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class StoryboardRenderer {

    private final StoryboardData data;
    // Cache for Textures
    private final Map<String, Texture> loadedTextures = new HashMap<>();
    private final String baseDir;

    // Osu! coordinate system is 640x480.
    private static final float OSU_WIDTH = 640.0f;
    private static final float OSU_HEIGHT = 480.0f;

    public StoryboardRenderer(StoryboardData data, String baseDir) {
        this.data = data;
        this.baseDir = baseDir;

        // Sort sprites by Layer
        if (this.data != null) {
            this.data.sprites.sort((s1, s2) -> s1.layer.compareTo(s2.layer));
        }
    }

    public void render(long time, SkinObjectRenderer sprite, Rectangle r) {
        if (data == null) return;

        // Calculate scale to fit/fill target rectangle 'r'
        // Strategy: Scale to fit height, center horizontally? Or Stretch?
        // Most Osu players fit to height and allow width to exceed (widescreen).
        // Here we constrain to 'r'.
        // Let's use "Fit Height" logic as base, but since 'r' is the BGA area, we likely want to fill it.
        // If we stretch, it distorts. If we fit, we might have black bars.
        // BGA usually fills. Let's stretch for now to match BGA behavior.

        float scaleX = r.width / OSU_WIDTH;
        float scaleY = r.height / OSU_HEIGHT;

        for (StoryboardSprite s : data.sprites) {
            if (!isActive(s, time)) continue;

            Texture tex = getTexture(s.filePath);
            if (tex == null) continue;

            // Initial state
            float x = s.initialX;
            float y = s.initialY;
            float opacity = 0.0f; // Default invisible until Fade command?
            // Actually Osu defaults:
            // If there are commands, start state is defined by first command?
            // If no commands, it's static visible?
            // Let's assume opacity 1 if no Fade commands, else 0 until fade in.
            // But usually sprites have a Fade command if they appear.
            // If strictly no commands, it is static.
            if (s.commands.isEmpty()) opacity = 1.0f;
            else {
                // If there are fade commands, check if any cover 'time'.
                // If time is before all fade commands, opacity is 0? Or the start value of first fade?
                // Osu spec: "The sprite will be invisible until the start time of the first command"
                // But only if that command is a Fade?
                // Let's stick to: iterate commands and apply.
                // If no Fade command affects 'time', we need a default.
                // If there is ANY Fade command in the list, default is 0.
                boolean hasFade = false;
                for(StoryboardCommand c : s.commands) {
                    if(c.type == CommandType.Fade) {
                        hasFade = true;
                        break;
                    }
                }
                if(!hasFade) opacity = 1.0f;
                // If hasFade, start at 0 (or value of first fade command's start if we want to be precise, but usually 0)
                // Actually, value persists. If time < first fade start, it's 0.
            }

            float scaleX_sprite = 1.0f;
            float scaleY_sprite = 1.0f;
            float rotation = 0.0f;
            Color color = new Color(1, 1, 1, 1);
            boolean flipH = false;
            boolean flipV = false;
            boolean additive = false;

            // Apply Loop commands first (TODO: implement loops)
            // For now, ignoring loops (commands inside loops need time shifting)

            for (StoryboardCommand cmd : s.commands) {
                if (cmd.type == CommandType.Loop || cmd.type == CommandType.Trigger) continue;

                // Check time overlap
                if (time < cmd.startTime) {
                     // If we are before this command, we might take its start value IF it's the first command?
                     // No, values persist from previous commands.
                     // If it's the VERY first command of this type, we might not apply it yet.
                     continue;
                }

                // If time > cmd.endTime, we apply the end value (persistence).
                // If time is within, we interpolate.

                float t = 1.0f;
                if (time < cmd.endTime) {
                    float duration = cmd.endTime - cmd.startTime;
                    if (duration > 0) {
                        t = (float)(time - cmd.startTime) / duration;
                        t = Easing.apply(cmd.easing, t);
                    }
                }

                switch (cmd.type) {
                    case Move:
                        x = lerp(cmd.startValues[0], cmd.endValues[0], t);
                        y = lerp(cmd.startValues[1], cmd.endValues[1], t);
                        break;
                    case MoveX:
                        x = lerp(cmd.startValues[0], cmd.endValues[0], t);
                        break;
                    case MoveY:
                        y = lerp(cmd.startValues[0], cmd.endValues[0], t);
                        break;
                    case Fade:
                        opacity = lerp(cmd.startValues[0], cmd.endValues[0], t);
                        break;
                    case Scale:
                        float sVal = lerp(cmd.startValues[0], cmd.endValues[0], t);
                        scaleX_sprite = sVal;
                        scaleY_sprite = sVal;
                        break;
                    case VectorScale:
                        scaleX_sprite = lerp(cmd.startValues[0], cmd.endValues[0], t);
                        scaleY_sprite = lerp(cmd.startValues[1], cmd.endValues[1], t);
                        break;
                    case Rotate:
                        rotation = lerp(cmd.startValues[0], cmd.endValues[0], t);
                        break;
                    case Color:
                        color.r = lerp(cmd.startValues[0], cmd.endValues[0], t) / 255f;
                        color.g = lerp(cmd.startValues[1], cmd.endValues[1], t) / 255f;
                        color.b = lerp(cmd.startValues[2], cmd.endValues[2], t) / 255f;
                        break;
                    case Parameter:
                         // Parameters are active during their time window
                         if (cmd.startTime <= time && time <= cmd.endTime) {
                             // Value is char code?
                             // Usually stored as 0 or 1 in float?
                             // StoryboardCommand stores float array.
                             // Need to know how we parsed 'H', 'V', 'A'.
                             // Assuming we didn't fully implement Parameter parsing in OsuDecoder yet.
                             // Placeholder.
                         }
                        break;
                }
            }

            if (opacity <= 0.001f) continue;

            color.a = opacity;
            sprite.setColor(color);
            // TODO: handle additive blending if 'additive' is true
            // SkinObjectRenderer doesn't easily expose blend mode per draw call without flushing.

            // Convert Coords
            // Osu (0,0) is Top-Left. LibGDX (0,0) is Bottom-Left.
            // Sprite Origin also matters.

            float screenX = r.x + x * scaleX;
            float screenY = r.y + r.height - (y * scaleY); // Flip Y axis for position

            // Size
            float w = tex.getWidth() * scaleX_sprite * scaleX;
            float h = tex.getHeight() * scaleY_sprite * scaleY;

            // Origin
            // In Osu, rotation is around the origin.
            // In LibGDX, we need to pass origin relative to (x,y)
            float originX = 0;
            float originY = 0;

            switch (s.origin) {
                case TopLeft: originX = 0; originY = h; break; // Y flipped
                case Centre: originX = w/2; originY = h/2; break;
                case CentreLeft: originX = 0; originY = h/2; break;
                case TopRight: originX = w; originY = h; break;
                case BottomCentre: originX = w/2; originY = 0; break;
                case BottomRight: originX = w; originY = 0; break;
                // ... others
                default: originX = w/2; originY = h/2; break;
            }

            // Create TextureRegion for flip support
            TextureRegion region = new TextureRegion(tex);
            if (flipH) region.flip(true, false);
            if (flipV) region.flip(false, true);

            // Rotation: Osu is clockwise radians?
            // LibGDX is counter-clockwise degrees.
            float degrees = -rotation * MathUtils.radiansToDegrees;

            // SkinObjectRenderer.draw takes cx, cy as relative origin (0..1)
            float cx = originX / w;
            float cy = originY / h;

            // Note: SkinObjectRenderer.draw(..., cx, cy, ...)
            // cx, cy are multiplied by w, h inside.

            // We need to adjust x, y because sprite.draw draws at x,y with origin offset.
            // If Osu says "draw at 320,240 with center origin",
            // LibGDX draw(x, y, originX, originY, ...) draws image at (x,y) relative to bottom-left, rotated around origin.
            // The texture is drawn from x to x+w, y to y+h.
            // So if Osu x,y is the center, we need to subtract originX, originY from position?
            // No, sprite.draw takes (x, y) as the bottom-left corner of the *unrotated* image.

            // So:
            // screenX is the "Anchor Point" from Osu.
            // We want (screenX, screenY) to be the point (originX, originY) of the image.
            // So drawX = screenX - originX
            // drawY = screenY - originY

            // But wait, screenY was flipped.
            // Osu y=0 is top. screenY = H.
            // If origin is TopLeft (0,0), Osu draws image downwards.
            // LibGDX draws upwards.
            // So if we draw at (screenX, screenY), LibGDX draws from screenY up to screenY+h.
            // We want it to draw down to screenY-h.
            // So drawY = screenY - h.

            // Let's re-verify:
            // Osu TopLeft Origin at 0,0. Image appears at 0,0 to w,h (down-right).
            // LibGDX: r.y+r.height is Top.
            // If we draw at (r.x, r.y+r.height - h), it spans to r.y+r.height.
            // So drawY = screenY - h + (some offset?)

            // Let's use simpler logic:
            // Anchor is at (screenX, screenY).
            // We want the point (originX, originY) of the image to coincide with Anchor.
            // In LibGDX texture coordinates (0,0 bottom-left):
            // The image box is [drawX, drawX+w] x [drawY, drawY+h].
            // The origin point within this box is (drawX + originX_gdx, drawY + originY_gdx).
            // We want this to be (screenX, screenY).
            // So drawX = screenX - originX_gdx
            //    drawY = screenY - originY_gdx

            // Mapping Osu Origin to LibGDX Origin:
            // Osu Centre -> Gdx Centre (w/2, h/2).
            // Osu TopLeft -> Gdx TopLeft (0, h). (Since texture 0,0 is bottom-left).

            float originX_gdx = 0;
            float originY_gdx = 0;
             switch (s.origin) {
                case TopLeft: originX_gdx = 0; originY_gdx = h; break;
                case Centre: originX_gdx = w/2; originY_gdx = h/2; break;
                case CentreLeft: originX_gdx = 0; originY_gdx = h/2; break;
                case TopRight: originX_gdx = w; originY_gdx = h; break;
                case BottomCentre: originX_gdx = w/2; originY_gdx = 0; break;
                case BottomRight: originX_gdx = w; originY_gdx = 0; break;
                case CentreRight: originX_gdx = w; originY_gdx = h/2; break;
                case TopCentre: originX_gdx = w/2; originY_gdx = h; break;
                case BottomLeft: originX_gdx = 0; originY_gdx = 0; break;
                default: originX_gdx = w/2; originY_gdx = h/2; break;
            }

            float drawX = screenX - originX_gdx;
            float drawY = screenY - originY_gdx;

            // Normalize cx, cy for SkinObjectRenderer
            cx = originX_gdx / w;
            cy = originY_gdx / h;

            sprite.draw(region, drawX, drawY, w, h, cx, cy, degrees);
        }
    }

    private boolean isActive(StoryboardSprite s, long time) {
        if (s.commands.isEmpty()) return true;

        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for(StoryboardCommand c : s.commands) {
            if(c.startTime < min) min = c.startTime;
            if(c.endTime > max) max = c.endTime;
        }
        return time >= min && time <= max;
    }

    private Texture getTexture(String path) {
        if (loadedTextures.containsKey(path)) {
            return loadedTextures.get(path);
        }

        // Remove quotes if present
        if (path.startsWith("\"") && path.endsWith("\"")) {
            path = path.substring(1, path.length()-1);
        }

        Path fullPath = Paths.get(baseDir, path);
        try {
            // Load using PixmapResourcePool logic (static method)
            com.badlogic.gdx.graphics.Pixmap p = PixmapResourcePool.loadPicture(fullPath.toString());
            if (p != null) {
                Texture t = new Texture(p);
                p.dispose(); // Texture consumes pixmap? No, Texture creates from pixmap.
                // Pixmap needs to be disposed if not needed.
                // But Texture(Pixmap) uploads it to GPU.
                loadedTextures.put(path, t);
                return t;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return null;
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public void dispose() {
        for(Texture t : loadedTextures.values()) {
            t.dispose();
        }
        loadedTextures.clear();
    }
}
