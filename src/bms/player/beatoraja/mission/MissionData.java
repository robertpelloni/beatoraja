package bms.player.beatoraja.mission;

import java.io.Serializable;

public class MissionData implements Serializable {
    private static final long serialVersionUID = 1L;

    public int id;
    public String title;
    public String description;
    public boolean completed;

    // Simple criteria
    public int requiredClearCount;
    public int requiredExScore;

    public MissionData() {}

    public MissionData(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
