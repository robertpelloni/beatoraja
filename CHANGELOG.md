# Changelog

## [0.9.8] - 2025-02-10

### Added
- **Mod Menu**: Added in-game sliders for Osu! Hit Sound Volume and Background Dim.
- **Mod Menu**: Updated Arena and Mission windows to synchronize settings with `PlayerConfig` directly.

### Changed
- **Config**: Bumped version to 0.9.8.

## [0.9.7] - 2025-02-10

### Added
- **Launcher**: Added "Arena", "Mission", and "Osu!" tabs to the configuration launcher.
- **Launcher**: Added comprehensive tooltips to all configuration options.
- **Documentation**: Created `manual/` directory with detailed HTML user manuals.
- **Documentation**: Created `VISION.md`, `DASHBOARD.md`, and `LLM_INSTRUCTIONS.md`.
- **Infrastructure**: Centralized agent instructions and versioning protocol.

### Changed
- **Config**: Updated `PlayerConfig.java` to support Arena (IP/Port), Osu! (Volume/Dim), and Mission (Auto-accept) settings.
- **Launcher**: Integrated new configuration views into the main `PlayConfigurationView`.
- **Engine**: Wired up new settings:
    - Arena settings connected to `ArenaManager`.
    - Osu! Hit Sound Volume applied in `JudgeManager` and `KeySoundProcessor`.
    - Osu! Background Dim applied in `BGAProcessor`.
    - Mission Auto-Accept applied in `MissionManager`.

## [0.9.6] - 2025-12-28
- ConfigTest, PlayModeConfigTest, Hit Sound support, 180+ tests.

## [0.9.5] - 2025-12-28
- Timing point support, MissionManagerTest, StepUpManagerTest.
