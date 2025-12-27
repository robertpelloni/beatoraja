package bms.player.beatoraja.manager;

import org.lwjgl.input.Mouse;

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
		this.input = new BMSPlayerInputProcessor(main.getConfig(), main.getPlayerConfig());
		
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
		if (main.getCurrentState() == null) return;

		main.getCurrentState().input();
		// event - move pressed
		if (input.isMousePressed()) {
			input.setMousePressed();
			if (main.getCurrentState().getSkin() != null) {
				main.getCurrentState().getSkin().mousePressed(main.getCurrentState(), input.getMouseButton(), input.getMouseX(), input.getMouseY());
			}
		}
		// event - move dragged
		if (input.isMouseDragged()) {
			input.setMouseDragged();
			if (main.getCurrentState().getSkin() != null) {
				main.getCurrentState().getSkin().mouseDragged(main.getCurrentState(), input.getMouseButton(), input.getMouseX(), input.getMouseY());
			}
		}

		// マウスカーソル表示判定
		if(input.isMouseMoved()) {
			input.setMouseMoved(false);
			mouseMovedTime = time;
		}
		
		boolean isPlayState = main.getCurrentState() instanceof BMSPlayer;
		Mouse.setGrabbed(isPlayState && time > mouseMovedTime + 5000 && Mouse.isInsideWindow());

		// FPS表示切替
		if (input.isActivated(KeyCommand.SHOW_FPS)) {
			main.setShowFps(!main.isShowFps());
		}
		// Debug表示切替
		if (input.isActivated(KeyCommand.TOGGLE_DEBUG)) {
			MainController.debug = !MainController.debug;
		}
		// fullscrees - windowed
		if (input.isActivated(KeyCommand.SWITCH_SCREEN_MODE)) {
			boolean fullscreen = Gdx.graphics.isFullscreen();
			Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
			if (fullscreen) {
				Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
			} else {
				Gdx.graphics.setFullscreenMode(currentMode);
			}
			main.getConfig().setDisplaymode(fullscreen ? Config.DisplayMode.WINDOW : Config.DisplayMode.FULLSCREEN);
		}
	}
	
	public void dispose() {
		// input.dispose(); // MainController had this commented out
	}
}
