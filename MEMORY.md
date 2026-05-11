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
