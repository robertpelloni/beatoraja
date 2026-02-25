# Changelog

## [0.9.18] - 2025-02-10

### Added
- **Replay**: Functional Data Loading for Replay Analysis.
    - The "Replay Analysis" tab in the Launcher now automatically loads the last 50 scores from the database when selected.
    - Users can select a score to view detailed statistics (Score, PG/GR/etc breakdown, Hit Error Histogram).

### Changed
- **Config**: Bumped version to 0.9.18.

## [0.9.17] - 2025-02-10

### Added
- **Replay**: Integrated Replay Analysis UI into Launcher.
    - Added "Replay Analysis" tab to the Configuration window.
    - Displays list of scores with detailed breakdown (Fast/Slow distribution).
    - Wired to `PlayConfigurationView` (currently requires manual data loading implementation in future, serves as UI skeleton).

### Changed
- **Config**: Bumped version to 0.9.17.

## [0.9.16] - 2025-02-10

### Added
- **Replay**: Added Replay Analysis UI Foundation.
    - Implemented `ReplayAnalysisView` and `ReplayAnalysisView.fxml` for viewing detailed replay statistics (Hit Error Histogram, Gauge History).
    - NOTE: This feature is currently in backend implementation state and will be fully wired in the next update.

### Changed
- **Config**: Bumped version to 0.9.16.

## [0.9.15] - 2025-02-10

### Added
- **Input**: Implemented Device-Specific Input Interval Configuration.
    - Added "Min Interval" column to the Input Configuration table.
    - Decoupled Keyboard polling rate from Controller polling rates.
    - Allows fine-tuning of input latency per controller device.

### Changed
- **Config**: Bumped version to 0.9.15.

## [0.9.14] - 2025-02-10

### Added
- **Launcher**: Added Skin Preview functionality.
    - The Skin Configuration tab now displays a `preview.png` (or `.jpg`) if available in the selected skin's directory.
- **Documentation**: Overhauled development documentation.
    - Added `LLM_INSTRUCTIONS.md`, `VISION.md`, `DEPLOY.md`, `DASHBOARD.md`.
    - Updated agent-specific instruction files.

### Changed
- **Config**: Bumped version to 0.9.14.

## [0.9.13] - 2025-02-10

### Added
- **Course Editor**: Implemented saving functionality.
    - Users can now save created courses as `.crs` files.
    - Uses absolute paths to reference songs for maximum compatibility.
- **Osu!**: Refined Storyboard Renderer.
    - Added support for `FlipH` and `FlipV` commands.
    - Implemented correct rotation (degrees vs radians) and origin handling.
    - Secured image loading with `PixmapResourcePool` to prevent memory leaks.
- **Documentation**: Added "Course Editor" manual page and updated "Osu! Mode" page with Storyboard details.

### Changed
- **Config**: Bumped version to 0.9.13.

## [0.9.12] - 2025-02-10

### Added
- **Arena**: Implemented Chat System.
    - Updated `ArenaMessage` to support `TYPE_CHAT`.
    - Added `sendChat`/`onChatMessage` handling in `ArenaClient` and `ArenaManager`.
    - Added Chat Log and Input Field to the Arena window in `ModMenu` (in-game).
- **Course Editor**: Implemented Backend Logic.
    - Wired `CourseEditorView` to `SongDatabaseAccessor` for real song searching and filtering.
    - Implemented stub methods for `getCourseData`/`setCourseData` to support `TableEditorView` integration.

### Changed
- **Config**: Bumped version to 0.9.12.

## [0.9.11] - 2025-02-10

### Added
- **Osu!**: Full Storyboard Rendering support.
    - Implemented `StoryboardRenderer` to draw sprites during gameplay.
    - Implemented easing functions (Linear, Quad, Cubic, Quart, Quint, Sine, Expo, Circ, Back) in `Easing.java`.
    - Added sorting of storyboard sprites by layer (Background -> Overlay) for correct draw order.
    - Implemented coordinate projection to scale Osu! 640x480 coordinates to the player's screen resolution.

### Changed
- **Config**: Bumped version to 0.9.11.

## [0.9.10] - 2025-02-10

### Added
- **Osu!**: Implemented backend infrastructure for Storyboard support.
    - Added `StoryboardData`, `StoryboardSprite`, `StoryboardCommand` structures.
    - Updated `OsuDecoder` to parse `[Events]` commands (Move, Fade, Scale, etc).
    - Created `StoryboardRegistry` to manage storyboard data alongside immutable BMS models.
    - Created `StoryboardRenderer` (foundation) and wired it into `BGAProcessor`.
- **Launcher**: Added "Course Editor" tab with basic UI for creating custom courses.

## [0.9.9] - 2025-02-10

### Added
- **Skinning**: Added new skin properties `NUMBER_MISSION_CURRENT`, `NUMBER_MISSION_TARGET`, `NUMBER_ARENA_PLAYERS_COUNT`.
- **Skinning**: Added `NUMBER_ARENA_SCORE_DIFF` to display real-time score difference against the top opponent in Arena mode.
- **Osu!**: Basic support for parsing "Sprite" events in Osu! storyboards (currently extracts background images).
