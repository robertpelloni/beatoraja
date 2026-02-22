# Memory

*   **Course Editor:** Wired to `SongDatabaseAccessor`. Saving uses absolute paths in `.crs` format. Stubbed `setCourseData` methods need implementation if we want to load existing courses.
*   **Osu! Storyboard:** Renderer handles Move, Fade, Scale, Rotate, Parameter, and Flip commands. Uses `WeakHashMap` in Registry to prevent leaks. Texture loading is guarded by `PixmapResourcePool`.
*   **Arena Chat:** Implemented via Type 8 packet. UI is in `ModMenu`.
*   **Skinning:** Missing a preview feature in the launcher.
*   **UI Framework:** JavaFX for Launcher, LibGDX/Scene2D for In-Game. Wiring between them is handled via `Config` and `PlayerConfig` serialization/deserialization.
*   **BMSModel:** Immutable object from `bms-common` (likely). We use external registries (StoryboardRegistry) to attach extra data.
