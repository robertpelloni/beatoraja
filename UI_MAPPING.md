# UI Feature Mapping

## Core Menus & Configurations

### Play Configuration (`PlayConfigurationView.fxml` & `PlayConfigurationView.java`)
| Backend Property / Feature | Frontend UI Representation | Status |
|----------------------------|-----------------------------|--------|
| `PlayerConfig.targetid` | `ComboBox<String> target` | ✅ Implemented |
| `PlayerConfig.target2id` | `ComboBox<String> target2` | ✅ Implemented (0.10.3) |
| `PlayerConfig.target3id` | `ComboBox<String> target3` | ✅ Implemented (0.10.3) |
| `PlayConfig.gauge` | `ComboBox<GaugeProperty> gauge` | ✅ Implemented |
| `PlayConfig.random` | `ComboBox<RandomProperty> random` | ✅ Implemented |
| `PlayConfig.hispeed` | `NumericSpinner<Double> hispeed` | ✅ Implemented |
| `PlayConfig.lanecover` | `NumericSpinner<Integer> lanecover`| ✅ Implemented |
| FLIP Modifiers | `PlayDataAccessor` refactored, UI pending | ⏳ Planned |
| Battle Mode | `PlayDataAccessor` refactored, UI pending | ⏳ Planned |

### Music Selection (`MusicSelector.java` & In-Game UI)
| Backend Property / Feature | Frontend UI Representation | Status |
|----------------------------|-----------------------------|--------|
| Song Database Searching | `SongDataView.fxml` | ✅ Implemented |
| Folder Browsing | `FolderEditorView.fxml` | ✅ Implemented |
| Course Selection | `CourseEditorView.fxml` | ✅ Implemented |
| Difficulty Selection | Embedded in `MusicSelector` / In-game | ✅ Implemented |
| Dynamic Chart Generator | None yet | ⏳ Planned |

### In-Game Score Tracking (`MusicResult.java`, `SkinNoteDistributionGraph.java`)
| Backend Property / Feature | Frontend UI Representation | Status |
|----------------------------|-----------------------------|--------|
| Replay Hit Error Graphs | `SkinNoteDistributionGraph` | ✅ Implemented |
| Notes Radar Metrics | `SkinRadarGraph` | ✅ Implemented |
| Fast/Slow Timing Counts | `SkinProperty.NUMBER_FAST_NOTES` | ✅ Implemented |
| Score Difference (`target2`)| `SkinProperty.NUMBER_TARGET2_SCORE_DIFF`| ✅ Implemented |

## Future UI Implementation Targets
1. **Theme/Skin Editor**: Create a new FXML view to visually manipulate `JsonSkinObjectLoader` coordinates (`dstX`, `dstY`).
2. **Replay Scrubbing Controls**: Map keyboard inputs or on-screen UI buttons in `MusicSelector` or `BMSPlayer` for Rewind/Fast-Forward functionality.
