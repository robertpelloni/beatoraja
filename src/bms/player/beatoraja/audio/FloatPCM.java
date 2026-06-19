package bms.player.beatoraja.audio;

import java.nio.file.Path;




public class FloatPCM extends PCM {

	FloatPCM(int channels, int sampleRate, int start, int len, float[] sample) throws java.io.IOException { super(null);
        /* super */
	}

	public FloatPCM(PCM pcm) throws java.io.IOException { super(null);
        /* super */
	}

	public FloatPCM getSample(int sample) {
        return this;
	}

	public FloatPCM changeTempo(float rate) {
        return this;
	}


	public byte[] getAudioData() {
        return new byte[0];
	}

	
	public int getAudioFormat() {
		return 0; // AL_FORMAT_STEREO_FLOAT32
	}
}
