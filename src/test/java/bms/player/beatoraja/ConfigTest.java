package bms.player.beatoraja;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    @Test
    public void testDefaultValues() {
        Config config = new Config();
        
        assertNull(config.getPlayername());
        assertEquals(Config.DisplayMode.WINDOW, config.getDisplaymode());
        assertFalse(config.isVsync());
        assertEquals(Resolution.HD, config.getResolution());
        assertEquals(240, config.getMaxFramePerSecond());
        assertEquals(10, config.getMaxSearchBarCount());
    }

    @Test
    public void testPlayerName() {
        Config config = new Config();
        config.setPlayername("TestPlayer");
        assertEquals("TestPlayer", config.getPlayername());
    }

    @Test
    public void testDisplayMode() {
        Config config = new Config();
        config.setDisplaymode(Config.DisplayMode.FULLSCREEN);
        assertEquals(Config.DisplayMode.FULLSCREEN, config.getDisplaymode());
        
        config.setDisplaymode(Config.DisplayMode.BORDERLESS);
        assertEquals(Config.DisplayMode.BORDERLESS, config.getDisplaymode());
    }

    @Test
    public void testVsync() {
        Config config = new Config();
        config.setVsync(true);
        assertTrue(config.isVsync());
        
        config.setVsync(false);
        assertFalse(config.isVsync());
    }

    @Test
    public void testMaxFramePerSecond() {
        Config config = new Config();
        config.setMaxFramePerSecond(120);
        assertEquals(120, config.getMaxFramePerSecond());
    }

    @Test
    public void testBgaSettings() {
        Config config = new Config();
        assertEquals(Config.BGA_ON, config.getBga());
        
        config.setBga(Config.BGA_OFF);
        assertEquals(Config.BGA_OFF, config.getBga());
        
        config.setBga(Config.BGA_AUTO);
        assertEquals(Config.BGA_AUTO, config.getBga());
    }

    @Test
    public void testPathDefaults() {
        assertEquals("songdata.db", Config.SONGPATH_DEFAULT);
        assertEquals("table", Config.TABLEPATH_DEFAULT);
        assertEquals("player", Config.PLAYERPATH_DEFAULT);
        assertEquals("skin", Config.SKINPATH_DEFAULT);
    }

    @Test
    public void testDiscordRPC() {
        Config config = new Config();
        assertFalse(config.isUseDiscordRPC());
        
        config.setUseDiscordRPC(true);
        assertTrue(config.isUseDiscordRPC());
    }

    @Test
    public void testIpfsSettings() {
        Config config = new Config();
        assertTrue(config.isEnableIpfs());
        assertEquals("https://gateway.ipfs.io/", config.getIpfsUrl());
        
        config.setEnableIpfs(false);
        assertFalse(config.isEnableIpfs());
    }

    @Test
    public void testWindowDimensions() {
        Config config = new Config();
        assertEquals(1280, config.getWindowWidth());
        assertEquals(720, config.getWindowHeight());
        
        config.setWindowWidth(1920);
        config.setWindowHeight(1080);
        assertEquals(1920, config.getWindowWidth());
        assertEquals(1080, config.getWindowHeight());
    }

    @Test
    public void testScrollSettings() {
        Config config = new Config();
        assertEquals(300, config.getScrollDurationLow());
        assertEquals(50, config.getScrollDurationHigh());
        assertTrue(config.isAnalogScroll());
    }
}
