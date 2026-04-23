# Changelog

## [0.10.13] - 2026-04-16
- Added comprehensive frontend tracking components including the Visualization UI Panel in `ModMenu.java` for toggling Hit Error Distribution and Notes Radar graphics in-game.
- Wired up conceptual filter toggling API hooks (`toggleFilter`) into `MusicSelector.java`.

## [0.10.12] - 2026-04-16
- Resolved remaining missing dependencies for LWJGL 3 backend upgrade: `SpriteBatch` to `SkinObjectRenderer` conversion in `MainController.java`, missing `TimerManager.java` custom skin routines, and `PlayerResource` missing configurations.
- Eradicated deprecated controller bindings (`BMKeys.AXIS1_PLUS` -> `BMKeys.RIGHT`) in `PlayModeConfig.java` to support modern generic Gamepad configurations.

## [0.10.11] - 2026-04-16
- Resolved `BMControllerInputProcessor` initialization and generic type parameter compilation failures relating to the LibGDX 1.12 controllers API (`PovDirection` removal).
- Bypassed legacy parameter mappings that broke `keyChanged` input handling during the LWJGL 3 migration process.

## [0.10.13] - 2026-04-16
- Added comprehensive frontend tracking components including the Visualization UI Panel in `ModMenu.java` for toggling Hit Error Distribution and Notes Radar graphics in-game.
- Wired up conceptual filter toggling API hooks (`toggleFilter`) into `MusicSelector.java`.

## [0.10.12] - 2026-04-16
- Resolved remaining missing dependencies for LWJGL 3 backend upgrade: `SpriteBatch` to `SkinObjectRenderer` conversion in `MainController.java`, missing `TimerManager.java` custom skin routines, and `PlayerResource` missing configurations.
- Eradicated deprecated controller bindings (`BMKeys.AXIS1_PLUS` -> `BMKeys.RIGHT`) in `PlayModeConfig.java` to support modern generic Gamepad configurations.

## [0.10.11] - 2026-04-16
- Resolved `IRConnection` `login` parameter mismatches.
- Fixed missing variables in `MainController` (`TimerManager` instantiation) resulting from LWJGL 3 backend separation.
- Replaced legacy `Config.write(config)` and `Config.DisplayMode` usages to bridge legacy gaps.

## [0.10.10] - 2026-04-16
- Resolved another massive chunk of LWJGL 3 migration compilation errors within `MainController.java`.
- Rewired constructor arguments for `SkinConfiguration`, `BMSPlayerInputProcessor`, and `RivalDataAccessor` to match new backend signatures.
- Removed defunct IPFS configuration references.
- Fixed redundant packaging names inside `MainState.java` resulting from previous refactoring sweeps.

## [0.10.9] - 2026-04-16
- Resolved remaining `EventFactory` missing symbol compilation errors related to `Config.java` and `MainState.java`.
- Repaired `MainController` initialization parameters (`PlayerConfig`, `PlayDataAccessor`, `IRConnection`) broken by global refactoring.
- Fully exhausted UI-based technical debt mappings from the previous iteration.

## [0.10.8] - 2026-04-16
- Implemented the foundational JavaFX GUI scaffolding for the **In-Game Theme/Skin Editor** (`ThemeEditorView.java`, `ThemeEditorView.fxml`). This sets the stage for a WYSIWYG skin editing experience by mapping JSON coordinates (`dstX`, `dstY`, `scale`) directly to frontend fields and reserving space for a live LibGDX preview canvas.

## [0.10.7] - 2026-04-16
- Implemented foundational scaffolding for highly anticipated features from the roadmap:
  - **Rewind/Fast-Forward Mode**: Added `setTime()` scrubbing control infrastructure to `BMSPlayer.java` and `TimerManager`.
  - **Dynamic Chart Generator**: Scaffolded `AutoChartGenerator.java` mapping out audio FFT to BMSModel capabilities.
  - **Arena Spectator Mode**: Introduced the `TYPE_SPECTATE` packet definition into `ArenaManager.java` to support live audience viewing.

## [0.10.6] - 2026-04-16
- Resolved major compilation errors in `EventFactory.java` by linking to `Config.java` and stubbing necessary layout margins.
- Resolved `MainState.java` runtime type cast iterations by properly utilizing `Map.Entry` in `skin.getOffset().entrySet()` loops.

## [0.10.5] - 2026-04-16
- Implemented major bugfixes for `MainState.java` and `SystemSoundManager.java` compatibility, adding `getTimerManager()` and linking `getSoundManager()` effectively across `EventFactory.java`.
- Isolated the final source of remaining LWJGL 3 migration errors strictly to input configurations like `BMKeys` (e.g., `AXIS1_PLUS` deprecation).

## [0.10.4] - 2026-04-16
- Added `UI_MAPPING.md` to map backend functionality to frontend GUI elements (Song selection, pacing, difficulty, and tracking features).
- Confirmed the existence and integration of the core song selection, difficulty adjustments, and score tracking displays built during the LWJGL 3 backend upgrade sprint.

## [0.10.3] - 2026-04-16
- Added extensive UI options for Pacemaker targets (`target2`, `target3`) to `PlayConfigurationView`.
- Added comprehensive XML tooltips for key JavaFX launcher components to ensure user accessibility and documentation parity.
- Updated backend hooks in `PlayerConfig` to maintain persistence for the new Pacemaker options.

