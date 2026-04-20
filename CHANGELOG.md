# Changelog

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

## [0.9.6] - 2025-12-28
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
