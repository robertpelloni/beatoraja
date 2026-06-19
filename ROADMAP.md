# ROADMAP

* **LWJGL 3 Integration:** Complete the migration to LibGDX/LWJGL 3, restoring all the UI rendering, Shader support, and hardware controller bounds.
* **Audio Backend:** Replace or restore the `be.tarsos.dsp` library to properly handle PCM decoding and time stretching. Clean up the `AbstractAudioDriver` logic.
* **Internet Ranking (IR):** Refactor `MainController.getIRConnection()` to fully handle the multiplayer ranking data correctly instead of being stubbed.
* **Architecture Cleanup:** Move all constants out of static namespaces into properly managed Singletons, specifically input handling logic which should be modularized to cleanly support both LibGDX and standalone contexts.
