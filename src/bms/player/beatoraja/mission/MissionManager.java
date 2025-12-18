package bms.player.beatoraja.mission;

import bms.player.beatoraja.MainController;
import bms.player.beatoraja.ScoreData;
import bms.player.beatoraja.ClearType;
import com.badlogic.gdx.utils.Json;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MissionManager {

    private final MainController main;
    private List<MissionData> missions = new ArrayList<>();
    private final String savePath = "missions.json";

    public MissionManager(MainController main) {
        this.main = main;
        load();
        checkDailyMissions();
        if (missions.isEmpty()) {
            initDefaults();
        }
    }

    private void initDefaults() {
        missions.add(new MissionData(1, MissionData.TYPE_NORMAL, "First Step", "Play any song", "PLAY_COUNT", 1));
        missions.add(new MissionData(2, MissionData.TYPE_NORMAL, "Clear Master", "Clear 10 songs", "CLEAR_COUNT", 10));
    }

    private void checkDailyMissions() {
        long today = System.currentTimeMillis() / (24 * 60 * 60 * 1000);

        // Remove expired dailies
        Iterator<MissionData> it = missions.iterator();
        boolean hasDaily = false;
        while(it.hasNext()) {
            MissionData m = it.next();
            if (m.type == MissionData.TYPE_DAILY) {
                if (m.expiryDate < today) {
                    it.remove();
                } else {
                    hasDaily = true;
                }
            }
        }

        // Add new daily if missing
        if (!hasDaily) {
            Random r = new Random();
            int target = 3 + r.nextInt(3); // 3 to 5
            MissionData daily = new MissionData(100 + (int)today, MissionData.TYPE_DAILY,
                    "Daily Clear", "Clear " + target + " songs today", "CLEAR_COUNT", target);
            daily.expiryDate = today;
            missions.add(daily);
            save();
        }
    }

    public void load() {
        try {
            if (Files.exists(Paths.get(savePath))) {
                Json json = new Json();
                ArrayList<MissionData> list = json.fromJson(ArrayList.class, MissionData.class, Files.newBufferedReader(Paths.get(savePath)));
                if (list != null) missions = list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Json json = new Json();
            Files.writeString(Paths.get(savePath), json.toJson(missions));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkResult(ScoreData result) {
        boolean updated = false;
        for (MissionData m : missions) {
            if (!m.completed) {
                switch(m.criteriaType) {
                    case "PLAY_COUNT":
                        m.progress++;
                        break;
                    case "CLEAR_COUNT":
                        if (result.getClear() > ClearType.Failed.id) {
                            m.progress++;
                        }
                        break;
                    case "EX_SCORE":
                        // Accumulate
                        m.progress += result.getExscore();
                        break;
                    case "COMBO":
                        m.progress = Math.max(m.progress, result.getCombo());
                        break;
                }

                if (m.progress >= m.target) {
                    m.completed = true;
                    m.progress = m.target;
                    // Notify
                    if (main.getMessageRenderer() != null) {
                        main.getMessageRenderer().addMessage("Mission Completed: " + m.title, 3000, com.badlogic.gdx.graphics.Color.GOLD, 0);
                    }
                }
                updated = true;
            }
        }
        if (updated) {
            save();
        }
    }

    public List<MissionData> getMissions() {
        return missions;
    }
}
