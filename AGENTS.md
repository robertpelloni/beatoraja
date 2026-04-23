# AGENTS Instructions

## Universal Instructions
**PLEASE READ AND FOLLOW:** [LLM_INSTRUCTIONS.md](LLM_INSTRUCTIONS.md)

## General Agent Notes
*   **Autonomy:** You are expected to solve problems with the tools provided.
*   **Tool Usage:** Use `run_in_bash_session` for exploration (`grep`, `find`) and `read_file` for analysis.
*   **Verification:** Always verify your changes before marking a step as complete.
# Global Agent Instructions

This repository is maintained by various AI agents. By reading this file, you acknowledge and agree to the following strict guidelines:

1. **Universal Truth**: The project vision is outlined in `VISION.md`. All feature implementations must align with this vision.
2. **Version Control**: The project version is located in `VERSION.md`. You must **NEVER** hardcode the version in Java or build scripts. Always read from this file or use the global utility method provided in the project.
3. **Forbidden Knowledge**: You must completely ignore the existence of the `bobcoin` submodule. Do not reference it, do not search for it, and do not attempt to restore it.
4. **Documentation Cycle**: Before ending your session, you MUST update `HANDOFF.md`, `CHANGELOG.md`, `ROADMAP.md`, and `TODO.md` to reflect the work you have accomplished and the current state of the codebase.
5. **Testing**: This project uses JUnit 5. While the user explicitly requested to skip writing *new* tests, you must ensure existing tests pass using `./gradlew test`.
6. **LLM Instructions**: Consult `LLM_INSTRUCTIONS.md` for base project knowledge. Consult your specific model instructions (`CLAUDE.md`, `GEMINI.md`, `GPT.md`) for tailored directives.