# TODO

## High Priority
- [ ] Parse project version directly from `VERSION.md` in `build.gradle` and `MainController.java` rather than hardcoding it.
- [ ] Fix Version references in UI titles to use the global version.
- [ ] Osu! Storyboard: fully parse `[Events]` section to map Sprite, Animation, and transformations into `BGAEvent`.
- [ ] BGAProcessor: Add full Event Layer support (currently only handles MISS layers).

## Medium Priority
- [ ] Refactor `PlayDataAccessor.java` to properly handle FLIP modifiers and separate Battle mode hashes.
- [ ] Allocate IDs 64-69 in `IntegerPropertyFactory.java` for target2/target3 (Pacemaker feature).
- [ ] Optimize `SkinNoteDistributionGraph.java` by batching texture bind calls (`bindTexture`).
- [ ] Implement a `isPlaying()` check in `GdxSoundDriver.java`.

## Low Priority
- [ ] Add `bobcoin` to the forbidden list in `.gitignore` (completed conceptually, just a reminder).
