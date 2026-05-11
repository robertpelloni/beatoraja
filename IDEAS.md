# Ideas for Improvements & Innovation

## Core Gameplay
- **Rewind/Fast-Forward in Practice Mode**: Similar to StepMania or modern rhythm games, allowing players to instantly scrub through a song.
- **Dynamic Chart Generator**: A mode that auto-generates charts from audio files using rhythm detection algorithms.
- **VR Support**: A VR mode utilizing LibGDX's VR extensions for immersive gameplay.

## Multiplayer & Social
- **Live Spectator Mode**: Allow users to watch active Arena games.
- **Ghost/Rival Overlay**: Show a ghost of a selected rival's inputs on the playfield.

## Audio & Visuals
- **Custom Shader Support**: Allow users to write and load their own GLSL shaders for the playfield.
- **Vulkan Rendering Pipeline**: Upgrade from OpenGL to Vulkan for better performance on modern GPUs.

## UI/UX
- **In-Game Theme/Skin Editor**: A visual editor to modify JSON/Lua skins directly within the game.
- **Advanced Replay Analyzer**: A built-in tool (similar to osu!rewind) to visualize hit errors on a timeline.

## Platform Support
- **Mobile/Android Port**: Port the game to Android using LibGDX's mobile backends.
- **WebAssembly Build**: A "lite" browser-based version using TeaVM/GWT.


## Code Architecture & Refactoring
- **Completely Resolve LWJGL 3 Migration Errors**: The project is currently transitioning from LWJGL 2/Ant to LWJGL 3/Gradle 8.8/Java 21. There are roughly 1,200 compilation errors in the codebase mainly due to `SkinObjectRenderer` mismatches and parameterized `PCM` classes. This should be the highest priority technical debt task.
- **Isolate Network Logic**: Currently `ArenaManager` is tightly coupled; abstracting the TCP layer would make spectating and ghost features much easier to implement.
- **Implement True Sound Polling**: LibGDX `Sound` interface doesn't expose an `isPlaying()` or `duration()` method natively, causing us to stub it for pacemakers. We should consider wrapping LibGDX's `Sound` implementation or switching exclusively to `Music` objects if we need fine-grained duration tracking for sound effects.
