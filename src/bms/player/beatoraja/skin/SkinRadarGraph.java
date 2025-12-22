package bms.player.beatoraja.skin;

import bms.player.beatoraja.MainState;
import bms.player.beatoraja.skin.Skin.SkinObjectRenderer;
import bms.player.beatoraja.song.SongData;
import bms.player.beatoraja.song.NotesRadar;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SkinRadarGraph extends SkinObject {

    private TextureRegion shapetex;
    private SongData current;
    private Color lineColor = Color.RED;
    private Color fillColor = new Color(1, 0, 0, 0.5f);
    private MainState state;

    public SkinRadarGraph(String color) {
        if (color != null && color.length() > 0) {
             try {
                 Color c = Color.valueOf(color);
                 if (c != null) {
                     this.lineColor = c;
                     this.fillColor = new Color(lineColor);
                     this.fillColor.a = 0.5f;
                 }
             } catch (Exception e) {}
        }
    }

    @Override
    public void prepare(long time, MainState state) {
        this.state = state;
        super.prepare(time, state);
    }

    @Override
    public void draw(SkinObjectRenderer sprite) {
        if (state == null) return;
        final SongData song = state.resource.getSongdata();
        if (song != current || shapetex == null) {
            current = song;
            updateTexture();
        }

        if (shapetex != null) {
            draw(sprite, shapetex, region.x, region.y + region.height, region.width, -region.height);
        }
    }

    private void updateTexture() {
        if (shapetex != null) {
            shapetex.getTexture().dispose();
            shapetex = null;
        }

        if (current == null || current.getNotesRadar() == null) {
            return;
        }

        NotesRadar radar = current.getNotesRadar();
        int width = (int)Math.abs(region.width);
        int height = (int)Math.abs(region.height);
        if (width <= 0 || height <= 0) return;

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        // Clear transparent
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        // Center
        int cx = width / 2;
        int cy = height / 2;
        int radius = Math.min(width, height) / 2 - 2;

        // 6 points
        // 0: Top (Notes)
        // 1: Top-Right (Chord)
        // 2: Bottom-Right (Peak)
        // 3: Bottom (Scratch)
        // 4: Bottom-Left (Soflan)
        // 5: Top-Left (Charge)

        double[] values = new double[] {
            radar.notes / 100.0,
            radar.chord / 100.0,
            radar.peak / 100.0,
            radar.scratch / 100.0,
            radar.soflan / 100.0,
            radar.charge / 100.0
        };

        int[] px = new int[6];
        int[] py = new int[6];

        for(int i=0; i<6; i++) {
            double angle = Math.toRadians(i * 60 - 90); // -90 to start at top
            double val = Math.min(Math.max(values[i], 0.05), 1.0); // Minimum 5% to show point
            int r = (int)(radius * val);
            px[i] = cx + (int)(r * Math.cos(angle));
            py[i] = cy + (int)(r * Math.sin(angle));
        }

        // Draw Fill (Triangle Fan)
        pixmap.setBlending(Pixmap.Blending.SourceOver);
        pixmap.setColor(fillColor);
        for(int i=0; i<6; i++) {
            int next = (i + 1) % 6;
            pixmap.fillTriangle(cx, cy, px[i], py[i], px[next], py[next]);
        }

        // Draw Outline
        pixmap.setColor(lineColor);
        for(int i=0; i<6; i++) {
            int next = (i + 1) % 6;
            pixmap.drawLine(px[i], py[i], px[next], py[next]);
        }

        shapetex = new TextureRegion(new Texture(pixmap));
        pixmap.dispose();
    }

    @Override
    public void dispose() {
        if (shapetex != null) {
            shapetex.getTexture().dispose();
        }
    }
}
