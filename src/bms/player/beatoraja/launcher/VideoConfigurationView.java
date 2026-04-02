package bms.player.beatoraja.launcher;

import bms.player.beatoraja.Config;
import bms.player.beatoraja.MainLoader;
import bms.player.beatoraja.PlayerConfig;
import bms.player.beatoraja.Resolution;
import com.badlogic.gdx.Graphics;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;

import java.net.URL;
import java.util.ResourceBundle;

public class VideoConfigurationView implements Initializable {
	@FXML
	private ComboBox<Resolution> resolution;
	@FXML
	private ComboBox<Config.DisplayMode> displayMode;
	@FXML
	private ComboBox<String> bgaOp;
	@FXML
	private ComboBox<String> bgaExpand;

	@FXML
	private CheckBox vSync;

	@FXML
	private Spinner<Integer> maxFps;
	@FXML
	private Spinner<Integer> missLayerTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
		updateResolutions();

		displayMode.getItems().setAll(Config.DisplayMode.values());

		// Add Tooltips
		resolution.setTooltip(new Tooltip("Select the game resolution."));
		displayMode.setTooltip(new Tooltip("Select display mode (Windowed, Fullscreen, Borderless)."));
		vSync.setTooltip(new Tooltip("Synchronize frame rate with monitor refresh rate (VSync)."));
		maxFps.setTooltip(new Tooltip("Limit the maximum frame rate (FPS)."));
		bgaOp.setTooltip(new Tooltip("Background Animation (BGA) settings (On/Off/Autoplay)."));
		bgaExpand.setTooltip(new Tooltip("BGA scaling options (Full/Keep Aspect Ratio)."));
		missLayerTime.setTooltip(new Tooltip("Duration of the Miss Layer image display in ms."));
    }

    public void update(Config config) {
		displayMode.setValue(config.getDisplaymode());
		resolution.setValue(config.getResolution());
		vSync.setSelected(config.isVsync());
		bgaOp.getSelectionModel().select(config.getBga());
		bgaExpand.getSelectionModel().select(config.getBgaExpand());
		maxFps.getValueFactory().setValue(config.getMaxFramePerSecond());
	}

	public void updatePlayer(PlayerConfig player) {
		missLayerTime.getValueFactory().setValue(player.getMisslayerDuration());
	}

	public void commit(Config config) {
		config.setResolution(resolution.getValue());
		config.setDisplaymode(displayMode.getValue());
		config.setVsync(vSync.isSelected());
		config.setBga(bgaOp.getSelectionModel().getSelectedIndex());
		config.setBgaExpand(bgaExpand.getSelectionModel().getSelectedIndex());
		config.setMaxFramePerSecond(maxFps.getValue());
	}

	public void commitPlayer(PlayerConfig player) {
		player.setMisslayerDuration(missLayerTime.getValue());
	}

	@FXML
	public void updateResolutions() {
		Resolution oldValue = resolution.getValue();
		resolution.getItems().clear();

		if (displayMode.getValue() == Config.DisplayMode.FULLSCREEN) {
			Graphics.DisplayMode[] displays = MainLoader.getAvailableDisplayMode();
			for(Resolution r : Resolution.values()) {
				for(Graphics.DisplayMode display : displays) {
					if(display.width == r.width && display.height == r.height) {
						resolution.getItems().add(r);
						break;
					}
				}
			}
		} else {
			Graphics.DisplayMode display = MainLoader.getDesktopDisplayMode();
			for(Resolution r : Resolution.values()) {
				if (r.width <= display.width && r.height <= display.height) {
					resolution.getItems().add(r);
				}
			}
		}
		resolution.setValue(resolution.getItems().contains(oldValue)
				? oldValue : resolution.getItems().get(resolution.getItems().size() - 1));
	}
}
