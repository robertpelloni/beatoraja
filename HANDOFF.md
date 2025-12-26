# Session Handoff Document
**Date**: December 25, 2025
**Project**: beatoraja
**Current Version**: 0.8.9

## Executive Summary
This session focused on modernizing the build process, implementing a robust versioning system, and consolidating documentation for AI agents. The project is now buildable on Java 25 using a new `Launcher` class and updated Ant scripts.

## Key Achievements
1.  **Build System Repair**:
    -   Fixed `package javafx.* does not exist` errors by manually downloading JavaFX 21 jars to `lib/`.
    -   Resolved `java.lang.reflect.InaccessibleObjectException` in Launch4j by adding `--add-opens` flags in `build_release.ps1`.
    -   Created `src/bms/player/beatoraja/Launcher.java` to bypass Java 9+ module encapsulation checks.
    -   Updated `build.xml` to include new dependencies and the `Launcher` class.

2.  **Versioning System**:
    -   Established `VERSION.md` as the single source of truth.
    -   Updated `MainController.java` to read the version from `VERSION.md` at runtime.
    -   Updated `build.xml` to read the version from `VERSION.md` at build time.

3.  **Documentation Overhaul**:
    -   Created `LLM_INSTRUCTIONS.md` as a central hub for agent protocols.
    -   Updated `CLAUDE.md`, `GEMINI.md`, and `GPT.md` to reference the central instructions.
    -   Created `docs/DASHBOARD.md` to provide a high-level project overview.

## Current State
-   **Build**: Passing. Run `.\build_release.ps1` to generate release artifacts in `release/`.
-   **Version**: 0.8.9.
-   **Submodules**: Updated (though none are explicitly defined in `.gitmodules`).

## Next Steps
1.  **Testing**: Run the generated executable on a clean Windows machine to verify JavaFX runtime availability.
2.  **CI/CD**: Integrate `build_release.ps1` into a CI pipeline (e.g., GitHub Actions).
3.  **Feature Development**: Continue implementing features from `ROADMAP.md`.

## Artifact Locations
-   **Windows Release**: `release/windows/beatoraja_0.8.9_windows.zip`
-   **Linux Release**: `release/linux/beatoraja_0.8.9_linux.zip`

## Known Issues
-   **Git Push**: The final `git push` failed due to permission errors (403). The changes are committed locally on the `master` branch. You will need to authenticate or use a different remote to push the changes.
