package bms.player.beatoraja.play.bga;

import bms.model.storyboard.*;
import bms.player.beatoraja.skin.Skin.SkinObjectRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import java.util.HashMap;
import java.util.Map;

public class StoryboardRenderer {

    private final StoryboardData data;
    // Cache for loaded textures (path -> Texture)
    // In a real implementation, this should use ResourcePool or BGAProcessor's cache
    private final Map<String, Texture> textureCache = new HashMap<>();

    public StoryboardRenderer(StoryboardData data) {
        this.data = data;
    }

    public void render(long time, SkinObjectRenderer sprite, Rectangle r) {
        if (data == null) return;

        for (StoryboardSprite s : data.sprites) {
            // Check if active
            long startTime = Long.MAX_VALUE;
            long endTime = Long.MIN_VALUE;

            if (s.commands.isEmpty()) {
                // Static sprite? Or default behavior?
                // For now skip empty commands to avoid clutter
                continue;
            }

            for (StoryboardCommand cmd : s.commands) {
                if (cmd.startTime < startTime) startTime = cmd.startTime;
                if (cmd.endTime > endTime) endTime = cmd.endTime;
            }

            // Simple visibility check
            if (time < startTime || time > endTime) continue;

            // Interpolate values
            float x = s.initialX;
            float y = s.initialY;
            float opacity = 1.0f;
            float scale = 1.0f;
            float rotation = 0.0f;

            for (StoryboardCommand cmd : s.commands) {
                if (time >= cmd.startTime) {
                    float t = 1.0f;
                    if (time < cmd.endTime) {
                        t = (float)(time - cmd.startTime) / (cmd.endTime - cmd.startTime);
                        // TODO: Apply Easing
                    }

                    switch (cmd.type) {
                        case Move:
                            x = lerp(cmd.startValues[0], cmd.endValues[0], t);
                            y = lerp(cmd.startValues[1], cmd.endValues[1], t);
                            break;
                        case Fade:
                            opacity = lerp(cmd.startValues[0], cmd.endValues[0], t);
                            break;
                        case Scale:
                            scale = lerp(cmd.startValues[0], cmd.endValues[0], t);
                            break;
                        // ... other commands
                    }
                }
            }

            if (opacity <= 0.01f) continue;

            // Draw
            // Need texture... bypassing for prototype
            // Texture tex = textureCache.get(s.filePath);
            // if (tex != null) ...
        }
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public void dispose() {
        for(Texture t : textureCache.values()) {
            t.dispose();
        }
        textureCache.clear();
    }
}
