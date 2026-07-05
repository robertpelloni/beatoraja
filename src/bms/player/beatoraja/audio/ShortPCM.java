package bms.player.beatoraja.audio;

import java.nio.file.Path;

public class ShortPCM extends PCM {

	ShortPCM(int channels, int sampleRate, int start, int len, short[] sample) throws java.io.IOException {
        super(null);
	}

	public ShortPCM getSample(int sample) {
        return this;
	}

	public ShortPCM changeTempo(float rate) {
        return this;
	}

	
	public byte[] getAudioData() {
        return new byte[0];
	}

	
	public int getAudioFormat() {
		return 0;
	}
}
