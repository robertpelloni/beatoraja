# Project Dashboard

## Project Status
- **Version**: 0.8.9
- **Date**: 2025-12-25
- **Build System**: Ant (`build.xml`)
- **Java Version**: Java 8 (Target), Java 25 (Compatible via `Launcher.java`)

## Submodules
*Note: This project uses submodules, but they are currently not configured in `.gitmodules` in the root directory. The following are key dependencies managed as libraries or potential submodules:*

- **libgdx**: Game development framework.
- **launch4j**: Executable wrapper.

## Project Structure

- **src/**: Source code for the application.
  - **bms/player/beatoraja/**: Main package.
    - **Launcher.java**: Entry point for modern Java versions.
    - **MainLoader.java**: Legacy entry point.
    - **audio/**: Audio driver implementations.
    - **config/**: Configuration management.
    - **play/**: Gameplay logic.
    - **skin/**: Skinning system.
    - **song/**: Song database and management.
- **build.xml**: Ant build script.
- **build_release.ps1**: PowerShell script for building releases with correct JVM arguments.
- **launch4j/**: Configuration for creating Windows executables.
- **lib/**: External libraries (jar files), including JavaFX 21.
- **skin/**: Default skins and resources.
- **docs/**: Documentation files.
- **VERSION.md**: Single source of truth for project version.
- **CHANGELOG.md**: Record of all notable changes.
- **LLM_INSTRUCTIONS.md**: Unified instructions for AI agents.

## Recent Changes
See [CHANGELOG.md](../CHANGELOG.md) for details.
