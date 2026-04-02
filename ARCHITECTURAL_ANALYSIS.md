# Architectural Analysis

## Core Dependencies & Project Structure

The project `beatoraja` relies heavily on external libraries, some of which are provided as JARs in the `lib/` directory rather than source dependencies. This fundamentally impacts how features can be extended.

### 1. `jbms-parser` (The Core Model)
*   **Status**: JAR Library (`lib/jbms-parser.jar`).
*   **Contents**: Contains `bms.model.BMSModel`, `bms.model.Note`, `bms.model.TimeLine`, and parsers.
*   **Implication**: We **cannot** modify `BMSModel.java` directly. We cannot add fields like `private StoryboardData storyboard;` to the `BMSModel` class.
*   **Workaround**: Features that require attaching data to a song chart (like Osu! Storyboards) must use an **External Registry** pattern (e.g., `Map<BMSModel, ExtraData>`) or be managed by the `PlayerResource` / `BMSPlayer` classes which wrap the model.

### 2. Audio Engine (`bms.player.beatoraja.audio`)
*   **Drivers**: Supports `OpenAL` (via LibGDX) and `PortAudio` (via `jportaudio.jar`).
*   **PCM Processing**: Uses a generic `PCM<T>` hierarchy.
*   **Formats**: `jflac-codec` enables FLAC support. `ffmpeg` (via JavaCPP) handles video and complex audio formats.

### 3. Graphics & UI (`bms.player.beatoraja.play`, `launcher`)
*   **Engine**: LibGDX (OpenGL).
*   **Launcher**: JavaFX. This is a separate windowing toolkit from the game itself. Communication happens via `Config` objects (`PlayerConfig`, `Config`) serialized to JSON.
*   **Skinning**: Custom engine (`SkinLoader`, `SkinManager`) supporting legacy LR2 (CSV) and modern JSON/Lua skins.

### 4. Networking (`bms.player.beatoraja.arena`)
*   **Protocol**: TCP-based custom protocol (`ArenaClient`, `ArenaServer`).
*   **State**: `ArenaManager` holds the state.
*   **Sync**: Scores are synced via packet transmission. Song hash validation ensures all players have the exact same chart.

## Feature Implementation Strategies

### Osu! Storyboard
Since `BMSModel` is immutable/closed:
1.  **Parsing**: `OsuDecoder` (source available in `src/`) parses `.osu` files. It creates a `BMSModel` using the public API.
2.  **Storage**: We must create a `StoryboardRegistry` singleton or inject a `StoryboardManager` into `OsuDecoder`.
3.  **Rendering**: `BGAProcessor` (source available) manages visual elements. It can query the Registry: `StoryboardData sb = StoryboardRegistry.get(model)`. If present, it delegates drawing to a `StoryboardRenderer`.

### Course Editor
*   **Data**: `CourseData` structure exists.
*   **UI**: Needs to be built in JavaFX (`src/bms/player/beatoraja/launcher/`).
*   **Integration**: The Launcher creates a `course.bms` or `course.json` file. The game loads this as a standard chart.

## Missing Sources
*   `src/bms/model/BMSModel.java`: **MISSING** (Inside `jbms-parser.jar`).
*   `src/bms/model/ChartInformation.java`: **MISSING** (Inside `jbms-parser.jar`).

## Recommendation for AI Agents
*   **Do not attempt to edit `bms.model.*` files** unless you are sure they are in `src/`. Check `ls` first.
*   **Use Composition over Inheritance** when extending core model functionality.
*   **Check `lib/`** folder for dependencies if symbols are missing.
