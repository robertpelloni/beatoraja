package bms.player.beatoraja.audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Paths;
import java.io.IOException;

public abstract class PCM<T> {
    protected int channels;
    protected int sampleRate;
    protected int start;
    protected int len;
    protected T sample;

    protected PCM() {
    }

    public PCM(int channels, int sampleRate, int start, int len, T sample) {
        this.channels = channels;
        this.sampleRate = sampleRate;
        this.start = start;
        this.len = len;
        this.sample = sample;
    }

    public int getChannels() {
        return channels;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public T getSample() {
        return sample;
    }
    
    public abstract PCM<T> changeSampleRate(int sample);
    public abstract PCM<T> changeFrequency(float rate);
    public abstract PCM<T> changeChannels(int channels);
    public abstract PCM<T> slice(long starttime, long duration);
    public abstract boolean validate();

    public static PCM<?> load(String path, AbstractAudioDriver<?> driver) {
        try {
            return new LegacyPCM(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PCM<?> load(java.nio.file.Path path, AbstractAudioDriver<?> driver) {
        try {
            return new LegacyPCM(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static ByteBuffer getDirectByteBuffer(int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }
}
