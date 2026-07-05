package bms.player.beatoraja.audio;

import java.nio.file.Path;

public class BytePCM extends PCM {

	BytePCM(int channels, int sampleRate, int start, int len, byte[] sample) throws java.io.IOException {
        super(null);
	}

	public BytePCM getSample(int sample) {
        return this;
	}

	public BytePCM changeTempo(float rate) {
        return this;
	}

	
	public byte[] getAudioData() {
        return new byte[0];
	}

	
	public int getAudioFormat() {
		return 0;
	}
}
