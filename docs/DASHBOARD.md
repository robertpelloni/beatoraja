# Project Dashboard

## Project Status
| Property | Value |
|----------|-------|
| **Version** | 0.9.7 |
| **Date** | 2025-12-28 |
| **Build System** | Gradle 8.5 |
| **Java Version** | Java 21 |
| **Backend** | LWJGL 3 / LibGDX 1.12.1 |
| **Build Status** | ✅ Passing |
| **Test Status** | ✅ 13 test classes, 180+ tests passing |

## Repository Information
| Property | Value |
|----------|-------|
| **Origin** | https://github.com/robertpelloni/beatoraja.git |
| **Upstream** | https://github.com/exch-bms2/beatoraja.git |
| **Default Branch** | master |
| **Submodules** | None (dependencies via Gradle + `lib/`) |

---

## Feature Status Overview

### ✅ Fully Implemented
| Feature | Version | Package/Location |
|---------|---------|------------------|
| BMS Playback | Core | `play/`, `bms.model` |
| Osu! Support | 0.9.0 | `bms.model.OsuDecoder` |
| Arena Mode | 0.9.0 | `arena/`, `arena/net/` |
| Mission System | 0.9.2 | `mission/` |
| Step-Up Mode | 0.9.0 | `stepup/` |
| Mod Menu | 0.9.0 | `play/ui/` |
| In-Game Downloader | 0.9.0 | `manager/DownloadManager` |
| Fast/Slow Separation | 0.9.0 | `ScoreData`, `JudgeManager` |
| FLAC Audio | 0.9.1 | `audio/FlacProcessor` |
| Controller Hot-plug | 0.9.0 | `input/` |
| CI/CD Pipeline | 0.9.0 | `.github/workflows/` |

### 🔄 In Progress
| Feature | Notes |
|---------|-------|
| Audio Engine Improvements | Lower latency drivers |

### 📋 Planned
| Feature | Priority |
|---------|----------|
| Osu! Storyboard Events | Medium |
| Vulkan Rendering | Low |
| Theme Editor | Medium |

---

## Dependencies

### Gradle-Managed (Maven Central)
| Library | Version | Purpose |
|---------|---------|---------|
| LibGDX | 1.12.1 | Game framework |
| LibGDX LWJGL3 Backend | 1.12.1 | Modern OpenGL |
| LibGDX FreeType | 1.12.1 | Font rendering |
| LibGDX Controllers | 2.2.3 | Gamepad support |
| JavaFX | 21 | Configuration UI |
| JavaCV | 1.5.10 | Video processing |
| FFmpeg | 6.1.1-1.5.10 | Video/BGA playback |
| Commons Compress | 1.26.0 | Archive handling |
| Commons DBUtils | 1.8.1 | Database utilities |
| SQLite JDBC | 3.45.1.0 | Song database |
| Twitter4J | 4.0.4 | Twitter integration |
| JUnit Jupiter | 5.10.2 | Unit testing |

### Local Dependencies (`lib/`)
| Library | File | Purpose |
|---------|------|---------|
| JBMS Parser | `jbms-parser.jar` | BMS file parsing |
| JBMS Table Parser | `jbmstable-parser.jar` | Difficulty tables |
| LuaJ | `luaj-jse-3.0.2-custom.jar` | Lua skin scripting |
| JPortAudio | `jportaudio.jar` | PortAudio bindings |
| jFLAC | `jflac-codec-1.5.3.jar` | FLAC audio |
| Discord RPC | `java-discord-rpc-2.0.1-all.jar` | Discord integration |

---

## Project Directory Structure

