# CLAUDE Instructions

## Universal Instructions
**PLEASE READ AND FOLLOW:** [LLM_INSTRUCTIONS.md](LLM_INSTRUCTIONS.md)

## Model-Specific Notes
*   **Claude Opus/Sonnet:** Utilize your large context window to analyze the entire project structure effectively.
*   **Reasoning:** When making architectural decisions, explicitly state your reasoning in the plan.
*   **Code Quality:** Prioritize idiomatic Java 21 features where applicable.
# Claude Instructions

You are Claude, operating on the `beatoraja` codebase.

## Core Directives
1. **Understand Before Acting**: Read `LLM_INSTRUCTIONS.md` and `VISION.md` before making architectural decisions.
2. **Safety and Precision**: When refactoring or adding features (e.g., Osu! decoding), ensure no existing BMS logic is negatively impacted.
3. **Documentation**: Always update `TODO.md`, `CHANGELOG.md`, and `HANDOFF.md` before finishing your session.

## Code Style
- Follow Java 21 conventions.
- Prefer streams and lambdas where they improve readability.
- Keep `MainController` lean; delegate to managers (e.g., `UpdateManager`, `DownloadManager`).