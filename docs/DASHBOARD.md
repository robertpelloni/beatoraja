# Project Dashboard

## Project Status
- **Version**: 0.9.3
- **Date**: 2025-12-28
- **Build System**: Gradle 8.5 (`build.gradle`)
- **Java Version**: Java 21
- **Backend**: LWJGL 3 / LibGDX 1.12.1

## Repository Information
| Property | Value |
| :--- | :--- |
| **Origin** | https://github.com/robertpelloni/beatoraja.git |
| **Upstream** | https://github.com/exch-bms2/beatoraja.git |
| **Default Branch** | master |
| **Submodules** | None (dependencies via Gradle + `lib/`) |

## Dependencies

### Gradle-Managed Dependencies (Maven Central)
| Library | Version | Description |
| :--- | :--- | :--- |
| LibGDX | 1.12.1 | Game framework (rendering, audio, input) |
| LibGDX LWJGL3 Backend | 1.12.1 | Modern OpenGL backend |
| LibGDX FreeType | 1.12.1 | TrueType font rendering |
| LibGDX Controllers | 2.2.3 | Controller/gamepad support |
| JavaFX | 21 | Launcher/configuration UI |
| JavaCV | 1.5.10 | Computer vision framework |
| FFmpeg | 6.1.1-1.5.10 | Video/BGA playback |
| Commons Compress | 1.26.0 | Archive handling (ZIP, 7z) |
| Commons DBUtils | 1.8.1 | Database utilities |
| SQLite JDBC | 3.45.1.0 | Song database connectivity |
| Twitter4J | 4.0.4 | Twitter integration |
| JUnit Jupiter | 5.10.2 | Unit testing framework |

### Local Dependencies (`lib/` directory)
| Library | File | Description |
| :--- | :--- | :--- |
| JBMS Parser | `jbms-parser.jar` | BMS file format parser |
| JBMS Table Parser | `jbmstable-parser.jar` | BMS table (difficulty) parser |
| LuaJ | `luaj-jse-3.0.2-custom.jar` | Lua scripting for skins |
| JPortAudio | `jportaudio.jar` | PortAudio native bindings |
| jFLAC | `jflac-codec-1.5.3.jar` | FLAC audio decoder |
| Discord RPC | `java-discord-rpc-2.0.1-all.jar` | Discord Rich Presence |

## Project Directory Structure

```
beatoraja/
├── src/                          # Source code
│   ├── bms/
│   │   ├── model/                # BMS data model and parsing
│   │   ├── player/
│   │   │   └── beatoraja/        # Main application package
│   │   │       ├── MainLoader.java      # Entry point
│   │   │       ├── MainController.java  # Core game logic
│   │   │       ├── arena/        # Arena Mode (multiplayer)
│   │   │       ├── audio/        # Audio drivers (PCM, loaders)
│   │   │       ├── config/       # Configuration management
│   │   │       ├── input/        # Input handling
│   │   │       ├── ir/           # Internet Ranking
│   │   │       ├── manager/      # Extracted managers
│   │   │       │   ├── UpdateManager.java
│   │   │       │   ├── ScreenshotManager.java
│   │   │       │   ├── InputManager.java
│   │   │       │   └── DownloadManager.java
│   │   │       ├── pattern/      # Chart pattern analysis
│   │   │       ├── play/         # Gameplay logic
│   │   │       ├── result/       # Score/result screens
│   │   │       ├── select/       # Song selection
│   │   │       ├── skin/         # Skin system
│   │   │       ├── song/         # Song database (SQLite)
│   │   │       └── stepup/       # Step-Up Mode
│   │   └── tool/                 # Utility tools
│   ├── glsl/                     # OpenGL shaders
│   ├── resources/                # Resource files
│   └── test/                     # Unit tests (JUnit 5)
│       └── java/
├── lib/                          # Local JAR dependencies
├── skin/                         # Skin resources (Lua, JSON, images)
├── docs/                         # Documentation
│   └── DASHBOARD.md              # This file
├── table/                        # BMS table configurations
├── font/                         # Font resources
├── natives/                      # Platform-specific native libraries
├── build.gradle                  # Gradle build configuration
├── settings.gradle               # Gradle project settings
├── VERSION.md                    # Current version (single source of truth)
├── CHANGELOG.md                  # Release history
├── ROADMAP.md                    # Feature roadmap
├── AGENTS.md                     # AI agent instructions
├── LLM_INSTRUCTIONS.md           # Universal LLM instructions
└── README.md                     # Project readme
```

## Build Commands

| Command | Description |
| :--- | :--- |
| `gradlew build` | Compile and package the application |
| `gradlew run` | Run the application |
| `gradlew test` | Run all unit tests |
| `gradlew test --tests "ClassName"` | Run a single test class |
| `gradlew clean` | Clean build artifacts |

## Recent Changes (0.9.3)
- **2025-12-28**:
  - Comprehensive documentation update (Dashboard, AGENTS.md).
  - Version synchronization across project.
  - Build verification on Gradle 8.5 / Java 21.

## Previous Releases Summary
- **0.9.2**: Osu! Background/Video support, Audio fixes.
- **0.9.1**: FLAC support, PCM refactoring, Gradle 8.5 migration.
- **0.9.0**: Arena Mode, Mod Menu, Step-Up Mode, CI/CD, LWJGL 3.
- **0.8.9**: Centralized versioning, build fixes.
- **0.8.8**: Initial modernization, LLM instructions.
