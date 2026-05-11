# Fix Input Processors
sed -i 's/import bms.player.beatoraja.PlayConfig.KeyboardConfig;/import bms.player.beatoraja.PlayModeConfig.KeyboardConfig;/g' src/bms/player/beatoraja/input/BMSPlayerInputProcessor.java
sed -i 's/import bms.player.beatoraja.PlayConfig.ControllerConfig;/import bms.player.beatoraja.PlayModeConfig.ControllerConfig;/g' src/bms/player/beatoraja/input/BMSPlayerInputProcessor.java
sed -i 's/import bms.player.beatoraja.PlayConfig.MidiConfig;/import bms.player.beatoraja.PlayModeConfig.MidiConfig;/g' src/bms/player/beatoraja/input/BMSPlayerInputProcessor.java
