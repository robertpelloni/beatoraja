package bms.player.beatoraja.stepup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StepUpData implements Serializable {
    private static final long serialVersionUID = 1L;

    public int currentLevel = 1;
    public int currentStage = 1; // 1, 2, 3
    public List<String> passedSongs = new ArrayList<>(); // Store MD5 or Title

    public StepUpData() {}
}
