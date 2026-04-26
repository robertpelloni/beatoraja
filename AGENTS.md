# Beatoraja - Agent Instructions

See [LLM_INSTRUCTIONS.md](LLM_INSTRUCTIONS.md) for universal instructions.

## Project Overview
- **Type**: Java 21 Application (Gradle)
- **Frameworks**: 
  - **LibGDX 1.12.1** (Game Engine, LWJGL3 Backend)
  - **JavaFX 21** (Launcher/Config UI)
  - **SQLite** (Data Storage)
- **Platform**: Cross-platform (Windows, Linux, macOS), though current environment is Windows.

## Build & Run Commands
- **Build**: `./gradlew build`
- **Run**: `./gradlew run`
- **Test All**: `./gradlew test`
- **Single Test**: `./gradlew test --tests "bms.player.beatoraja.ConfigTest"`
- **Clean**: `./gradlew clean`

## Project Structure
- **Source**: `src/` contains BOTH Java source code and resources (images, shaders, properties).
  - **Note**: `build.gradle` defines `src` as both java and resource dir.
- **Tests**: `src/test/java/` (JUnit 5).
- **Local Libs**: `lib/` contains critical dependencies not in Maven Central (jbms-parser, luaj-custom, etc.).
- **Skins**: `skin/` directory contains Lua scripts and assets for game skins.
- **Config**: Root directory contains `.json` config files (e.g., `config_sys.json`).

## Key Packages & Classes
- **Entry Point**: `bms.player.beatoraja.MainLoader` (JavaFX App -> Launches LibGDX)
- **Core Logic**: `bms.player.beatoraja`
  - `BMSPlayer`: Main gameplay state and logic.
  - `Config`: Configuration management (`config_sys.json`).
  - `MainController`: Bridge between system and game states.
- **Data Models**: `bms.player.beatoraja.song`
  - `SongData`: Represents a BMS song entry.
  - `SQLiteSongDatabaseAccessor`: Database operations.
- **Skins**: `bms.player.beatoraja.skin`
  - Handles Lua (`.lua`) and JSON skin parsing/rendering.

## Development Patterns
- **Architecture**: Hybrid JavaFX (Setup/Launcher) and LibGDX (Game Loop).
- **Database**: SQLite accessed via JDBC. `songdata.db` is the main database.
- **Audio/Video**: Uses custom drivers wrapping PortAudio/OpenAL and FFmpeg (via JavaCV).
- **Style**:
  - **Encoding**: UTF-8 (Strictly enforced).
  - **Naming**: PascalCase classes, camelCase methods/vars.
  - **Imports**: Explicit imports preferred over wildcards.

## Critical Gotchas
1.  **Resources in Source**: Non-java files in `src/` are production resources. Do not delete or move them without updating `build.gradle`.
2.  **Local Dependencies**: The project relies on JARs in `lib/`. Ensure `flatDir` repository is preserved in build scripts.
3.  **Dual UI Context**: Code running in `MainLoader` (JavaFX thread) behaves differently from code in `BMSPlayer` (LibGDX/OpenGL thread). Be careful with thread safety and context context.
4.  **BMS Format**: Parsing logic is largely in the external `jbms-parser` library, not the main source tree.


THE ULTIMATE BEATMANIA IIDX SIMULATOR THE DEFINITIVE BEATMANIA PIANO GAME CLICKY KEY MUSIC GAME THING ALL OF IT MORE REAL THAN REAL

every feature from every single beatmania game in one, oh my gosh
infinitas but way way better

research in depth every game beatmania og beatmania III whatever cram it all in there

every little detail, it supports everything, beautiful themes, beautiful transitions, beautiful gameplay, so smooth, so optimized, so lightweight, yet so detailed, a masterpiece of engineering

supports every single feature and detail from sound voltex and dj max and popn music and every other bemani game and every other music game, oh my gosh it is the ULTIMATE in every way, i can't believe IT
research in depth every single iidx version and detailed changelog of differences between versions, every detail we implemented, we can just copy this right onto the arcade game itself and everyone would be happy, it's so good

then also it's a piano playing game too, it teaches you how to play the piano and read sheet music, it's insane, it's the best game ever made in every possible way

i love you