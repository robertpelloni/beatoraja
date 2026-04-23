# Submodules & Libraries

## Submodules
| Submodule | URL | Description | Integration Status |
|-----------|-----|-------------|--------------------|
| `lr2oraja-endlessdream` | `https://github.com/seraxis/lr2oraja-endlessdream` | The default standard LR2-compatible skin theme | Active/Integrated |
| `bobcoin` | N/A | Strictly and fully eradicated | Removed entirely |

## Core Libraries (Gradle)
- `com.badlogicgames.gdx:gdx:1.12.1`: Core LibGDX game framework providing OpenGL abstractions and game loops.
- `com.badlogicgames.gdx:gdx-backend-lwjgl3:1.12.1`: Desktop backend utilizing modern LWJGL 3 (currently in active migration/compilation fixing phase).
- `com.badlogicgames.gdx:gdx-freetype:1.12.1`: Font rendering.
- `org.xerial:sqlite-jdbc:3.42.0.0`: Local database for `score.db` and `scorelog.db`.
- `org.twitter4j:twitter4j-core:4.0.4`: Legacy integration for tweeting screenshots (`ScreenShotTwitterExporter`). Pinned deliberately.
- `com.github.tulskiy:jflac-codec:1.3`: Flac decoding capability for BMS audio arrays.
- `com.google.code.gson:gson:2.10.1`: JSON parsing for Arena settings and Skin configurations.
- `net.java.dev.jna:jna:5.13.0`: Native access required by parts of the audio/input system.
