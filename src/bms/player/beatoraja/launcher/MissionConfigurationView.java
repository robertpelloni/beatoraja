package bms.player.beatoraja.launcher;

import bms.player.beatoraja.PlayerConfig;
import bms.player.beatoraja.mission.MissionData;
import com.badlogic.gdx.utils.Json;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MissionConfigurationView implements Initializable {

    @FXML
    private CheckBox autoAccept;
    @FXML
    private TableView<MissionViewModel> missionTable;
    @FXML
    private TableColumn<MissionViewModel, String> titleCol;
    @FXML
    private TableColumn<MissionViewModel, String> descCol;
    @FXML
    private TableColumn<MissionViewModel, String> progressCol;
    @FXML
    private TableColumn<MissionViewModel, String> statusCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        autoAccept.setTooltip(new Tooltip("Automatically accept all available missions."));
        missionTable.setTooltip(new Tooltip("List of current missions and their progress."));

        titleCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().title));
        descCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().description));
        progressCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().progress));
        statusCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().status));

        loadMissions();
    }

    public void update(PlayerConfig player) {
        autoAccept.setSelected(player.isMissionAutoAccept());
    }

    public void commit(PlayerConfig player) {
        player.setMissionAutoAccept(autoAccept.isSelected());
    }

    private void loadMissions() {
        try {
            if (Files.exists(Paths.get("missions.json"))) {
                Json json = new Json();
                ArrayList<MissionData> list = json.fromJson(ArrayList.class, MissionData.class, Files.newBufferedReader(Paths.get("missions.json")));

                ObservableList<MissionViewModel> viewModels = FXCollections.observableArrayList();
                if (list != null) {
                    for (MissionData m : list) {
                        viewModels.add(new MissionViewModel(m));
                    }
                }
                missionTable.setItems(viewModels);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class MissionViewModel {
        public String title;
        public String description;
        public String progress;
        public String status;

        public MissionViewModel(MissionData m) {
            this.title = m.title;
            this.description = m.description;
            this.progress = m.progress + " / " + m.target;
            this.status = m.completed ? "Completed" : "Active";
        }
    }
}
