package bms.player.beatoraja.launcher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.util.Callback;
import bms.player.beatoraja.ScoreData;
import bms.player.beatoraja.MainLoader;
import bms.player.beatoraja.song.SongDatabaseAccessor;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class ReplayAnalysisView implements Initializable {

    @FXML private ListView<ScoreData> replayList;
    @FXML private Label songTitleLabel;
    @FXML private Label scoreLabel;
    @FXML private Label judgeLabel; // PG/GR/GD/BD/PR
    @FXML private BarChart<String, Number> hitErrorHistogram;
    @FXML private LineChart<Number, Number> gaugeHistoryChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        replayList.setCellFactory(new Callback<ListView<ScoreData>, ListCell<ScoreData>>() {
            @Override
            public ListCell<ScoreData> call(ListView<ScoreData> param) {
                return new ListCell<ScoreData>() {
                    @Override
                    protected void updateItem(ScoreData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(String.format("Score: %d (%s) - %s", item.getExscore(), item.getClear(), item.getSha256().substring(0, 8)));
                        }
                    }
                };
            }
        });

        replayList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayReplay(newValue);
        });
    }

    public void setScoreData(List<ScoreData> scores) {
        replayList.getItems().setAll(scores);
    }

    private void displayReplay(ScoreData data) {
        if (data == null) return;

        scoreLabel.setText("Score: " + data.getExscore() + " / " + data.getNotes() * 2);
        judgeLabel.setText(String.format("PG:%d GR:%d GD:%d BD:%d PR:%d BP:%d",
            data.getEpg() + data.getLpg(), data.getEgr() + data.getLgr(), data.getEgd() + data.getLgd(),
            data.getEbd() + data.getLbd(), data.getEpr() + data.getLpr(), data.getMinbp()));

        // Histogram (Fast/Slow distribution based on ScoreData fields)
        hitErrorHistogram.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Timing Distribution");

        // ScoreData splits Early (Fast) and Late (Slow) for each judge
        series.getData().add(new XYChart.Data<>("Fast PG", data.getEpg()));
        series.getData().add(new XYChart.Data<>("Slow PG", data.getLpg()));
        series.getData().add(new XYChart.Data<>("Fast GR", data.getEgr()));
        series.getData().add(new XYChart.Data<>("Slow GR", data.getLgr()));
        series.getData().add(new XYChart.Data<>("Fast GD", data.getEgd()));
        series.getData().add(new XYChart.Data<>("Slow GD", data.getLgd()));
        series.getData().add(new XYChart.Data<>("Fast BD", data.getEbd()));
        series.getData().add(new XYChart.Data<>("Slow BD", data.getLbd()));

        hitErrorHistogram.getData().add(series);

        // Gauge History
        gaugeHistoryChart.getData().clear();
        // ScoreData does not store gauge history.
        // We would need to replay the chart to get this.
        // Leaving blank for now as "Replay Analysis (Static)"
    }
}
