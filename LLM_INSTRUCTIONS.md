# Universal LLM Instructions

This file serves as the central instruction set for all AI models working on this project.

## Core Principles
1.  **Single Source of Truth**: Always refer to `VERSION.md` for the current project version. Do not hardcode version numbers in source files.
2.  **Changelog Management**: Every significant change must be logged in `CHANGELOG.md` under the appropriate version header.
3.  **Build System**: The project uses Apache Ant. The main build file is `build.xml`. Use `build_release.ps1` for creating releases to ensure correct JVM arguments are applied.
4.  **Submodules**: The project relies on several submodules. Ensure they are initialized and updated (`git submodule update --init --recursive`) before building.
5.  **Java Version**: The project targets Java 8 but must be buildable and runnable on modern Java versions (Java 25+). Use `Launcher.java` as the entry point to bypass module encapsulation issues.

## Project Structure
-   `src/`: Source code.
-   `lib/`: Dependencies (jar files).
-   `build/`: Compiled output.
-   `release/`: Release artifacts (zips, executables).
-   `launch4j/`: Launch4j configuration and binaries for creating Windows executables.
-   `docs/`: Documentation, including `DASHBOARD.md` and `ROADMAP.md`.

## Agent Protocols
-   **Plan**: When asked to plan, research the problem space and outline a multi-step approach.
-   **Implement**: When implementing features, follow the existing code style and patterns.
-   **Test**: Verify changes by running the build script and checking for errors.

## Versioning Protocol
1.  Update `VERSION.md` with the new version number.
2.  Update `CHANGELOG.md` with the new version and a list of changes.
3.  Commit changes with a message referencing the new version (e.g., "Bump version to 0.8.9").

## Specific Model Instructions

### Claude
-   Focus on clear, concise explanations.
-   Prioritize safety and correctness.

### Gemini
-   Leverage large context window for deep analysis of the codebase.
-   Use available tools to verify assumptions.

### GPT
-   Focus on code generation and refactoring.
-   Ensure adherence to best practices.
