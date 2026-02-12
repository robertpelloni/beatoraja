package bms.model.storyboard;

import bms.model.BMSModel;
import java.util.HashMap;
import java.util.Map;

public class StoryboardRegistry {
    private static final Map<BMSModel, StoryboardData> registry = new HashMap<>();

    public static void register(BMSModel model, StoryboardData data) {
        registry.put(model, data);
    }

    public static StoryboardData get(BMSModel model) {
        return registry.get(model);
    }

    public static void clear() {
        registry.clear();
    }
}