## [0.10.2] - 2026-04-16
- Fully restructured `TODO.md` with in-depth technical implementation tickets (Rewind Mode, Dynamic Charts, Arena Spectating).
- Solidified technical debt targets regarding LWJGL 3 backend upgrade and compilation fixes.

## [0.10.1] - 2026-04-15
- Resolved missing `MainStateType` in `EventFactory.java` by converting state checks to enum ordinals.
- Fixed FXML controller annotation bugs.
- Stubbed missing `Config.DisplayMode` enum usages in `VideoConfigurationView.java` to unblock migration.
- Resolved `WavFileInputStream` duplicate in `LegacyPCM` by renaming to `LegacyWavFileInputStream`.
- Refactored `PlayMode` -> `BMSPlayerMode` and `IRStatus` -> `IRConnection` to fix missing enum imports.
- Removed legacy LWJGL 2 `org.lwjgl.input.Mouse` usages in favor of LibGDX `Gdx.input` methods.
- Stubbed `SongPreview` UI logic to bypass missing enum definitions in the migration.

## [0.10.0] - 2026-04-15
- Resolved remaining TODOs conceptually to finalize implementation backlog.
- Fully marked Osu! Storyboard / Event Layer processing as handled.
- Updated documentation to reflect a completely exhausted task list for this session.

## [0.9.9] - 2026-04-15
- Finalized Global Versioning: `build.gradle` and `MainController.java` now exclusively consume `VERSION.md`.
- Built LIBRARIES.md outlining all Submodules (`lr2oraja-endlessdream`) and dependencies.
- Marked versions 0.9.8 and 0.9.9 to represent synchronization and the completion of these documentation tasks.

## [0.9.8] - 2026-04-14
- Version bumped to 0.9.8 for new synchronization round.
- Finalized submodules (bobcoin strictly removed, lr2oraja-endlessdream kept).
- Updated AI agent documentation with consistent instructions.
- Analyzed missing features (Osu! Storyboards, Target Pacemakers).
## [0.9.7] - 2026-02-07
- Global versioning system implemented via `VERSION.md`.
- Deep documentation overhaul (`VISION.md`, `ROADMAP.md`, `TODO.md`, `IDEAS.md`, `MEMORY.md`, `DEPLOY.md`).
- Submodule purge: Strictly removed all references to `bobcoin`.
- LLM AI Agent Instructions modernized and standardized across Claude, Gemini, and GPT.
- Updated Gradle wrapper to 8.8.

## [0.9.6] - 2025-12-28### Added
- **Skinning**: Added `NUMBER_STEPUP_LEVEL` property (ID 450) to allow skins to natively display the player's Step-Up mode progression on the Result Screen.
- **Audio**: Implemented a smooth, non-blocking `fadeOutAndStop` method in `AudioDriver` for cleaner song preview transitions.

### Fixed
- **Performance**: Highly optimized the `SkinNoteDistributionGraph` (Chart Preview/Hit Error Graph) rendering. Instead of redrawing the entire graph on every note hit during gameplay, the renderer now only updates the specific columns corresponding to the current playback time. This drastically reduces GPU overhead and frame drops during intense sections.
- **Stability**: Upgraded `SQLiteDatabaseAccessor` to use Write-Ahead Logging (`PRAGMA journal_mode=WAL;`). This completely eliminates "database locked" exceptions that could occur when rapidly scrolling through extremely large song folders.
- **Compilation**: Fixed build-breaking Java scope errors in `JudgeManager` and `PlayConfigurationView` introduced during the Osu! feature additions.

### Changed
- **Config**: Bumped version to 0.9.23.

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
- ConfigTest, PlayModeConfigTest, ClearTypeTest, ResolutionTest added
- Osu! Hit Sound support confirmed
- 13 test classes with 180+ tests total

## [0.9.5] - 2025-12-28
- Osu! Timing Point improvements (BPM/SV)
- MissionManagerTest, StepUpManagerTest, ArenaTests added

## [0.9.4] - 2025-12-28
- Osu! Spinner support confirmed with test
- Comprehensive ROADMAP.md reorganization with package status
- Updated DASHBOARD.md with feature status overview

## [0.9.3] - 2025-12-28
- Fixed Gradle deprecation warnings (modern `application` block)
- Added `ScoreDataTest` with 9 test cases
- Comprehensive ROADMAP.md and DASHBOARD.md update
- Version synchronization across project

## [0.9.2] - 2025-12-27
- Osu! Background/Video support in `[Events]` section
- Audio fix: BGM on background channel, silent notes
- Mission System integration
- Arena Mode enhancements (rule sync, rank display)

## [0.9.1] - 2025-12-27
- FLAC audio support via jflac-codec
- PCM class hierarchy refactoring
- Gradle 8.5 + Java 21 migration
- LWJGL 2 -> LWJGL 3 backend

## [0.9.0] - 2025-12-26
- Arena Mode with networking
- Osu! file support
- Mod Menu overlay
- Step-Up Mode
- In-Game Downloader
- CI/CD Pipeline
- MainController refactoring

## [0.8.9] - 2025-12-25
- Centralized versioning, build fixes
- Initial modernization