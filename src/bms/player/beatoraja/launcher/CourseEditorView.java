package bms.player.beatoraja.launcher;

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
import javafx.util.Callback;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseEditorView implements Initializable {

    @FXML private TableView<SongData> songTable;
    @FXML private TableColumn<SongData, String> titleColumn;
    @FXML private TableColumn<SongData, String> artistColumn;
    @FXML private TableColumn<SongData, String> levelColumn;
    @FXML private TextField searchField;
    @FXML private ListView<SongData> courseList;
    @FXML private TextField courseTitleField;

    private ObservableList<SongData> allSongs = FXCollections.observableArrayList();
    private ObservableList<SongData> courseSongs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        levelColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getLevel())));

        courseList.setItems(courseSongs);
        courseList.setCellFactory(new Callback<ListView<SongData>, ListCell<SongData>>() {
            @Override
            public ListCell<SongData> call(ListView<SongData> param) {
                return new ListCell<SongData>() {
                    @Override
                    protected void updateItem(SongData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle() + " [" + item.getArtist() + "]");
                        }
                    }
                };
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterSongs(newValue));
    }

    private SongDatabaseAccessor songDB;

    public void init(SongDatabaseAccessor songDB) {
        setSongDatabaseAccessor(songDB);
    }

    public void setSongDatabaseAccessor(SongDatabaseAccessor songDB) {
        this.songDB = songDB;
    }

    public void setCourseData(bms.player.beatoraja.CourseData[] courses) {
        courseSongs.clear();
        // TODO: Load existing courses logic
    }

    public bms.player.beatoraja.CourseData[] getCourseData() {
        return new bms.player.beatoraja.CourseData[0];
    }

    private void filterSongs(String query) {
        if (songDB != null && query != null && !query.isEmpty()) {
            SongData[] results = songDB.getSongDatasByText(query);
            if (results != null) {
                allSongs.setAll(results);
                songTable.setItems(allSongs);
            }
        }
    }

    @FXML
    public void addSong() {
        SongData selected = songTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            courseSongs.add(selected);
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
        if (courseSongs.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No songs", "Please add songs to the course before saving.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Course");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMS Course Files", "*.crs"));
        File file = fileChooser.showSaveDialog(songTable.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
                writer.write("#TITLE " + (courseTitleField.getText().isEmpty() ? "My Course" : courseTitleField.getText()));
                writer.newLine();
                writer.write("#ARTIST User");
                writer.newLine();
                writer.write("#DIFFICULTY 5");
                writer.newLine();

                for (SongData song : courseSongs) {
                    String path = song.getPath();
                    if (path != null) {
                        writer.write("#STAGEfile " + path);
                        writer.newLine();
                    }
                }

                showAlert(Alert.AlertType.INFORMATION, "Success", "Course saved successfully.");

            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save course: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
