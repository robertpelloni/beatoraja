package bms.player.beatoraja.launcher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import bms.player.beatoraja.ReplayData;
import bms.player.beatoraja.song.SongData;

import java.net.URL;
import java.util.ResourceBundle;

public class ReplayAnalysisView implements Initializable {

    @FXML private ListView<ReplayData> replayList;
    @FXML private Label songTitleLabel;
    @FXML private Label scoreLabel;
    @FXML private Label judgeLabel; // PG/GR/GD/BD/PR
    @FXML private BarChart<String, Number> hitErrorHistogram;
    @FXML private LineChart<Number, Number> gaugeHistoryChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize charts and list listeners
    }

    public void setReplayData(ReplayData replay, SongData song) {
        // Populate UI with replay stats
        if (replay == null) return;

        songTitleLabel.setText(song != null ? song.getTitle() : "Unknown Song");
        scoreLabel.setText("Score: " + replay.score + " (" + replay.clear + ")");

        // TODO: Populate Histogram (need hit delta data from ReplayData)
        // TODO: Populate Gauge Chart (need gauge history from ReplayData)
    }
}
