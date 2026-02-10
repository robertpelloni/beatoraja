package bms.player.beatoraja.launcher;

import bms.player.beatoraja.PlayerConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;
import java.net.URL;
import java.util.ResourceBundle;

public class ArenaConfigurationView implements Initializable {

    @FXML
    private TextField serverIP;
    @FXML
    private Spinner<Integer> serverPort;
    @FXML
    private TextField playerName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverIP.setTooltip(new Tooltip("IP address of the Arena server."));
        serverPort.setTooltip(new Tooltip("Port number of the Arena server (Default: 12345)."));
        playerName.setTooltip(new Tooltip("Your display name in Arena matches."));
    }

    public void update(PlayerConfig player) {
        serverIP.setText(player.getArenaServerIP());
        serverPort.getValueFactory().setValue(player.getArenaPort());
        playerName.setText(player.getArenaPlayerName());
    }

    public void commit(PlayerConfig player) {
        player.setArenaServerIP(serverIP.getText());
        player.setArenaPort(serverPort.getValue());
        player.setArenaPlayerName(playerName.getText());
    }
}
