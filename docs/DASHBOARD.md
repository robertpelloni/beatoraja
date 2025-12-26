# Project Dashboard

## Project Status
- **Version**: 0.8.9
- **Date**: 2025-12-26
- **Build System**: Gradle (build.gradle)
- **Java Version**: Java 21 (Target)
- **Backend**: LWJGL 3 (via origin/lwjgl3 merge)

## Dependencies (Managed via Gradle)
Dependencies are now primarily managed via Gradle, with some legacy JARs in `lib/`.

| Library | Version | Description |
| :--- | :--- | :--- |
| **libGDX** | 1.12.1 | Game development framework (LWJGL 3 backend). |
| **JavaFX** | 21 | UI framework for the launcher. |
| **FFmpeg** | 6.1.1 | Video playback support (via JavaCV 1.5.10). |
| **Commons Compress** | 1.26.0 | Compression library. |
| **Discord RPC** | 2.0.1 | Discord Rich Presence integration (local jar). |
| **jFLAC** | 1.5.3 | FLAC audio support (local jar). |
| **LuaJ** | 3.0.2-custom | Lua scripting support for skins (local jar). |
| **Twitter4J** | 4.0.4 | Twitter integration. |
| **SQLite JDBC** | 3.45.1.0 | Database connectivity. |

## Project Structure

- **src/**: Source code for the application.
  - **bms/player/beatoraja/**: Main package.
    - **MainLoader.java**: Main application entry point (LWJGL 3).
    - **MainController.java**: Core application logic and state management.
    - **arena/**: Arena Mode (Networking, Client/Server).
    - **audio/**: Audio driver implementations.
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
- **2025-12-26**:
    - **Arena Mode**: Implemented Song Sync protocol and integrated with Music Selector.
    - **Skin System**: Added support for Fast/Slow counts and Arena Rank display. Updated default skin.
    - **Merged `origin/master`**: Integrated Arena Mode, Osu! file support, Mod Menu, and In-Game Downloader.
    - **Merged `origin/lwjgl3`**: Upgraded backend to LWJGL 3.
    - **Build System**: Migrated from Ant to Gradle.
    - **Documentation**: Created Dashboard and unified agent instructions.

```
