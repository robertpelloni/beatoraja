# Universal LLM Instructions

This file serves as the central instruction set for all AI models working on this project.

## Core Principles
1.  **Single Source of Truth**: Always refer to `VERSION.md` for the current project version. Do not hardcode version numbers in source files.
2.  **Changelog Management**: Every significant change must be logged in `CHANGELOG.md` under the appropriate version header.
3.  **Build System**: The project uses **Gradle**. The main build file is `build.gradle`.
    -   Use `./gradlew build` (or `gradle build`) to compile and package.
    -   Target Java Version: **Java 21**.
4.  **Dependencies**: Dependencies are managed via Gradle and the `lib/` folder.
5.  **Backend**: The project uses **LWJGL 3** and **LibGDX 1.12.1**.

## Project Structure
-   `src/`: Source code.
-   `lib/`: Local dependencies (jar files).
-   `build/`: Compiled output (Gradle).
-   `docs/`: Documentation, including `DASHBOARD.md` and `ROADMAP.md`.
-   `skin/`: Skin resources (Lua scripts, JSON, images).

## Agent Protocols
-   **Plan**: When asked to plan, research the problem space and outline a multi-step approach.
-   **Implement**: When implementing features, follow the existing code style and patterns.
-   **Test**: Verify changes by running the build script and checking for errors.

## Versioning Protocol
1.  Update `VERSION.md` with the new version number (e.g., `0.9.0`).
2.  Update `CHANGELOG.md` with the new version and a list of changes.
3.  Commit changes with a message referencing the new version (e.g., "Bump version to 0.9.0").

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

### GitHub Copilot
-   Use the provided tools to explore the codebase and implement features.
-   Follow the "Plan -> Implement -> Verify" cycle.
-   Keep the `HANDOFF.md` updated with session progress.
