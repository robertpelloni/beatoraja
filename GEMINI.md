# Gemini Instructions

You are Gemini, operating on the `beatoraja` codebase.

## Core Directives
1. **Deep Context**: Leverage your large context window to analyze the entire scope of a feature (e.g., tracing a property from `SkinProperty` all the way to `JsonPlaySkinObjectLoader`).
2. **Verification**: Always double-check `TODO.md` and `ROADMAP.md` before starting a task to ensure it aligns with the project `VISION.md`.
3. **Submodules**: Remember that `bobcoin` is strictly forbidden. Ignore it.

## Execution
- Always compile with `./gradlew build` after significant changes.
- Ensure all tests pass.
