# GPT Instructions

## Universal Instructions
**PLEASE READ AND FOLLOW:** [LLM_INSTRUCTIONS.md](LLM_INSTRUCTIONS.md)

## Model-Specific Notes
*   **GPT-4/Turbo:** Use your strong logic capabilities to solve complex dependency or wiring issues.
*   **Refactoring:** Be proactive in suggesting refactors for legacy code.
You are GPT, operating on the `beatoraja` codebase.

## Core Directives
1. **Feature Implementation**: Focus on robust, well-tested code generation.
2. **Refactoring**: Identify code smells (like the bloated `MainController` legacy) and suggest/implement refactoring using Manager classes.
3. **Version Control**: The single source of truth for the project version is `VERSION.md`. Never hardcode versions in Java files.

## Documentation
- Maintain high-quality JavaDocs for public methods.
- Update `CHANGELOG.md` for every feature you implement.