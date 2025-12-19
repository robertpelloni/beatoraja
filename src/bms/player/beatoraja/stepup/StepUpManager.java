package bms.player.beatoraja.stepup;

import bms.player.beatoraja.MainController;
import bms.player.beatoraja.RandomCourseData;
import bms.player.beatoraja.RandomStageData;
import bms.player.beatoraja.song.SongData;
import com.badlogic.gdx.utils.Json;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StepUpManager {

    private StepUpData data;
    private final String savePath = "stepup.json";
    private final MainController main;

    public StepUpManager(MainController main) {
        this.main = main;
        load();
    }

    public void load() {
        try {
            if (Files.exists(Paths.get(savePath))) {
                Json json = new Json();
                data = json.fromJson(StepUpData.class, Files.newBufferedReader(Paths.get(savePath)));
            } else {
                data = new StepUpData();
            }
        } catch (Exception e) {
            e.printStackTrace();
            data = new StepUpData();
        }
    }

    public void save() {
        try {
            Json json = new Json();
            Files.writeString(Paths.get(savePath), json.toJson(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RandomCourseData createCourse() {
        RandomCourseData course = new RandomCourseData();
        course.setName("Step-Up Level " + data.currentLevel);

        RandomStageData[] stages = new RandomStageData[3];

        for(int i=0; i<3; i++) {
            stages[i] = new RandomStageData();
            int targetLevel = data.currentLevel;
            if (i == 0) targetLevel = Math.max(1, data.currentLevel - 1);
            stages[i].setSql("level = " + targetLevel);
        }

        course.setStage(stages);
        course.lotterySongDatas(main);

        // Validation and Fallback
        boolean valid = true;
        for (SongData s : course.getSongDatas()) {
            if (s == null) {
                valid = false;
                break;
            }
        }

        if (!valid) {
            // Fallback: Try a broader query
            for(int i=0; i<3; i++) {
                course.getStage()[i].setSql("level >= " + Math.max(1, data.currentLevel - 2) + " AND level <= " + (data.currentLevel + 2));
            }
            course.lotterySongDatas(main);
        }

        return course;
    }

    public void onResult(boolean clear) {
        if (clear) {
            data.currentLevel = Math.min(12, data.currentLevel + 1);
        } else {
            // Only level down if level > 1
            data.currentLevel = Math.max(1, data.currentLevel - 1);
        }
        save();
    }

    public int getCurrentLevel() {
        return data.currentLevel;
    }
}
