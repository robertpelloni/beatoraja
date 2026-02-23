# TODO List

## Priority 1: High Impact / Missing Features

### Gameplay
- [ ] **Replay Analysis (Wire-up)**:
    - [ ] Integrate `ReplayAnalysisView` into `PlayConfigurationView` or a new Launcher tab.
    - [ ] Implement data population logic (Histogram from hit deltas, Gauge graph from replay frames).

### Launcher
- [ ] **Chart Preview**: Verify rendering in `MusicSelector`. It relies on skin properties to actually draw the note graph.
- [ ] **Folder Editing**: Polish `FolderEditorView`.

## Priority 2: Polish & Refinement

### Osu! Support
- [ ] **Spinner Graphics**: Improve visualization of spinners (currently Scratch LNs).
- [ ] **Storyboard Loops**: Implement `Loop` and `Trigger` command support in `StoryboardRenderer`.

## Priority 3: Long Term / Architectural

### Graphics
- [ ] **Vulkan Support**: Prepare `LibGDX` backend for Vulkan migration.
- [ ] **Shader System**: Refactor `ShaderManager` for more flexible post-processing effects.

### Networking
- [ ] **Spectator Mode**: Allow clients to spectate an Arena match without playing.

---

## Completed (0.9.16)
- [x] **Replay Analysis UI Foundation**

## Completed (0.9.15)
- [x] **Skin Preview in Launcher**
- [x] **Device-Specific Latency/Interval**
