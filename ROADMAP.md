# Roadmap

## Version: 0.9.5 | Last Updated: 2025-12-28

---

## ✅ Completed Features

### Core Gameplay
- [x] **BMS Playback**: Full support for BMS/BME/BML/PMS file formats
- [x] **Judging System**: PGREAT/GREAT/GOOD/BAD/POOR/MISS with Fast/Slow separation
- [x] **Gauge Types**: Groove, Hard, Ex-Hard, Hazard, Easy, and custom gauges
- [x] **Lane Cover / Hi-Speed**: Adjustable during gameplay
- [x] **Practice Mode**: Section-based practice with loop functionality
- [x] **Replay System**: Record and playback replays

### Osu! Support (0.9.0+)
- [x] **Osu! File Parsing**: `.osu` file decoder with mania mode support
- [x] **Dynamic Key Mapping**: 4K-9K support based on CircleSize
- [x] **Slider Curves**: Bezier and Linear curve approximation
- [x] **Background/Video Events**: Parse `[Events]` section for BGA
- [x] **Audio Handling**: BGM on background channel, silent notes
- [x] **Spinner Support**: Spinners mapped to scratch lane as long notes (0.9.4)

### Arena Mode (Multiplayer)
- [x] **Networking**: TCP/JSON-based client/server architecture
- [x] **Song Synchronization**: `TYPE_SONG_SELECT` protocol for lobby sync
- [x] **Score Sync**: Real-time score sharing between players
- [x] **Rank Calculation**: Dynamic rank/points calculation
- [x] **Lobby UI**: Create/Join rooms via `ArenaLobby` (F5 in MusicSelector)
- [x] **Skin Integration**: `NUMBER_ARENA_RANK`, `NUMBER_ARENA_PLAYERS`
- [x] **Rule Syncing**: Synchronize game rules across players
- [x] **Disconnect Button**: Clean disconnect from Arena rooms

### Mission System (0.9.2+)
- [x] **MissionManager**: Core mission tracking and completion logic
- [x] **Daily Missions**: Time-based mission rotation
- [x] **Normal Missions**: Persistent achievement-style missions
- [x] **Mod Menu Integration**: Mission tab in overlay

### Step-Up Mode (0.9.0+)
- [x] **StepUpManager**: Level progression system (Levels 1-12)
- [x] **StepUpData**: Persistence to `stepup.json`
- [x] **Dynamic Courses**: Generate courses based on current level
- [x] **Progression Logic**: Level up on clear, level down on fail

### Mod Menu (0.9.0+)
- [x] **In-Game Overlay**: Scene2D-based UI (F5 key)
- [x] **Hi-Speed Control**: Adjust during gameplay
- [x] **Lane Cover Control**: Adjust during gameplay
- [x] **Arena Tab**: Connection management
- [x] **Mission Tab**: View active missions

### In-Game Downloader (0.9.0+)
- [x] **DownloadManager**: Background download handling
- [x] **Crawler Integration**: Download from BMS Search URLs
- [x] **Archive Extraction**: ZIP and TAR.GZ support
- [x] **Zip Slip Protection**: Security fix for path traversal

### Audio System
- [x] **PCM Refactoring**: Generic `PCM<T>` base class hierarchy
- [x] **Format Support**: WAV, MP3, OGG, FLAC
- [x] **Audio Drivers**: GdxSoundDriver, PortAudioDriver
- [x] **FlacProcessor**: FLAC decoding via jflac-codec

### Skinning System
- [x] **Lua Skins**: Full Lua scripting support
- [x] **JSON Skins**: LR2-compatible JSON skin format
- [x] **LR2 Skin Support**: CSV-based LR2 skin loading
- [x] **SkinRadarGraph**: Dynamic radar visualization
- [x] **Fast/Slow Display**: `NUMBER_FAST_NOTES`, `NUMBER_SLOW_NOTES`
- [x] **Hit Error Visualizer**: Timing distribution graphs

### Input System
- [x] **Keyboard Support**: Full keyboard mapping
- [x] **Controller Support**: Gamepad/joystick support
- [x] **Hot-Plugging**: Connect/disconnect controllers at runtime
- [x] **Input Manager**: Extracted from MainController

### Build & Infrastructure
- [x] **Gradle Migration**: Ant → Gradle 8.5
- [x] **Java 21**: Modern Java target
- [x] **LWJGL 3**: Backend upgrade from LWJGL 2
- [x] **LibGDX 1.12.1**: Latest stable game framework
- [x] **CI/CD**: GitHub Actions workflow
- [x] **Unit Testing**: JUnit 5 framework with 6 test classes
  - `MissionManagerTest`: 9 tests for mission system
  - `StepUpManagerTest`: 12 tests for step-up mode
- [x] **Centralized Versioning**: `VERSION.md` as single source of truth

