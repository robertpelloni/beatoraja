# TODO & Short-Term Action Items

This list represents the immediate next steps required to fully wire and polish the recently integrated features, and address remaining systemic constraints identified during the `0.9.23` deep dive.

## Internet Ranking (IR) Modernization
*   [ ] **Deprecate Old Format**: Find and replace outdated IR submission payloads in `RankingData.java` and `RankingDataCache.java` (grep `TODO 旧方式のため後で削除`).
*   [ ] **Hash Validation**: Implement logic to verify the integrity of the score hash before submission (`// TODO スコアハッシュを付与するかどうかの判定`).
*   [ ] **Battle Mode IR**: Ensure that 2-player battle mode scores are assigned independent hashes and submitted separately (`// TODO BATTLEは別ハッシュで登録したい`).

## Step-Up Mode Polish
*   [ ] **Dynamic Course Caching**: Currently, Step-Up generates a new course layout via `StepUpManager` every time the folder is opened. Cache the current level's course to avoid unnecessary DB lookups.

## Replay Playback Mechanics
*   [ ] **Fast-Forward / Rewind**: The Replay Analysis UI works (Static Data), but actual replay *playback* needs timeline scrubbing. Modify `BMSPlayer`'s time increment to support variable steps or jumping to a specific timestamp, syncing `ControlInputProcessor` offsets.
*   [ ] **Heatmap Generation**: Add an option in `ReplayAnalysisView` to export the hit-error distribution over time as a PNG heatmap for sharing.

## Audio Engine Polish
*   [ ] **HCN (Hell Charge Note) Re-triggering**: Determine logic for keysound re-triggering if a player releases and re-presses an HCN (`// TODO HCN押し直しの発音はどうする？` in `JudgeManager.java`).

## Skinning Engine (LR2 Parity & Expansion)
*   [ ] **Dynamic Skin Previews**: The Launcher UI now loads `preview.png`. However, the `Select` skin needs to dynamically read the folder's `preview.png` via LibGDX (`SkinConfigurationView.java` uses JavaFX; `MusicSelector.java` needs LibGDX implementation).
*   [ ] **Subtract Blending**: Investigate implementing `GL_FUNC_REVERSE_SUBTRACT` for specific skin elements to match LR2's blending modes (`// TODO 減算描画は難しいか？` in `Skin.java`).
*   [ ] **Robust Image Decoding**: Some older BMS files use obscure JPEG/BMP formats that `ImageIO.read` chokes on. Investigate replacing with `TwelveMonkeys` ImageIO plugin or pure LibGDX `Pixmap` loading for BGAs (`// TODO 一部のbmsはImageIO.readで失敗する`).

## Code Smells / Tech Debt
*   [ ] **Input Configuration**: `InputConfigurationView.java` needs an overall layout refactor; as more columns (like the new Min Interval) were added, the TableView has become cramped at 640x480 resolution.
*   [ ] **7-to-9 Mode Modifier**: The 9-key mapping logic in `ModeModifier.java` is largely copy-pasted from 7-key. It requires significant refactoring to use a shared base class (`// TODO 7to9ほぼそのままのコードのため、要リファクタリング`).
*   [ ] **Pattern Modifier**: `BATTLE` mode shuffling should likely be moved from `PatternModifier` to `ModeModifier` to ensure lane covers and skin targets are updated correctly.