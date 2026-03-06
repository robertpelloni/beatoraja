# Changelog

## [0.9.22] - 2025-02-10

### Fixed
- **Hotfix**: Fixed a critical ArrayOutOfBoundsException in the `OsuDecoder` caused by incorrect parameter parsing for Storyboard commands. Animations now correctly interpolate between start and end states.
- **Hotfix**: Fixed a severe UI destruction regression where `CourseEditorView.fxml` and its controller were completely overwritten. The original, fully-featured Dan/Class configuration UI has been successfully restored.
- **Hotfix**: Fixed a Threading violation (OpenGL crash) in Arena Chat. Incoming chat messages now correctly queue UI updates on the main rendering thread using `Gdx.app.postRunnable()`.
- **Hotfix**: Fixed an `ArenaListener` interface compilation breakage by ensuring the `onChatMessage` method has a `default` implementation, maintaining compatibility with existing listeners.
- **Hotfix**: Removed an unlocalized string key from the Input Configuration UI that could cause rendering errors.

### Changed
- **Config**: Bumped version to 0.9.22.

## [0.9.21] - 2025-02-10

### Fixed
- **Hotfix**: Reverted accidental destruction of `CourseEditorView.fxml` and `.java`. The original, highly detailed configuration UI for Dan/Class courses has been fully restored.
- **Hotfix**: Fixed a critical bug in `OsuDecoder.java` where Storyboard animation `startVals` and `endVals` arrays were sharing the same reference, resulting in static/broken animations. The parser now correctly separates start and end states.
- **Hotfix**: Fixed a compilation breakage caused by adding `onChatMessage` to `ArenaListener` without a `default` implementation.
- **Hotfix**: Removed missing translation key `%MINIMUM_INPUT_DURATION` from `InputConfigurationView.fxml` to prevent UI rendering errors.

### Changed
- **Config**: Bumped version to 0.9.21.

## [0.9.20] - 2025-02-10

### Fixed
- **Hotfix**: Resolved a critical bug where standard BMS background videos/images were globally darkened by the Osu! `Background Dim` setting. The dimming is now strictly limited to `.osu` charts.
- **Hotfix**: Resolved a critical bug where standard BMS keysounds were scaled down by the Osu! `Hit Sound Volume` setting. The volume scaling is now correctly isolated to Osu! mode.
- **Hotfix**: Fixed a major bug in the `CourseEditorView` where saving configuration settings in the Launcher would wipe existing custom courses from `config.json`. The editor now correctly preserves existing state.
- **Hotfix**: Fixed a file resource leak in `SkinConfigurationView` that locked `preview.png` files on Windows while the Launcher was open.

### Changed
- **Config**: Bumped version to 0.9.20.

## [0.9.19] - 2025-02-10

### Added
- **UI Polish**: Enhanced Folder Editor View.
    - Added comprehensive tooltips to all editor controls (+, -, Up, Down) for improved usability and clarity.

### Changed
- **Config**: Bumped version to 0.9.19.

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
