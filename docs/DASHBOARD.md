# Project Dashboard

## Project Status
- **Version**: 0.9.1
- **Date**: 2025-12-27
- **Build System**: Gradle (build.gradle)
- **Java Version**: Java 21 (Target)
- **Backend**: LWJGL 3 (via origin/lwjgl3 merge)

## Dependencies (Managed via Gradle)
Dependencies are now primarily managed via Gradle, with some legacy JARs in `lib/`.

| Library | Version | Description | Location |
| :--- | :--- | :--- | :--- |
| **libGDX** | 1.12.1 | Game development framework (LWJGL 3 backend). | Maven Central |
| **JavaFX** | 21 | UI framework for the launcher. | Maven Central |
| **FFmpeg** | 6.1.1 | Video playback support (via JavaCV 1.5.10). | Maven Central |
| **Commons Compress** | 1.26.0 | Compression library. | Maven Central |
| **Discord RPC** | 2.0.1 | Discord Rich Presence integration. | `lib/java-discord-rpc-2.0.1-all.jar` |
| **jFLAC** | 1.5.3 | FLAC audio support. | `lib/jflac-codec-1.5.3.jar` |
| **LuaJ** | 3.0.2-custom | Lua scripting support for skins. | `lib/luaj-jse-3.0.2-custom.jar` |
| **Twitter4J** | 4.0.4 | Twitter integration. | Maven Central |
| **SQLite JDBC** | 3.45.1.0 | Database connectivity. | Maven Central |
| **JBMS Parser** | - | BMS File Parser. | `lib/jbms-parser.jar` |
| **JBMS Table Parser** | - | BMS Table Parser. | `lib/jbmstable-parser.jar` |
| **JPortAudio** | - | PortAudio bindings. | `lib/jportaudio.jar` |

## Project Structure

- **src/**: Source code for the application.
  - **bms/player/beatoraja/**: Main package.
    - **MainLoader.java**: Main application entry point (LWJGL 3).
    - **MainController.java**: Core application logic (delegates to managers).
    - **manager/**:
        - **UpdateManager.java**: Song database and table updates.
        - **ScreenshotManager.java**: Screenshot capture and Twitter integration.
        - **InputManager.java**: Input polling and handling.
        - **DownloadManager.java**: Background downloads (IPFS/Crawler).
    - **arena/**: Arena Mode (Networking, Client/Server).
    - **audio/**: Audio driver implementations (LegacyPCM, PCMLoader).
    - **config/**: Configuration management.
    - **play/**: Gameplay logic (BMS parsing, note rendering, judging).
    - **skin/**: Skinning system (Lua-based).
    - **song/**: Song database (SQLite) and management.
    - **stepup/**: Step-Up Mode logic.
- **build.gradle**: Gradle build script.
- **settings.gradle**: Gradle settings.
- **lib/**: Local dependencies (Discord RPC, LuaJ, etc.).
- **docs/**: Documentation files.
- **VERSION.md**: Single source of truth for project version.
- **HANDOFF.md**: Session summary and changelog.
- **LLM_INSTRUCTIONS.md**: Unified instructions for AI agents.

## Recent Changes
- **2025-12-27**:
    - **Versioning**: Centralized versioning using `VERSION.md`.
    - **Audio Refactoring**: Refactored `PCM` class hierarchy and re-implemented `PCMLoader`.
    - **Runtime Fix**: Fixed `FileNotFoundException` crash by adding `table/default.json`.
    - **Tests**: Added `AudioTest` and fixed `OsuDecoderTest`.
    - **Refactoring**: Extracted `UpdateManager`, `ScreenshotManager`, `InputManager`, and `DownloadManager` from `MainController`.
    - **Controller Support**: Added hot-plugging support.
    - **Osu! Support**: Improved slider curve approximation (Bezier/Linear) and column mapping.
    - **Arena Polish**: Added Disconnect button and optimized UI.
- **2025-12-26**:
    - **Arena Mode**: Implemented Song Sync protocol and integrated with Music Selector.
    - **Skin System**: Added support for Fast/Slow counts and Arena Rank display. Updated default skin.
    - **Merged `origin/master`**: Integrated Arena Mode, Osu! file support, Mod Menu, and In-Game Downloader.
    - **Merged `origin/lwjgl3`**: Upgraded backend to LWJGL 3.
    - **Build System**: Migrated from Ant to Gradle.
    - **Documentation**: Created Dashboard and unified agent instructions.
