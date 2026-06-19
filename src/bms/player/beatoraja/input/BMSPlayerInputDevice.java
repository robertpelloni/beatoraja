package bms.player.beatoraja.input;

/**
 * 入力デバイス
 *
 * @author excln
 */
public abstract class BMSPlayerInputDevice {

	protected final BMSPlayerInputProcessor bmsPlayerInputProcessor;
	/**
	 * 入力デバイスの種類
	 */
	public final Type type;

	/**
	 * デバイスの入力状態をクリアする
	 */
	public abstract void clear();

	private boolean enabled = true;
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	protected BMSPlayerInputDevice(BMSPlayerInputProcessor bmsPlayerInputProcessor, Type type) {
		this.bmsPlayerInputProcessor = bmsPlayerInputProcessor;
		this.type = type;
	}

	/**
	 * 入力デバイスのタイプ
	 *
	 * @author excln
	 */
	public enum Type {
		KEYBOARD,
		BM_CONTROLLER,
		MIDI
	}
}
