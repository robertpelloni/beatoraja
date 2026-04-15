# Changelog

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
