# Changelog

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
