cat << 'DOCS_EOF' >> ROADMAP.md
### Current LWJGL3 Migration Status (as of 0.10.14)
- **Resolved**: `MainState`, `SystemSoundManager`, `EventFactory` signatures.
- **Pending**: Method parameter mismatches in `BMSPlayerInputProcessor`, `PlayConfig` structure discrepancies (specifically `isEnableIpfs`, `getUserid`, `getPassword` which migrated from `Config` to `PlayerConfig` in upstream but aren't fully resolved locally), and unresolved references in `TimerManager`.
- **Next Focus**: Complete the remaining ~1,000 compilation errors to restore a passing build state.

DOCS_EOF

cat << 'DOCS_EOF' >> TODO.md
## High Priority
- [ ] Fix `PlayConfig` vs `PlayerConfig` vs `Config` data model discrepancies causing `cannot find symbol` errors in `MainController` and `BMSPlayerInputProcessor`.
- [ ] Investigate `TimerManager` compilation errors (`unnamed classes are a preview feature` implies a malformed file or missing imports/class definition).
- [ ] Resolve missing imports for `scene2d.ui` elements (like `CheckBox`) in `ModMenu.java`.

DOCS_EOF