### Code Quality
- [x] **MainController Refactoring**: Extracted to 4 manager classes
  - `UpdateManager`: Song database and table updates
  - `ScreenshotManager`: Screenshot capture and Twitter integration
  - `InputManager`: Input polling and handling
  - `DownloadManager`: Background downloads
- [x] **Gradle Deprecation Fixes**: Modern application plugin syntax

### Social & Integration
- [x] **Internet Ranking (IR)**: Score submission and leaderboards
- [x] **Discord RPC**: Rich Presence integration
- [x] **Twitter Integration**: Screenshot sharing
- [x] **IPFS Support**: Decentralized song distribution

### LR2 Compatibility
- [x] **LR2 Gauge/Judge Constants**: GaugeProperty additions
- [x] **Bad on Early Release**: LN judge behavior
- [x] **LR2 Skin Loader**: CSV skin format parsing

---

## 🔄 In Progress

### Audio Engine Improvements
- [ ] **Lower Latency Drivers**: Investigate ASIO/WASAPI exclusive mode
- [ ] **Additional Formats**: Consider OPUS support

---

## 📋 Planned Features

### Osu! Mode Enhancements
- [x] **Timing Points**: Inherited timing point handling (0.9.5)
- [ ] **Storyboard Events**: Parse and display storyboard elements
- [ ] **Hit Sound Support**: Per-note hit sounds

### Rendering Improvements
- [ ] **Vulkan Backend**: Future LibGDX Vulkan support
- [ ] **Shader Improvements**: Enhanced visual effects

### Quality of Life
- [ ] **Theme Editor**: In-app skin/theme creation tool
- [ ] **Replay Analysis**: Detailed replay statistics
- [ ] **Chart Preview**: Pre-play chart visualization

---

## 📦 Backlog

### Localization
- [ ] Additional language translations
- [ ] RTL language support

### Advanced Features
- [ ] **Course Editor**: Create custom courses in-app
- [ ] **Network Spectating**: Watch live Arena matches
- [ ] **Score Import/Export**: Migrate scores between installations

---

## 📊 Package Structure

| Package | Status | Description |
|---------|--------|-------------|
| `arena/` | ✅ Complete | Multiplayer Arena mode |
| `arena/net/` | ✅ Complete | TCP networking layer |
| `audio/` | ✅ Complete | Audio drivers and PCM handling |
| `config/` | ✅ Complete | Configuration management |
| `decide/` | ✅ Complete | Song decision screen |
| `external/` | ✅ Complete | External integrations |
| `input/` | ✅ Complete | Input processing |
| `ir/` | ✅ Complete | Internet Ranking |
| `launcher/` | ✅ Complete | JavaFX configuration UI |
| `manager/` | ✅ Complete | Extracted manager classes |
| `mission/` | ✅ Complete | Mission system |
| `pattern/` | ✅ Complete | Chart pattern analysis |
| `play/` | ✅ Complete | Core gameplay logic |
| `play/bga/` | ✅ Complete | BGA/Video processing |
| `play/ui/` | ✅ Complete | Play screen UI elements |
| `result/` | ✅ Complete | Result screen |
| `select/` | ✅ Complete | Music selection |
| `select/bar/` | ✅ Complete | Selection bar types |
| `skin/` | ✅ Complete | Skinning system |
| `skin/json/` | ✅ Complete | JSON skin loader |
| `skin/lr2/` | ✅ Complete | LR2 skin loader |
| `skin/lua/` | ✅ Complete | Lua skin scripting |
| `skin/property/` | ✅ Complete | Skin property mappings |
| `song/` | ✅ Complete | Song database |
| `stepup/` | ✅ Complete | Step-Up mode |
| `stream/` | ✅ Complete | Streaming support |

---

## 🧪 Test Coverage

| Test Class | Coverage |
|------------|----------|
| `OsuDecoderTest` | Slider duration, Background, Audio, Video parsing |
| `OsuDecoderBezierTest` | Bezier curve approximation |
| `AudioTest` | WAV loading verification |
| `ScoreDataTest` | EXScore, JudgeCount, Fast/Slow tracking |
| `MissionManagerTest` | 9 tests - mission tracking, completion, daily/normal missions |
| `StepUpManagerTest` | 12 tests - level progression, persistence, course generation |

---

## 📈 Version History

| Version | Date | Highlights |
|---------|------|------------|
| 0.9.5 | 2025-12-28 | Timing point support, MissionManagerTest, StepUpManagerTest |
| 0.9.3 | 2025-12-28 | Documentation overhaul, Gradle fixes, ScoreDataTest |
| 0.9.2 | 2025-12-27 | Osu! Background/Video, Mission System, Arena enhancements |
| 0.9.1 | 2025-12-27 | FLAC support, PCM refactoring, Gradle 8.5 |
| 0.9.0 | 2025-12-26 | Arena Mode, Osu! Support, Mod Menu, Step-Up, CI/CD |
| 0.8.9 | 2025-12-25 | Centralized versioning, build fixes |
| 0.8.8 | 2025-12-25 | Initial modernization |
