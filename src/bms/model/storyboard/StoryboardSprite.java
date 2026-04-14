package bms.model.storyboard;

import java.util.ArrayList;
import java.util.List;

public class StoryboardSprite {
    public enum Layer {
        Background, Fail, Pass, Foreground, Overlay
    }

    public enum Origin {
        TopLeft, Centre, CentreLeft, TopRight, BottomCentre, TopCentre, Custom, CentreRight, BottomLeft, BottomRight
    }

    public Layer layer;
    public Origin origin;
    public String filePath;
    public float initialX;
    public float initialY;
    public List<StoryboardCommand> commands = new ArrayList<>();

    // Loop support
    public boolean isLoop;
    public long loopStartTime;
    public int loopCount;
    public List<StoryboardCommand> loopCommands = new ArrayList<>();

    public StoryboardSprite(Layer layer, Origin origin, String filePath, float initialX, float initialY) {
        this.layer = layer;
        this.origin = origin;
        this.filePath = filePath;
        this.initialX = initialX;
        this.initialY = initialY;
    }
}
