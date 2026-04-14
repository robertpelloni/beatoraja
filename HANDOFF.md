# Session Handoff: Deep Codebase Analysis & Documentation Overhaul

## Current State & Analysis
- **Version:** 0.9.7 (Managed via `VERSION.md`).
- **Build System:** Gradle 8.5 targeting Java 21.
- **Dependencies:** LibGDX 1.12.1, LWJGL 3, SQLite.
- **Submodules:** The `bobcoin` submodule and all references have been strictly removed as per user instruction. `lr2oraja-endlessdream` remains.

## What Was Accomplished During This Analysis Phase
- **bobcoin Purge:** Fully removed `bobcoin` from `.gitmodules` and deleted the directory.
- **Documentation Bootstrapping:** Created base files for `TODO.md`, `VISION.md`, `MEMORY.md`, `DEPLOY.md`, and `IDEAS.md`.
- **Version Tracking Verification:** Confirmed `MainLoader.java` and `MainController.java` currently hardcode "beatoraja 0.6". This will be fixed in the upcoming "Global Version System" step to parse `VERSION.md`.

## Outstanding TODOs Discovered (Codebase grep)
- `PlayDataAccessor.java`: Needs logic for handling FLIP modifiers and Battle separate hashes.
- `IntegerPropertyFactory.java`: IDs 64-69 should be allocated for target2/target3 (Pacemaker).
- `SkinNoteDistributionGraph.java`: Needs texture batching to reduce `bindTexture` calls.
- `BGAProcessor.java`: Missing full Event Layer support (currently only handles MISS layers).
- `OsuDecoder.java`: `[Events]` section parsing exists for background/video, but full Storyboard (Sprites, Animations) mapping into `BGAEvent` is incomplete.
- `GdxSoundDriver.java`: Needs a method to check if a sound is currently playing.

## Next Steps
1. Refactor AI instructions (`CLAUDE.md`, `GEMINI.md`, etc.).
2. Refactor `MainController.java` to dynamically load the version string.
3. Select a feature (likely Osu! Storyboards or fixing the TODOs above) and implement it.
