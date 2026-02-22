# TODO List

## Priority 1: High Impact / Missing Features

### Skinning
- [ ] **Skin Preview in Launcher**:
    - [ ] Add `ImageView` to `SkinConfigurationView.fxml`.
    - [ ] Implement logic in `SkinConfigurationView.java` to load `preview.png` (or equivalent) from the selected skin's directory.
    - [ ] Handle fallback if no preview image exists.

### Input
- [ ] **Device-Specific Latency/Interval**:
    - [ ] Address `// TODO 各デバイス毎の最小入力間隔設定` in `InputConfigurationView.java`.
    - [ ] Expose configuration UI for polling rates or debounce times per controller.

### Gameplay
- [ ] **Replay Analysis**:
    - [ ] Create a UI to view detailed stats of a replay (Hit error distribution, Gauge history).
    - [ ] Integrate into Result Screen or a standalone Replay Viewer.

## Priority 2: Polish & Refinement

### Launcher
- [ ] **Chart Preview**: Ensure the preview in Music Select is performant and accurate.
- [ ] **Folder Editing**: Polish `FolderEditorView`.

### Osu! Support
- [ ] **Spinner Graphics**: Improve visualization of spinners (currently Scratch LNs).
- [ ] **Storyboard Loops**: Implement `Loop` and `Trigger` command support in `StoryboardRenderer`.

## Priority 3: Long Term / Architectural

### Graphics
- [ ] **Vulkan Support**: Prepare `LibGDX` backend for Vulkan migration.
- [ ] **Shader System**: Refactor `ShaderManager` for more flexible post-processing effects.

### Networking
- [ ] **Spectator Mode**: Allow clients to spectate an Arena match without playing.
