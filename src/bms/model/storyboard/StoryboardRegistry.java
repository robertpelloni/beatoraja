package bms.model.storyboard;

import bms.model.BMSModel;
import java.util.WeakHashMap;
import java.util.Map;
import java.util.Collections;

public class StoryboardRegistry {
    // Use WeakHashMap to prevent memory leaks.
    // Keys (BMSModel) are weak, so when BMSModel is GC'd, the entry is removed.
    private static final Map<BMSModel, StoryboardData> registry = Collections.synchronizedMap(new WeakHashMap<>());

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
