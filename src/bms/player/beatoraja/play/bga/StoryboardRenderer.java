package bms.player.beatoraja.play.bga;

import bms.model.storyboard.*;
import bms.player.beatoraja.PixmapResourcePool;
import bms.player.beatoraja.skin.Skin.SkinObjectRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class StoryboardRenderer {

    private final StoryboardData data;
    // We will use a separate resource pool for storyboard sprites to avoid polluting BGA cache
    private final PixmapResourcePool texturePool;
    private final Map<String, Texture> loadedTextures = new HashMap<>();
    private final String baseDir;

    // LibGDX coordinate system is Y-up (0,0 is bottom-left).
    // Osu! coordinate system is Y-down (0,0 is top-left), 640x480 resolution.
    // We need to project Osu coordinates to the target rectangle.
    private static final float OSU_WIDTH = 640.0f;
    private static final float OSU_HEIGHT = 480.0f;

    public StoryboardRenderer(StoryboardData data, String baseDir) {
        this.data = data;
        this.baseDir = baseDir;
        this.texturePool = new PixmapResourcePool(20); // Keep last 20 sprites in memory if needed

        // Sort sprites by Layer
        if (this.data != null) {
            this.data.sprites.sort((s1, s2) -> s1.layer.compareTo(s2.layer));
        }
    }

    public void render(long time, SkinObjectRenderer sprite, Rectangle r) {
        if (data == null) return;

        // Scale factor to fit Osu 640x480 into rectangle r
        // Usually we want "Fit" or "Fill". Let's assume "Fit" but fill the width primarily for widescreen.
        // Actually Osu storyboards are 4:3 but modern ones use widescreen hacks.
        // For simplicity, let's map 0..640 to r.x..r.width and 0..480 to r.y..r.height (flipped Y)

        float scaleX = r.width / OSU_WIDTH;
        float scaleY = r.height / OSU_HEIGHT;
        // Keep aspect ratio? Usually BGA fills the screen.
        // If we stretch, sprites might look distorted.
        // Let's use a uniform scale based on height to maintain 4:3 logic if strictly followed,
        // but robust players usually scale to screen.

        // Render layers in order: Background, Fail/Pass, Foreground, Overlay
        // Osu Layer enum: Background=0, Fail=1, Pass=2, Foreground=3, Overlay=4

        // Sort sprites by layer if not sorted?
        // Assuming data.sprites is populated in parse order.
        // Ideally we should have lists per layer.
        // For now iterate all.

        for (StoryboardSprite s : data.sprites) {
            // Optimization: check layer visibility (e.g. Fail layer only if failing)
            // Current BMSPlayer doesn't expose fail state easily to BGAProcessor draw loop
            // So we might render Pass by default or check state.
            // For now render all (Overlay is always top).

            // Check active time
            if (!isActive(s, time)) continue;

            Texture tex = getTexture(s.filePath);
            if (tex == null) continue;

            // Initial state
            float x = s.initialX;
            float y = s.initialY;
            float opacity = 1.0f; // Default opacity is actually 1 in Osu? No, usually 0 until Fade command?
            // Actually Osu sprites are visible if commands cover time, but default might be defined by first frame.
            // Let's iterate commands.

            float scaleX_sprite = 1.0f;
            float scaleY_sprite = 1.0f;
            float rotation = 0.0f;
            Color color = new Color(1, 1, 1, 1);
            boolean flipH = false;
            boolean flipV = false;

            // Apply Loop commands first (simplified)
            // ...

            boolean hasCommands = false;

            for (StoryboardCommand cmd : s.commands) {
                hasCommands = true;
                if (time < cmd.startTime) {
                    // Before command: take start value if it's the first command of this type?
                    // Actually we need the value at 'time'.
                    // If time is before *all* commands, what is the value?
                    // Usually undefined or invisible?
                    continue;
                }

                // Interpolate
                float t = 1.0f;
                if (time < cmd.endTime) {
                    float duration = cmd.endTime - cmd.startTime;
                    if (duration > 0) {
                        t = (time - cmd.startTime) / duration;
                        t = Easing.apply(cmd.easing, t);
                    }
                }

                // If time > endTime, t remains 1.0 (clamped state)

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
                         // FlipH, FlipV, Additive
                         if (cmd.startTime <= time && time <= cmd.endTime) {
                             // Parameters usually don't interpolate, they apply if active
                             // 'H' -> FlipH, 'V' -> FlipV, 'A' -> Additive
                             // This needs checking command parameter value
                         }
                        break;
                }
            }

            if (opacity <= 0.001f) continue;

            color.a = opacity;
            sprite.setColor(color);
            // Handling Additive blend: if parameter 'A' was set... (TODO)

            // Convert Osu (0,0 top-left) to LibGDX (0,0 bottom-left relative to r)
            // Sprite position is center/origin based.

            // Screen coords
            float screenX = r.x + x * scaleX;
            float screenY = r.y + r.height - (y * scaleY); // Flip Y

            float w = tex.getWidth() * scaleX_sprite * scaleX; // Scale sprite by screen scale too? Usually yes?
            float h = tex.getHeight() * scaleY_sprite * scaleY;

            // Apply Origin
            float originX = 0;
            float originY = 0;

            switch (s.origin) {
                case TopLeft: originX = 0; originY = h; break; // Y is flipped in calculation
                case Centre: originX = w/2; originY = h/2; break;
                // ... others
                default: originX = w/2; originY = h/2; break;
            }

            // Draw
            // SpriteObjectRenderer.draw(texture, x, y, width, height, originX, originY, rotation, srcX, srcY, srcW, srcH, flipX, flipY)
            // We need to use draw method that supports rotation.
            // BGAProcessor passes SkinObjectRenderer which wraps SpriteBatch.
            // But SkinObjectRenderer might usually just have draw(tex, x, y, w, h).
            // We might need to cast to SpriteBatch or extend renderer.
            // For now using simple draw if rotation is 0.

            sprite.draw(tex, screenX - originX, screenY - originY, w, h);
        }
    }

    private boolean isActive(StoryboardSprite s, long time) {
        // Quick check if sprite has commands covering this time
        // Or if it's "Always Visible" (no commands = static? Osu rules say static if present)
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
            // We use PixmapResourcePool but need Texture
            // Ideally use BGImageProcessor logic
            com.badlogic.gdx.graphics.Pixmap p = bms.player.beatoraja.PixmapResourcePool.loadPicture(fullPath.toString());
            if (p != null) {
                Texture t = new Texture(p);
                p.dispose();
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
        texturePool.dispose();
    }
}
