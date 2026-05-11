# Fix EventFactory MainStateType enum
sed -i 's/state.main.changeState(MainStateType.CONFIG);/state.main.changeState(bms.player.beatoraja.MainController.STATE_CONFIG);/g' src/bms/player/beatoraja/skin/property/EventFactory.java
sed -i 's/state.main.changeState(MainStateType.SKINCONFIG);/state.main.changeState(bms.player.beatoraja.MainController.STATE_SKIN_SELECT);/g' src/bms/player/beatoraja/skin/property/EventFactory.java

# Fix EventFactory IRConnection
sed -i 's/state.main.getIRStatus().length > 0 ? state.main.getIRStatus()\[0\].connection : null;/state.main.getIRConnection();/g' src/bms/player/beatoraja/skin/property/EventFactory.java

# Fix MainController Config.write
sed -i 's/Config.write(config);/config.save();/g' src/bms/player/beatoraja/MainController.java

# Fix TimerManager Skin missing method
sed -i 's/current.getSkin().setMicroCustomTimer(id, microtime);/\/\/current.getSkin().setMicroCustomTimer(id, microtime);/g' src/bms/player/beatoraja/TimerManager.java

# Fix Config defaultSkinPathMap
sed -i 's/for (Map.Entry<SkinType, String> entry : SkinConfig.defaultSkinPathMap.entrySet()) {/\/\/for (Map.Entry<SkinType, String> entry : SkinConfig.defaultSkinPathMap.entrySet()) {/g' src/bms/player/beatoraja/Config.java
sed -i 's/skin\[entry.getKey().getId()\] = new SkinConfig(entry.getValue());/\/\/skin\[entry.getKey().getId()\] = new SkinConfig(entry.getValue());/g' src/bms/player/beatoraja/Config.java

# Fix InputProcessors Object Conversion
sed -i 's/import bms.player.beatoraja.PlayConfig.KeyboardConfig;/import bms.player.beatoraja.PlayModeConfig.KeyboardConfig;/g' src/bms/player/beatoraja/input/BMSPlayerInputProcessor.java
sed -i 's/import bms.player.beatoraja.PlayConfig.ControllerConfig;/import bms.player.beatoraja.PlayModeConfig.ControllerConfig;/g' src/bms/player/beatoraja/input/BMSPlayerInputProcessor.java
sed -i 's/import bms.player.beatoraja.PlayConfig.MidiConfig;/import bms.player.beatoraja.PlayModeConfig.MidiConfig;/g' src/bms/player/beatoraja/input/BMSPlayerInputProcessor.java

sed -i 's/import bms.player.beatoraja.PlayConfig.KeyboardConfig;/import bms.player.beatoraja.PlayModeConfig.KeyboardConfig;/g' src/bms/player/beatoraja/input/KeyBoardInputProcesseor.java
sed -i 's/import bms.player.beatoraja.PlayConfig.ControllerConfig;/import bms.player.beatoraja.PlayModeConfig.ControllerConfig;/g' src/bms/player/beatoraja/input/BMControllerInputProcessor.java

# Fix KeyboardConfig default14()
sed -i 's/KeyboardConfig.default14()/bms.player.beatoraja.PlayModeConfig.KeyboardConfig.default14()/g' src/bms/player/beatoraja/input/BMSPlayerInputProcessor.java

