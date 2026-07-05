package bms.player.beatoraja.audio;

import java.nio.ByteBuffer;
import java.nio.file.Path;

public class ShortDirectPCM extends PCM {

	ShortDirectPCM(int channels, int sampleRate, int start, int len, ByteBuffer sample) throws java.io.IOException {
        super(null);
	}

	public ShortDirectPCM getSample(int sample) {
        return this;
	}

	public ShortDirectPCM changeTempo(float rate) {
        return this;
	}

	
	public byte[] getAudioData() {
        return new byte[0];
	}

	
	public int getAudioFormat() {
		return 0;
	}
}
