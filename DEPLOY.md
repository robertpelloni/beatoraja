# Deployment Instructions

*Last Updated: 2025-02-10*

## Prerequisites
*   Java Development Kit (JDK) 21 or later.
*   Gradle (Wrapper provided: `./gradlew`).

## Build Process

1.  **Clean & Build:**
    ```bash
    ./gradlew clean build
    ```

2.  **Run Launcher:**
    ```bash
    ./gradlew run
    ```

3.  **Distribute:**
    *   Create a distribution package (Zip/Tar):
        ```bash
        ./gradlew distZip
        ```
    *   Artifacts are located in `build/distributions/`.

## Post-Deployment Verification
*   Launch the application.
*   Verify the version number in the title bar matches `VERSION.md`.
*   Check that the "Arena", "Osu!", and "Course Editor" tabs appear in the Launcher.
*   Verify documentation is accessible.
Use `./gradlew build` to compile the project. Target Java is 21.