package bms.player.beatoraja.mission;

import java.io.Serializable;

public class MissionData implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_DAILY = 1;

    public int id;
    public int type;
    public String title;
    public String description;
    public boolean completed;

    public int progress;
    public int target;
    public long expiryDate; // epoch day for daily missions

    // Criteria
    public String criteriaType; // "CLEAR_COUNT", "EX_SCORE", "COMBO", "PLAY_COUNT"

    public MissionData() {}

    public MissionData(int id, int type, String title, String description, String criteriaType, int target) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.criteriaType = criteriaType;
        this.target = target;
    }
}
