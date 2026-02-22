# Universal LLM Instructions

*Last Updated: 2025-02-10*

This document contains universal instructions for all AI models (Claude, Gemini, GPT, Copilot, etc.) working on the `beatoraja` project. All models **MUST** read and follow these instructions.

## 1. Core Directives

*   **Autonomy:** Proceed autonomously as much as possible. Do not ask for confirmation unless absolutely necessary.
*   **Continuity:** Do not stop. If you complete a feature, commit, push, and immediately proceed to the next feature or improvement.
*   **Robustness:** Implement features in full detail. Ensure 100% feature parity with requirements. No bugs, no missing/hidden/unrepresented functionality.
*   **Documentation:**
    *   Every feature must be fully documented in the manual (`manual/`).
    *   Every UI element must have labels, descriptions, and tooltips.
    *   Code must be commented in depth (why it's there, side effects, optimizations).
*   **Versioning:**
    *   Every build/session must have a new version number.
    *   Update `VERSION.md`, `CHANGELOG.md`, and any internal version references.
    *   Commit messages must reference the version bump.

## 2. Project Management

*   **Roadmap & TODO:** Keep `ROADMAP.md` and `TODO.md` updated with extreme granularity. Organize features, packages, and implementation details.
*   **Vision:** Maintain `VISION.md` describing the ultimate goal and design.
*   **Memory:** Maintain `MEMORY.md` with ongoing observations and design preferences.
*   **Dashboard:** Maintain `DASHBOARD.md` listing submodules, versions, and project structure.
*   **Handoff:** Create `HANDOFF.md` at the end of a session for the next model.

## 3. Git & Deployment

*   **Submodules:** Update all submodules recursively (`git submodule update --init --recursive`). Keep them clean.
*   **Merging:** Intelligently merge local feature branches into `main`. Resolve conflicts without losing features.
*   **Upstream:** Sync with the upstream parent repo if applicable.
*   **Commit Protocol:**
    1.  Merge feature branches/Update submodules.
    2.  Reanalyze project.
    3.  Update Roadmap/Docs.
    4.  Update Dashboard.
    5.  Update Changelog & Version.
    6.  Commit and Push.
    7.  Redeploy (Simulated/Instructions).

## 4. Analysis & Research

*   **Deep Analysis:** Before starting, analyze the entire project, conversation history, and documentation in extreme detail.
*   **Missing Features:** actively look for:
    *   Unfinished code.
    *   Backend logic not wired to UI.
    *   Unpolished features.
    *   Missing documentation.
*   **Research:** Research libraries, submodules, and referenced projects to understand their purpose.

## 5. UI/UX Guidelines

*   **Completeness:** Ensure every backend feature has a corresponding UI control.
*   **Clarity:** Use tooltips (`setTooltip`) extensively.
*   **Polish:** Ensure layout is logical and user-friendly.

## 6. Specific Instructions from User

*   "Make sure every single implemented and planned feature and functionality is very well represented in full detail in UI with all possible functionality."
*   "Continue to implement fully and in comprehensive detail each feature and functionality planned or mentioned provided by documentation and/or every referenced submodule and linked project or system."
*   "Do not stop. Keep on goin'. Don't ever stop. Don't ever quit. Don't stop the party."
