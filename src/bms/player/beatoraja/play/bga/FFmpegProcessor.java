package bms.player.beatoraja.play.bga;

import com.badlogic.gdx.graphics.Texture;

public class FFmpegProcessor implements MovieProcessor {
    public FFmpegProcessor(int skip) {}
    public void dispose() {}
    public Texture getFrame() { return null; }
    public Texture getFrame(long time) { return null; }
    public long getFrameDuration() { return 0; }
    public long getFrameTime() { return 0; }
    public int getHeight() { return 0; }
    public int getWidth() { return 0; }
    public void load(String path) {} public void create(String path) {}
    public void play(boolean loop) {}
    public void play(long time, boolean loop) {}
    public void setSyncOffset(long offset) {}
    public void stop() {}
}
