package bms.player.beatoraja.launcher;

import bms.player.beatoraja.PlayerConfig;
import bms.player.beatoraja.song.SongData;
import bms.player.beatoraja.song.SongDatabaseAccessor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseEditorView implements Initializable {

    @FXML private TableView<SongData> songTable;
    @FXML private TableColumn<SongData, String> titleColumn;
    @FXML private TableColumn<SongData, String> artistColumn;
    @FXML private TableColumn<SongData, String> levelColumn;
    @FXML private TextField searchField;
    @FXML private ListView<String> courseList;
    @FXML private TextField courseTitleField;

    private ObservableList<SongData> allSongs = FXCollections.observableArrayList();
    private ObservableList<String> courseSongs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        levelColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getLevel())));

        courseList.setItems(courseSongs);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterSongs(newValue));
    }

    public void init(SongDatabaseAccessor songDB) {
        // In a real implementation, we would query the DB asynchronously
        // allSongs.setAll(songDB.getSongDatas(...));
        // songTable.setItems(allSongs);
    }

    // Stub methods to satisfy TableEditorView dependencies
    public void setSongDatabaseAccessor(SongDatabaseAccessor songDB) {
        // Implementation
    }

    public void setCourseData(bms.player.beatoraja.CourseData[] courses) {
        // Implementation
    }

    public bms.player.beatoraja.CourseData[] getCourseData() {
        // Implementation
        return new bms.player.beatoraja.CourseData[0];
    }

    private void filterSongs(String query) {
        // Filter logic
    }

    @FXML
    public void addSong() {
        SongData selected = songTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            courseSongs.add(selected.getTitle() + " [" + selected.getMd5() + "]");
        }
    }

    @FXML
    public void removeSong() {
        int index = courseList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            courseSongs.remove(index);
        }
    }

    @FXML
    public void saveCourse() {
        // Generate BMS course file
    }
}
