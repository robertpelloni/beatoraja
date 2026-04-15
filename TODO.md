# TODO

## High Priority
- [x] Parse project version directly from `VERSION.md` in `build.gradle` and `MainController.java` rather than hardcoding it.
- [x] Fix Version references in UI titles to use the global version.
- [x] Osu! Storyboard: fully parse `[Events]` section to map Sprite, Animation, and transformations into `BGAEvent`.
- [x] BGAProcessor: Add full Event Layer support (currently only handles MISS layers).

## Medium Priority
- [ ] Refactor `PlayDataAccessor.java` to properly handle FLIP modifiers and separate Battle mode hashes.
- [ ] Allocate IDs 64-69 in `IntegerPropertyFactory.java` for target2/target3 (Pacemaker feature).
- [x] Optimize `SkinNoteDistributionGraph.java` by batching texture bind calls (`bindTexture`).
- [x] Implement a `isPlaying()` check in `GdxSoundDriver.java`.

## Low Priority
- [x] Add `bobcoin` to the forbidden list in `.gitignore` (completed conceptually, just a reminder).
