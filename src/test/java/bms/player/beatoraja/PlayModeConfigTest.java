package bms.player.beatoraja;

import bms.model.Mode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayModeConfigTest {

    @Test
    public void testDefaultConstruction() {
        PlayModeConfig config = new PlayModeConfig();
        assertNotNull(config.getKeyboardConfig());
        assertNotNull(config.getController());
        assertNotNull(config.getMidiConfig());
    }

    @Test
    public void testModeConstruction() {
        PlayModeConfig config7k = new PlayModeConfig(Mode.BEAT_7K);
        assertNotNull(config7k.getKeyboardConfig());
        assertEquals(1, config7k.getController().length);

        PlayModeConfig config14k = new PlayModeConfig(Mode.BEAT_14K);
        assertEquals(2, config14k.getController().length);
    }

    @Test
    public void testVersion() {
        PlayModeConfig config = new PlayModeConfig();
        config.setVersion(5);
        assertEquals(5, config.getVersion());
    }

    @Test
    public void testKeyboardConfig() {
        PlayModeConfig config = new PlayModeConfig();
        PlayModeConfig.KeyboardConfig keyboard = config.getKeyboardConfig();
        assertNotNull(keyboard);
        
        keyboard.setDuration(50);
        assertEquals(50, keyboard.getDuration());
    }

    @Test
    public void testControllerConfig() {
        PlayModeConfig config = new PlayModeConfig();
        PlayModeConfig.ControllerConfig[] controllers = config.getController();
        assertNotNull(controllers);
        assertTrue(controllers.length >= 1);
        
        PlayModeConfig.ControllerConfig controller = controllers[0];
        assertNotNull(controller);
        
        controller.setDuration(25);
        assertEquals(25, controller.getDuration());
        
        controller.setJKOC(true);
        assertTrue(controller.getJKOC());
        
        controller.setAnalogScratch(true);
        assertTrue(controller.isAnalogScratch());
    }

    @Test
    public void testAnalogScratchThreshold() {
        PlayModeConfig config = new PlayModeConfig();
        PlayModeConfig.ControllerConfig controller = config.getController()[0];
        
        controller.setAnalogScratchThreshold(500);
        assertEquals(500, controller.getAnalogScratchThreshold());
        
        // Test clamping to max 1000
        controller.setAnalogScratchThreshold(2000);
        assertEquals(1000, controller.getAnalogScratchThreshold());
        
        // Test clamping to min 1
        controller.setAnalogScratchThreshold(0);
        assertEquals(1, controller.getAnalogScratchThreshold());
    }

    @Test
    public void testMidiConfig() {
        PlayModeConfig config = new PlayModeConfig();
        PlayModeConfig.MidiConfig midi = config.getMidiConfig();
        assertNotNull(midi);
    }

    @Test
    public void testSetControllers() {
        PlayModeConfig config = new PlayModeConfig();
        PlayModeConfig.ControllerConfig[] newControllers = new PlayModeConfig.ControllerConfig[2];
        newControllers[0] = new PlayModeConfig.ControllerConfig();
        newControllers[1] = new PlayModeConfig.ControllerConfig();
        
        config.setController(newControllers);
        assertEquals(2, config.getController().length);
    }

    @Test
    public void testStartSelectButtons() {
        PlayModeConfig config = new PlayModeConfig();
        PlayModeConfig.ControllerConfig controller = config.getController()[0];
        
        controller.setStart(10);
        controller.setSelect(11);
        // These setters exist; no getters exposed in current version
    }
}