```
beatoraja/
├── src/
│   ├── bms/
│   │   ├── model/                    # BMS/Osu data models
│   │   │   └── OsuDecoder.java       # Osu! file parser
│   │   ├── player/beatoraja/         # Main application
│   │   │   ├── MainLoader.java       # Entry point
│   │   │   ├── MainController.java   # Core game logic
│   │   │   ├── arena/                # Arena Mode
│   │   │   │   ├── net/              # Networking (Client/Server)
│   │   │   │   ├── ArenaManager.java
│   │   │   │   └── ArenaData.java
│   │   │   ├── audio/                # Audio system
│   │   │   │   ├── PCM.java          # Generic PCM base
│   │   │   │   ├── PCMLoader.java    # Format loaders
│   │   │   │   └── FlacProcessor.java
│   │   │   ├── manager/              # Extracted managers
│   │   │   │   ├── UpdateManager.java
│   │   │   │   ├── ScreenshotManager.java
│   │   │   │   ├── InputManager.java
│   │   │   │   └── DownloadManager.java
│   │   │   ├── mission/              # Mission system
│   │   │   │   ├── MissionManager.java
│   │   │   │   └── MissionData.java
│   │   │   ├── stepup/               # Step-Up mode
│   │   │   │   ├── StepUpManager.java
│   │   │   │   └── StepUpData.java
│   │   │   ├── play/                 # Gameplay
│   │   │   │   ├── JudgeManager.java # Judging logic
│   │   │   │   ├── LaneRenderer.java # Note rendering
│   │   │   │   └── bga/              # BGA processing
│   │   │   ├── select/               # Song selection
│   │   │   │   ├── MusicSelector.java
│   │   │   │   └── ArenaLobby.java
│   │   │   ├── skin/                 # Skinning system
│   │   │   │   ├── json/             # JSON skins
│   │   │   │   ├── lr2/              # LR2 skins
│   │   │   │   ├── lua/              # Lua skins
│   │   │   │   └── property/         # Skin properties
│   │   │   └── ...
│   │   └── tool/                     # Utilities
│   ├── glsl/                         # OpenGL shaders
│   ├── resources/                    # Resource files
│   └── test/java/                    # Unit tests
│       ├── bms/model/
│       │   ├── OsuDecoderTest.java
│       │   └── OsuDecoderBezierTest.java
│       └── bms/player/beatoraja/
│           ├── ScoreDataTest.java
│           └── audio/AudioTest.java
├── lib/                              # Local JAR dependencies
├── skin/                             # Skin resources
│   └── default/                      # Default skin
├── docs/                             # Documentation
├── table/                            # BMS table configs
├── .github/workflows/                # CI/CD
│   └── gradle.yml                    # GitHub Actions
├── build.gradle                      # Build configuration
├── VERSION.md                        # Version (0.9.7)
├── CHANGELOG.md                      # Release history
├── ROADMAP.md                        # Feature roadmap
├── HANDOFF.md                        # Session notes
├── AGENTS.md                         # AI agent instructions
└── LLM_INSTRUCTIONS.md               # Universal LLM guide
```

---

## Build Commands

| Command | Description |
|---------|-------------|
| `gradlew build` | Compile and package |
| `gradlew run` | Run application |
| `gradlew test` | Run all tests |
| `gradlew test --tests "ClassName"` | Run single test |
| `gradlew test --tests "ClassName.methodName"` | Run single method |
| `gradlew clean` | Clean build artifacts |
| `gradlew build --warning-mode all` | Build with deprecation warnings |

---

## Test Summary

| Test Class | Tests | Status |
|------------|-------|--------|
| `OsuDecoderTest` | 7 | ✅ Pass |
| `OsuDecoderBezierTest` | 1 | ✅ Pass |
| `AudioTest` | 22 | ✅ Pass |
| `ScoreDataTest` | 9 | ✅ Pass |
| `MissionManagerTest` | 9 | ✅ Pass |
| `StepUpManagerTest` | 12 | ✅ Pass |
| `ArenaDataTest` | 6 | ✅ Pass |
| `ArenaManagerTest` | 15 | ✅ Pass |
| `ConfigTest` | 11 | ✅ Pass |
| `PlayModeConfigTest` | 9 | ✅ Pass |
| `PlayerConfigTest` | 12 | ✅ Pass |
| `ClearTypeTest` | 7 | ✅ Pass |
| `ResolutionTest` | 5 | ✅ Pass |

---

## Recent Changes

### 0.9.6 (2025-12-28)
- ConfigTest, PlayModeConfigTest, ClearTypeTest, ResolutionTest added
- Osu! Hit Sound support confirmed
- 13 test classes with 180+ tests total

### 0.9.5 (2025-12-28)
- Osu! Timing Point improvements (BPM/SV)
- MissionManagerTest, StepUpManagerTest, ArenaTests added

### 0.9.4 (2025-12-28)
- Osu! Spinner support confirmed with test
- Comprehensive ROADMAP.md reorganization with package status
- Updated DASHBOARD.md with feature status overview

### 0.9.3 (2025-12-28)
- Fixed Gradle deprecation warnings (modern `application` block)
- Added `ScoreDataTest` with 9 test cases
- Comprehensive ROADMAP.md and DASHBOARD.md update
- Version synchronization across project

### 0.9.2 (2025-12-27)
- Osu! Background/Video support in `[Events]` section
- Audio fix: BGM on background channel, silent notes
- Mission System integration
- Arena Mode enhancements (rule sync, rank display)

### 0.9.1 (2025-12-27)
- FLAC audio support via jflac-codec
- PCM class hierarchy refactoring
- Gradle 8.5 + Java 21 migration
- LWJGL 2 → LWJGL 3 backend

### 0.9.0 (2025-12-26)
- Arena Mode with networking
- Osu! file support
- Mod Menu overlay
- Step-Up Mode
- In-Game Downloader
- CI/CD Pipeline
- MainController refactoring

---

## Next Priority: Osu! Storyboard Support

The next feature to implement is **storyboard support** in `OsuDecoder.java`:
- Parse storyboard elements from `[Events]` section
- Display background animations and sprites
- Coordinate with BGA system for unified rendering
