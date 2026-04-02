package bms.model.storyboard;

public class StoryboardCommand {
    public CommandType type;
    public Easing easing;
    public long startTime;
    public long endTime;
    public float[] startValues;
    public float[] endValues;

    public StoryboardCommand(CommandType type, Easing easing, long startTime, long endTime, float[] startValues, float[] endValues) {
        this.type = type;
        this.easing = easing;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startValues = startValues;
        this.endValues = endValues;
    }
}
