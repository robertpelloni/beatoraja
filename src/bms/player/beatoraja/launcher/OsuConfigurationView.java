package bms.player.beatoraja.launcher;

import bms.player.beatoraja.PlayerConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import java.net.URL;
import java.util.ResourceBundle;

public class OsuConfigurationView implements Initializable {

    @FXML
    private Slider hitSoundVolume;
    @FXML
    private Slider backgroundDim;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hitSoundVolume.setTooltip(new Tooltip("Volume of note hit sounds (0.0 - 1.0)."));
        backgroundDim.setTooltip(new Tooltip("Darkness of background image/video (0.0 - 1.0)."));
    }

    public void update(PlayerConfig player) {
        hitSoundVolume.setValue(player.getOsuHitSoundVolume() * 100);
        backgroundDim.setValue(player.getOsuBackgroundDim() * 100);
    }

    public void commit(PlayerConfig player) {
        player.setOsuHitSoundVolume((float) (hitSoundVolume.getValue() / 100.0));
        player.setOsuBackgroundDim((float) (backgroundDim.getValue() / 100.0));
    }
}
