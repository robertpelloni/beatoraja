# Project Dashboard

## Project Status
- **Version**: 0.8.9
- **Date**: 2025-12-26
- **Build System**: Ant (uild.xml)
- **Java Version**: Java 8 (Target), Java 25 (Compatible via Launcher.java)
- **Backend**: LWJGL 3 (via origin/lwjgl3 merge)

## Dependencies (Managed in lib/)
The project does not currently use Git submodules. Dependencies are managed as JAR files in the lib/ directory.

| Library | Version | Description |
| :--- | :--- | :--- |
| **libGDX** | (Unknown, likely 1.9.10+) | Game development framework (LWJGL 3 backend). |
| **JavaFX** | 21.0.1 | UI framework for the launcher. |
| **FFmpeg** | (via JavaCPP) | Video playback support. |
| **JavaCV** | (via JavaCPP) | Computer vision/video processing. |
| **Commons Compress** | 1.16.1 | Compression library. |
| **Discord RPC** | 2.0.1 | Discord Rich Presence integration. |
| **jFLAC** | 1.5.3 | FLAC audio support. |
| **LuaJ** | 3.0.2-custom | Lua scripting support for skins. |
| **Twitter4J** | 4.0.4 | Twitter integration. |
| **SQLite JDBC** | (Unknown) | Database connectivity. |

## Project Structure

- **src/**: Source code for the application.
  - **bms/player/beatoraja/**: Main package.
    - **Launcher.java**: Entry point for modern Java versions (Java 9+).
    - **MainLoader.java**: Legacy entry point and main application bootstrapper.
    - **MainController.java**: Core application logic and state management.
    - **audio/**: Audio driver implementations (OpenAL, PortAudio, etc.).
    - **config/**: Configuration management (JSON serialization).
    - **play/**: Gameplay logic (BMS parsing, note rendering, judging).
    - **skin/**: Skinning system (Lua-based).
    - **song/**: Song database (SQLite) and management.
- **build.xml**: Ant build script.
- **build_release.ps1**: PowerShell script for building releases with correct JVM arguments.
- **launch4j/**: Configuration for creating Windows executables.
- **lib/**: External libraries (jar files).
- **skin/**: Default skins and resources.
- **docs/**: Documentation files.
- **VERSION.md**: Single source of truth for project version.
- **CHANGELOG.md**: Record of all notable changes.
- **LLM_INSTRUCTIONS.md**: Unified instructions for AI agents.

## Recent Changes
- **2025-12-26**: Merged origin/lwjgl3 feature branch. Upgraded backend to LWJGL 3.
- **2025-12-25**: Modernized build system, implemented VERSION.md, and refactored documentation.
