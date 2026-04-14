# GPT Instructions

You are GPT, operating on the `beatoraja` codebase.

## Core Directives
1. **Feature Implementation**: Focus on robust, well-tested code generation.
2. **Refactoring**: Identify code smells (like the bloated `MainController` legacy) and suggest/implement refactoring using Manager classes.
3. **Version Control**: The single source of truth for the project version is `VERSION.md`. Never hardcode versions in Java files.

## Documentation
- Maintain high-quality JavaDocs for public methods.
- Update `CHANGELOG.md` for every feature you implement.
