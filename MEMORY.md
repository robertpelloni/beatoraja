# Memory

*   **Course Editor:** Wired to `SongDatabaseAccessor`. Saving uses absolute paths in `.crs` format. Stubbed `setCourseData` methods need implementation if we want to load existing courses.
*   **Osu! Storyboard:** Renderer handles Move, Fade, Scale, Rotate, Parameter, and Flip commands. Uses `WeakHashMap` in Registry to prevent leaks. Texture loading is guarded by `PixmapResourcePool`.
*   **Arena Chat:** Implemented via Type 8 packet. UI is in `ModMenu`.
*   **Skinning:** Missing a preview feature in the launcher.
*   **UI Framework:** JavaFX for Launcher, LibGDX/Scene2D for In-Game. Wiring between them is handled via `Config` and `PlayerConfig` serialization/deserialization.
*   **BMSModel:** Immutable object from `bms-common` (likely). We use external registries (StoryboardRegistry) to attach extra data.
# Agent Memory & Codebase Observations

## Core Architecture
- `MainController.java` is historically a god class but is actively being refactored into Managers (`UpdateManager`, `DownloadManager`, `InputManager`, etc.).
- UI is split between **JavaFX** (Launcher/Config) and **LibGDX Scene2D** (In-Game Overlay/ModMenu).

## Data Flow
- Osu! files (`.osu`) are ingested via `OsuDecoder`, which maps them to the standard `BMSModel`. This allows the engine to treat Osu! charts exactly like BMS charts downstream.
- Scoring data is stored in `ScoreData` while inputs are logged in `ReplayData`.
- The Skin engine relies heavily on `IntegerPropertyFactory` and `BooleanPropertyFactory` to map internal game state IDs (like `NUMBER_FAST_NOTES`) to visual skin elements.

## Design Preferences
- Zero tolerance for `bobcoin`.
- Prefer keeping versions in `VERSION.md` and reading them dynamically at runtime rather than relying on Java constants.