# Beatoraja - Agent Instructions

See [LLM_INSTRUCTIONS.md](LLM_INSTRUCTIONS.md) for universal instructions.

## Build Commands
- **Build**: `./gradlew build`
- **Run**: `./gradlew run`
- **Test all**: `./gradlew test`
- **Single test**: `./gradlew test --tests "ClassName"` or `./gradlew test --tests "ClassName.methodName"`
- **Clean**: `./gradlew clean`

## Architecture
- **Java 21** with **LibGDX 1.12.1** game framework and **LWJGL 3** backend
- **Database**: SQLite (`songdata.db`) via sqlite-jdbc
- **Packages**: `bms.player.beatoraja` (main), `bms.model` (BMS parsing), `bms.tool` (utilities)
- **Entry point**: `bms.player.beatoraja.MainLoader`
- **Skins**: Lua scripts in `skin/` directory
- **Local libs**: `lib/` contains jbms-parser, luaj, jportaudio, jflac

## Code Style
- **Encoding**: UTF-8
- **Naming**: camelCase for methods/variables, PascalCase for classes
- **Imports**: Explicit imports preferred (no wildcards)
- **Versioning**: Update `VERSION.md` and `CHANGELOG.md` for releases (never hardcode versions)
- **Testing**: JUnit 5 (Jupiter) in `src/test/java/`
