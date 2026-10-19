package bms.player.beatoraja.audio;

import java.nio.file.Path;

public class BytePCM extends PCM {

	BytePCM(int channels, int sampleRate, int start, int len, byte[] sample) {
		super(channels, sampleRate, start, len); this.sample = sample;
	}

	public BytePCM getSample(int sample) {
        return this;
	}

	/**
	 * サンプリングレートを変更したPCMを返す
	 * 
	 * @param sample
	 *            サンプリングレート
	 * @return サンプリングレート変更後のPCM
	 */
	public BytePCM changeSampleRate(int sample) {
		byte[] samples = getSample(sample);
		int start = (Math.min((int)((long)((PCM)this).start * sample / this.sampleRate), samples.length - 1) / channels) * channels;
		int len = (Math.min((int)((long)((PCM)this).len * sample / this.sampleRate), samples.length - start) / channels) * channels;
		return new BytePCM(channels, sample, start, len, samples);
	}

	/**
	 * 再生速度を変更したPCMを返す
	 * 
	 * @param rate
	 *            再生速度。基準は1.0
	 * @return 再生速度を変更したPCM
	 */
	public BytePCM changeFrequency(float rate) {
		byte[] samples = getSample((int) (sampleRate / rate));
		int start = (Math.min((int)((long)((PCM)this).start / rate / this.sampleRate), samples.length - 1) / channels) * channels;
		int len = (Math.min((int)((long)((PCM)this).len / rate / this.sampleRate), samples.length - start) / channels) * channels;
		return new BytePCM(channels, sampleRate, start, len, samples);
	}
	
	private byte[] getSample(int sample) {
		byte[] samples = new byte[(int) (((long) ((byte[])this.sample).length / channels) * sample / sampleRate) * channels];

		for (long i = 0; i < samples.length / channels; i++) {
			long position = i * sampleRate / sample;
			long mod = (i * sampleRate) % sample;
			for (int j = 0; j < channels; j++) {
				if (mod  != 0 && (int) ((position + 1) * channels + j) < ((byte[])this.sample).length) {
					short sample1 = ((byte[])this.sample)[(int) (position * channels + j)];
					short sample2 = ((byte[])this.sample)[(int) ((position + 1) * channels + j)];
					samples[(int) (i * channels + j)] = (byte) (((long)sample1 * (sample - mod) + (long)sample2 * mod) / sample);
				} else {
					samples[(int) (i * channels + j)] = ((byte[])this.sample)[(int) (position * channels + j)];
				}
			}
		}
		
		return samples;
	}

	/**
	 * チャンネル数を変更したPCMを返す
	 * 
	 * @param channels
	 *            チャンネル数
	 * @return チャンネル数を変更したPCM
	 */
	public BytePCM changeChannels(int channels) {
		byte[] samples = new byte[((byte[])this.sample).length * channels / this.channels];

		for (long i = 0; i < samples.length / channels; i++) {
			for (int j = 0; j < channels; j++) {
				samples[(int) (i * channels + j)] = ((byte[])this.sample)[(int) (i * this.channels)];
			}
		}
		return new BytePCM(channels, sampleRate, ((PCM)this).start * channels / this.channels , ((PCM)this).len  * channels / this.channels, samples);
	}

	/**
	 * トリミングしたPCMを返す
	 * 
	 * @param starttime
	 *            開始時間(us)
	 * @param duration
	 *            再生時間(us)
	 * @return トリミングしたPCM
	 */
	public BytePCM slice(long starttime, long duration) {
		if (duration == 0 || starttime + duration > ((long) ((PCM)this).len) * 1000000 / (sampleRate * channels)) {
			duration = Math.max(((long) ((PCM)this).len) * 1000000 / (sampleRate * channels) - starttime, 0);
		}

		final int start = (int) ((starttime * sampleRate / 1000000) * channels);
		int length = (int) ((duration * sampleRate / 1000000) * channels);
//		final int orglength = length;
		while(length > channels) {
			boolean zero = true;
			for(int i = 0;i < channels;i++){
				zero &= (((byte[])this.sample)[((PCM)this).start + start + length - i - 1] == 0);
			}
			if(zero) {
				length -= channels;
			} else {
				break;
			}
		}
//		if(length != orglength) {
//			Logger.getGlobal().info("終端の無音データ除外 - " + (orglength - length) + " samples");
//		}
		return length > 0 ? new BytePCM(channels, sampleRate, ((PCM)this).start + start, length, this.sample) : null;
	}
	
	public int getAudioFormat() {
		return 0;
	}
}
