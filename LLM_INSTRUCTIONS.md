# Universal LLM Instructions

This file contains universal instructions for AI models working on this project.

## Project Overview
beatoraja is a cross-platform BMS (Be-Music Source) player. It is written in Java and uses LibGDX.

## Versioning
- The current version is stored in the `VERSION` file in the root directory.
- When updating the version:
  1. Update the `VERSION` file.
  2. Update `src/bms/player/beatoraja/MainController.java`.
  3. Update `build.xml`.
  4. Update `CHANGELOG.md`.
  5. Commit with a message referencing the new version.

## Changelog
- Maintain a detailed changelog in `CHANGELOG.md`.
- Follow the format: `## [Version] - Date`.

## Code Style
- Follow standard Java coding conventions.
- Use meaningful variable names.
- Document complex logic.

## Submodules
- This project currently does not use git submodules in the root directory.
- If submodules are added, list them in `docs/DASHBOARD.md`.

## Agents
- **Plan**: Researches and outlines multi-step plans.
