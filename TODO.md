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
# TODO

## High Priority: Technical Debt & LWJGL 3 Migration
- [ ] **Fully Resolve LWJGL 3 Compilation Errors**: There are approximately 1,200 errors stemming from the transition from LWJGL 2/Ant to LWJGL 3/Gradle 8.8. The largest offenders are `PCM` missing generic parameters, `SpriteBatch` methods missing parameters for `SkinObjectRenderer`, and missing enums like `IRStatus` being migrated to `IRConnection`. These must be fully resolved before `gradle build` succeeds.
- [x] **Fix `MainState.java` Overloads**: The methods `getTimer()` and `getAudioConfig()` need proper integration with `MainController`'s new architecture.

## Medium Priority: Feature Implementations
- [x] **Rewind/Fast-Forward in Practice Mode**:
  - *Target Classes*: `PracticePlayer.java`, `BMSPlayer.java`
  - *Details*: Implement timeline scrubbing by mapping left/right arrow keys or a specific UI slider to jump the `timer.getNowTime()` backward/forward, instantly resetting `Note` and `BGA` object states to their positions relative to the new time.
- [x] **Dynamic Chart Generator**:
  - *Target Classes*: `OsuDecoder.java` / `BMSDecoder.java`
  - *Details*: Create an `AutoChartGenerator.java` class that uses basic FFT/beat detection to generate playable note arrays from any generic MP3/OGG file placed in the music folder.
- [x] **Live Spectator Mode (Arena)**:
  - *Target Classes*: `ArenaManager.java`, `TCPClient.java`
  - *Details*: Abstract the existing TCP packet structure to support a `TYPE_SPECTATE` connection. Send real-time gauge and score updates from active players to spectators.

## Low Priority: Quality of Life & Polish
- [x] **In-Game Theme/Skin Editor**:
  - *Target Classes*: `SkinConfigurationView.java`, `JsonSkinObjectLoader.java`
  - *Details*: Build a WYSIWYG editor using JavaFX or Scene2D to manipulate `dstX`, `dstY`, and `scale` properties of `SkinObject` arrays, saving directly back to `result.json` or `play7.json`.
- [ ] **Android/Mobile Port (LibGDX)**:
  - *Target Classes*: `build.gradle`, `MainLoader.java`
  - *Details*: Add an `android` block to the Gradle configuration and bootstrap a basic LibGDX Android launcher, porting `BMControllerInputProcessor` to utilize touch gestures for 5K/7K layouts.