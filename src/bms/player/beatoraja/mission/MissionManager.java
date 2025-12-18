package bms.player.beatoraja.mission;

import bms.player.beatoraja.MainController;
import com.badlogic.gdx.utils.Json;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MissionManager {

    private final MainController main;
    private List<MissionData> missions = new ArrayList<>();
    private final String savePath = "missions.json";

    public MissionManager(MainController main) {
        this.main = main;
        load();
        if (missions.isEmpty()) {
            initDefaults();
        }
    }

    private void initDefaults() {
        missions.add(new MissionData(1, "First Play", "Play any song"));
        missions.add(new MissionData(2, "Clear Master", "Clear 10 songs"));
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

    public void checkMissions() {
        // Mock logic
        for (MissionData m : missions) {
            if (!m.completed) {
                if (m.id == 1 && main.getPlayerResource().getPlayerData().getPlaycount() > 0) {
                    m.completed = true;
                    // Notify?
                }
            }
        }
        save();
    }

    public List<MissionData> getMissions() {
        return missions;
    }
}
