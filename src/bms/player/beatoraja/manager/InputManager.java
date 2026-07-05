package bms.player.beatoraja.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

import bms.player.beatoraja.Config;
import bms.player.beatoraja.MainController;
import bms.player.beatoraja.input.BMSPlayerInputProcessor;
import bms.player.beatoraja.input.KeyCommand;
import bms.player.beatoraja.play.BMSPlayer;

public class InputManager {

	private final MainController main;
	private BMSPlayerInputProcessor input;
	private long mouseMovedTime;

	public InputManager(MainController main) {
		this.main = main;
		this.input = new BMSPlayerInputProcessor(main.getConfig());
		
		Thread polling = new Thread(() -> {
			long time = 0;
			for (;;) {
				final long now = System.nanoTime() / 1000000;
				if (time != now) {
					time = now;
					input.poll();
				} else {
					try {
						Thread.sleep(0, 500000);
					} catch (InterruptedException e) {
					}
				}
			}
		});
		polling.start();
	}

	public BMSPlayerInputProcessor getInputProcessor() {
		return input;
	}

	public void update(long time) {
		if (null /* main.getCurrentState() */ == null) return;

		/* main.getCurrentState().input(); */
		// event - move pressed
		if (input.isMousePressed()) {
			input.setMousePressed();
			if (false) {

			}
		}
		// event - move dragged
		if (input.isMouseDragged()) {
			input.setMouseDragged();
			if (false) {

			}
		}

		// マウスカーソル表示判定
		if(false) {

			mouseMovedTime = time;
		}
		
		boolean isPlayState = null /* main.getCurrentState() */ instanceof BMSPlayer;
		Gdx.input.setCursorCatched(isPlayState && time > mouseMovedTime + 5000);

		// FPS表示切替
		if (false) {

		}
		// Debug表示切替
		if (false) {

		}
		// fullscrees - windowed
		if (false) {
			boolean fullscreen = Gdx.graphics.isFullscreen();
			Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
			if (fullscreen) {
				Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
			} else {
				Gdx.graphics.setFullscreenMode(currentMode);
			}

		}
	}
	
	public void dispose() {
		// input.dispose(); // MainController had this commented out
	}
}
