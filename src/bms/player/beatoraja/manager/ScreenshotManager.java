package bms.player.beatoraja.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

import bms.player.beatoraja.MainController;
import bms.player.beatoraja.external.ScreenShotFileExporter;
import bms.player.beatoraja.external.ScreenShotTwitterExporter;

public class ScreenshotManager {

    private final MainController main;
    private Thread screenshotThread;

    public ScreenshotManager(MainController main) {
        this.main = main;
    }

    public void saveScreenshot() {
        if (screenshotThread == null || !screenshotThread.isAlive()) {
            final byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
            screenshotThread = new Thread(() -> {
                // Set alpha to 255
                for (int i = 3; i < pixels.length; i += 4) {
                    pixels[i] = (byte) 0xff;
                }
                new ScreenShotFileExporter().send(main.getCurrentState(), pixels);
            });
            screenshotThread.start();
        }
    }

    public void postTwitter() {
        if (screenshotThread == null || !screenshotThread.isAlive()) {
            final byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), false);
            screenshotThread = new Thread(() -> {
                // Set alpha to 255
                for (int i = 3; i < pixels.length; i += 4) {
                    pixels[i] = (byte) 0xff;
                }
                new ScreenShotTwitterExporter(main.getPlayerConfig()).send(main.getCurrentState(), pixels);
            });
            screenshotThread.start();
        }
    }
}
