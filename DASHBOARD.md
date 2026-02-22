# Project Dashboard

*Last Updated: 2025-02-10*

## Overview
**beatoraja** is a cross-platform rhythm game simulator based on the BMS format (and supporting Osu! formats). It uses Java (LibGDX) and Gradle.

## Project Structure

*   `src/`: Source code root.
    *   `bms/model/`: Core data models (BMSModel, TimeLine, Note, OsuDecoder).
    *   `bms/player/beatoraja/`: Main application logic.
        *   `launcher/`: JavaFX-based configuration launcher (PlayConfigurationView, CourseEditorView).
        *   `play/`: In-game engine (BMSPlayer, JudgeManager, BGAProcessor).
        *   `song/`: Song database management (SongData, SongDatabaseAccessor).
        *   `audio/`: Audio engine (AudioDriver, SoundManager).
        *   `skin/`: Skinning engine (Skin, SkinLoader, SkinObject).
        *   `arena/`: Multiplayer logic (ArenaManager, ArenaClient).
*   `manual/`: HTML user documentation.
*   `lib/`: Local library dependencies.
*   `skin/`: Default skins.

## Versions

*   **Current Version:** 0.9.13 (See `VERSION.md`)
*   **Java Version:** Java 21 LTS
*   **Build System:** Gradle 8.5

## Submodules / External References

*   *No Git submodules currently registered in the root.*
*   **Dependencies (Gradle):**
    *   LibGDX 1.12.1
    *   Twitter4J 4.0.4 (Core)
    *   SQLite JDBC
    *   Jackson (JSON)
    *   OpenJFX

## Key Documentation

*   [LLM_INSTRUCTIONS.md](LLM_INSTRUCTIONS.md) - **READ THIS FIRST**
*   [VISION.md](VISION.md) - Project Goals
*   [ROADMAP.md](ROADMAP.md) - Feature Status
*   [CHANGELOG.md](CHANGELOG.md) - History
