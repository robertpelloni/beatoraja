# Changelog

## [0.9.1] - 2025-12-27
### Added
- Centralized versioning using `VERSION.md`.
- **FLAC Audio Support**: Implemented FLAC decoding in `PCMLoader` using `jflac-codec`.
- New `AudioTest` unit test for verifying WAV loading.
- `table/default.json` configuration file to fix runtime crash.

### Changed
- Refactored `PCM` class hierarchy:
    - Created generic `PCM<T>` base class.
    - Renamed original `PCM` to `LegacyPCM`.
    - Updated `AbstractAudioDriver` to use `LegacyPCM`.
- Re-implemented `PCMLoader` to support MP3, OGG, and WAV loading using `JLayer` and `OggInputStream`.
- Updated `OsuDecoderTest` to match `BMSModel` API changes.
- Upgraded build environment to Gradle 8.5 and Java 21.
- Replaced `gdx-backend-lwjgl` with `gdx-backend-lwjgl3`.

### Fixed
- `FileNotFoundException: table\default.json` runtime crash.
- Compilation errors in `OsuDecoderTest` and `OsuDecoderBezierTest`.
- Missing `org.lwjgl.input` package errors by migrating to LibGDX Input API.

## [0.9.0] - 2025-12-26
### Added
- **Arena Mode**: Implemented Song Sync protocol (`TYPE_SONG_SELECT`) and integrated with `MusicSelector`.
- **Skin System**: Added support for Fast/Slow counts (`NUMBER_FAST_NOTES`, etc.) and Arena Rank (`NUMBER_ARENA_RANK`).
- **Osu! Support**: Added `.osu` file parser and playback support.
- **Mod Menu**: Added in-game overlay for Hi-Speed/Lane Cover adjustment.
- **In-Game Downloader**: Added background download manager for songs.
- **Step-Up Mode**: Added Step-Up mode logic and persistence.
- **Unit Testing**: Added JUnit 5 framework and initial tests.
- **CI/CD**: Added GitHub Actions workflow for Gradle build.
- **Controller Support**: Added hot-plugging support for controllers.
- **Debug**: Added debug toggle command (Shift+F1).

### Changed
- **Build System**: Migrated from Ant to Gradle (Java 21 target).
- **Backend**: Upgraded to LWJGL 3 and LibGDX 1.12.1.
- **Default Skin**: Updated `result.json` to display Fast/Slow stats and Arena Rank.
- **Refactoring**: Extracted `UpdateManager`, `ScreenshotManager`, `InputManager`, and `DownloadManager` from `MainController`.
- **Osu! Support**: Improved slider curve approximation (Bezier/Linear) and column mapping.
- **Arena Mode**: Added "Disconnect" button to Arena Lobby and optimized UI updates.

## [0.8.9] - 2025-12-25
### Added
- Added `VERSION.md` as the single source of truth for versioning.
- Added `Launcher.java` to bypass JavaFX module encapsulation checks on Java 9+.
- Added `build_release.ps1` helper script for building releases.
- Added `docs/DASHBOARD.md` for project overview.
- Added `LLM_INSTRUCTIONS.md` for unified agent instructions.

### Fixed
- Fixed build failure due to missing JavaFX dependencies by downloading them to `lib/`.
- Fixed Launch4j configuration error (missing icon).
- Fixed Windows release not including `beatoraja.jar`.
- Fixed `build.xml` to correctly package JavaFX and other dependencies.

## [0.8.8] - 2025-12-25
### Added
- Added `VERSION` file to track project version.
- Added `docs/DASHBOARD.md` for project overview.
- Added LLM instruction files (`CLAUDE.md`, `AGENTS.md`, etc.).

### Changed
- Updated version in `build.xml` to 0.8.8.
- Updated version in `MainController.java` to 0.8.8.
- Merged upstream changes (simulated).
