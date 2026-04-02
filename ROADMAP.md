# Roadmap

## Version: 0.9.23 | Last Updated: 2025-02-10

---

## 🛠 Feature Parity & Stability Goals

### 1. User Interface Completeness
- [x] **Skin Preview**: Add a preview image pane to the Skin tab in the Launcher.
- [x] **Input Configuration**: Implement "Minimum input interval per device".
- [x] **Chart Preview**: Rendering highly optimized.
- [ ] **Theme Editor**: In-app tool to create/edit skins (Long term).

### 2. Gameplay Mechanics
- [ ] **Internet Ranking (IR)**: Polish the IR configuration and submission logic.
- [ ] **Replay System**: Enhance replay saving options; implement fast-forward/rewind during playback.
- [ ] **Step-Up Mode**: Fully integrate Step-Up progress into the Result Screen skin properties.

### 3. Rendering & Audio
- [ ] **Vulkan Backend**: Migration path for future LibGDX versions.
- [ ] **Lower Latency Audio**: ASIO/WASAPI exclusive mode investigation.

---

## ✅ Completed Features

### Osu! Support (0.9.0+)
- [x] **Osu! File Parsing**: `.osu` file decoder with mania mode support
- [x] **Dynamic Key Mapping**: 4K-9K support based on CircleSize
- [x] **Slider Curves**: Bezier and Linear curve approximation
- [x] **Background/Video Events**: Parse `[Events]` section for BGA
- [x] **Audio Handling**: BGM on background channel, silent notes
- [x] **Spinner Support**: Spinners mapped to scratch lane as long notes (0.9.4)
- [x] **Hit Sound Volume**: Configurable volume scaling for hit sounds (0.9.7).
- [x] **Background Dim**: Configurable brightness for BG/Video (0.9.7).
- [x] **Storyboard Support (Full)**:
    - [x] Data structures for Commands (Move, Fade, etc), Sprites, and Easing (0.9.10).
    - [x] `OsuDecoder` updated to parse `[Events]` into `StoryboardData` (0.9.10).
    - [x] `StoryboardRegistry` pattern to attach data to immutable `BMSModel`.
    - [x] `StoryboardRenderer`: Sprite Loading, Interpolation, Easing, Projection, Layers (0.9.11).
    - [x] **Refinement**: Rotation, Flip support, Robust resource handling (0.9.13).
    - [x] **Bug Fix**: ArrayOutOfBounds crash and start/end state referencing resolved (0.9.21).

### Launcher & Tools
- [x] **Course Editor (Full)**:
    - [x] UI: `CourseEditorView` with song search and drag-and-drop (0.9.10).
    - [x] Backend: Wired to `SongDatabaseAccessor` for real song queries (0.9.12).
    - [x] **Saving**: Export courses to `.crs` files (0.9.13).
    - [x] **Restoration**: Repaired catastrophic UI deletion (0.9.21).
- [x] **Replay Analysis (Full)**:
    - [x] UI: `ReplayAnalysisView` implemented with Histograms (0.9.16).
    - [x] Backend: Wired to load top 50 scores from DB via `SelectionChanged` (0.9.18).
- [x] **Arena Tab**: Configure Server IP, Port, and Player Name.
- [x] **Mission Tab**: View active/daily missions and toggle auto-accept.
- [x] **Osu! Tab**: Adjust Hit Sound Volume and Background Dim.
- [x] **Documentation**: Comprehensive HTML manuals in `manual/` directory.

### Skinning & UI
- [x] **New Skin Properties**: `NUMBER_MISSION_CURRENT`, `NUMBER_MISSION_TARGET`, `NUMBER_ARENA_PLAYERS_COUNT`, `NUMBER_ARENA_SCORE_DIFF`.
- [x] **Arena Score Difference**: Real-time score comparison with the top opponent exposed to skins.
- [x] **SkinNoteDistributionGraph (Optimization)**: Redesigned to only update active time columns, saving massive CPU/GPU resources (0.9.23).

### In-Game UI (Mod Menu)
- [x] **Osu! Settings**: Adjust Hit Sound Volume and Background Dim directly in-game.
- [x] **Arena Integration**:
    - [x] View status, connect/host, and sync settings with Launcher.
    - [x] **Arena Chat**: Real-time lobby chat in the Mod Menu (0.9.12). Thread-safe OpenGL updates (0.9.22).
- [x] **Mission Integration**: View active missions list.

### Core Gameplay
- [x] **BMS Playback**: Full support for BMS/BME/BML/PMS file formats
- [x] **Judging System**: PGREAT/GREAT/GOOD/BAD/POOR/MISS with Fast/Slow separation
- [x] **Gauge Types**: Groove, Hard, Ex-Hard, Hazard, Easy, and custom gauges
- [x] **Lane Cover / Hi-Speed**: Adjustable during gameplay
- [x] **Practice Mode**: Section-based practice with loop functionality
- [x] **Replay System**: Record and playback replays

### Arena Mode (Multiplayer)
- [x] **Networking**: TCP/JSON-based client/server architecture
- [x] **Song Synchronization**: `TYPE_SONG_SELECT` protocol for lobby sync
- [x] **Score Sync**: Real-time score sharing between players
- [x] **Rank Calculation**: Dynamic rank/points calculation
- [x] **Lobby UI**: Create/Join rooms via `ArenaLobby` (F5 in MusicSelector)
- [x] **Skin Integration**: `NUMBER_ARENA_RANK`, `NUMBER_ARENA_PLAYERS`
- [x] **Rule Syncing**: Synchronize game rules across players
- [x] **Disconnect Button**: Clean disconnect from Arena rooms
- [x] **Chat System**: Send/Receive messages in lobby (0.9.12).

### Mission System (0.9.2+)
- [x] **MissionManager**: Core mission tracking and completion logic
- [x] **Daily Missions**: Time-based mission rotation
- [x] **Normal Missions**: Persistent achievement-style missions
- [x] **Mod Menu Integration**: Mission tab in overlay
- [x] **Auto-Accept**: Configurable option to automatically accept missions (0.9.7).

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
- [x] **Unit Testing**: JUnit 5 framework
- [x] **Centralized Versioning**: `VERSION.md` as single source of truth

---

## 📈 Version History

| Version | Date | Highlights |
|---------|------|------------|
| 0.9.23 | 2025-02-10 | Skin Graph Render Optimization, Final Documentation |
| 0.9.22 | 2025-02-10 | Crash Fixes (Osu Parsing, OpenGL Threading), UI Recovery |
| 0.9.18 | 2025-02-10 | Replay Analysis DB Integration |
| 0.9.13 | 2025-02-10 | Course Editor Saving, Storyboard Refinement |
| 0.9.12 | 2025-02-10 | Arena Chat, Course Editor backend logic |
| 0.9.11 | 2025-02-10 | Full Osu! Storyboard rendering (Easing, Interpolation, Sorting) |
| 0.9.6 | 2025-12-28 | ConfigTest, PlayModeConfigTest, Hit Sound support, 180+ tests |