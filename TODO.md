# TODO

## High Priority: Technical Debt & LWJGL 3 Migration
- [ ] **Fully Resolve LWJGL 3 Compilation Errors**: There are approximately 1,200 errors stemming from the transition from LWJGL 2/Ant to LWJGL 3/Gradle 8.8. The largest offenders are `PCM` missing generic parameters, `SpriteBatch` methods missing parameters for `SkinObjectRenderer`, and missing enums like `IRStatus` being migrated to `IRConnection`. These must be fully resolved before `gradle build` succeeds.
- [ ] **Fix `MainState.java` Overloads**: The methods `getTimer()` and `getAudioConfig()` need proper integration with `MainController`'s new architecture.

## Medium Priority: Feature Implementations
- [ ] **Rewind/Fast-Forward in Practice Mode**:
  - *Target Classes*: `PracticePlayer.java`, `BMSPlayer.java`
  - *Details*: Implement timeline scrubbing by mapping left/right arrow keys or a specific UI slider to jump the `timer.getNowTime()` backward/forward, instantly resetting `Note` and `BGA` object states to their positions relative to the new time.
- [ ] **Dynamic Chart Generator**:
  - *Target Classes*: `OsuDecoder.java` / `BMSDecoder.java`
  - *Details*: Create an `AutoChartGenerator.java` class that uses basic FFT/beat detection to generate playable note arrays from any generic MP3/OGG file placed in the music folder.
- [ ] **Live Spectator Mode (Arena)**:
  - *Target Classes*: `ArenaManager.java`, `TCPClient.java`
  - *Details*: Abstract the existing TCP packet structure to support a `TYPE_SPECTATE` connection. Send real-time gauge and score updates from active players to spectators.

## Low Priority: Quality of Life & Polish
- [ ] **In-Game Theme/Skin Editor**:
  - *Target Classes*: `SkinConfigurationView.java`, `JsonSkinObjectLoader.java`
  - *Details*: Build a WYSIWYG editor using JavaFX or Scene2D to manipulate `dstX`, `dstY`, and `scale` properties of `SkinObject` arrays, saving directly back to `result.json` or `play7.json`.
- [ ] **Android/Mobile Port (LibGDX)**:
  - *Target Classes*: `build.gradle`, `MainLoader.java`
  - *Details*: Add an `android` block to the Gradle configuration and bootstrap a basic LibGDX Android launcher, porting `BMControllerInputProcessor` to utilize touch gestures for 5K/7K layouts.
