package bms.player.beatoraja;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResolutionTest {

    @Test
    public void testResolutionCount() {
        assertEquals(15, Resolution.values().length);
    }

    @Test
    public void testCommonResolutions() {
        assertEquals(640, Resolution.SD.width);
        assertEquals(480, Resolution.SD.height);
        
        assertEquals(1280, Resolution.HD.width);
        assertEquals(720, Resolution.HD.height);
        
        assertEquals(1920, Resolution.FULLHD.width);
        assertEquals(1080, Resolution.FULLHD.height);
        
        assertEquals(2560, Resolution.WQHD.width);
        assertEquals(1440, Resolution.WQHD.height);
        
        assertEquals(3840, Resolution.ULTRAHD.width);
        assertEquals(2160, Resolution.ULTRAHD.height);
    }

    @Test
    public void testToString() {
        assertEquals("HD (1280 x 720)", Resolution.HD.toString());
        assertEquals("FULLHD (1920 x 1080)", Resolution.FULLHD.toString());
        assertEquals("ULTRAHD (3840 x 2160)", Resolution.ULTRAHD.toString());
    }

    @Test
    public void testAspectRatios() {
        // 4:3 resolutions
        assertEquals(640 * 3, Resolution.SD.height * 4);
        assertEquals(1024 * 3, Resolution.XGA.height * 4);
        
        // 16:9 resolutions
        assertEquals(1280 * 9, Resolution.HD.height * 16);
        assertEquals(1920 * 9, Resolution.FULLHD.height * 16);
        assertEquals(3840 * 9, Resolution.ULTRAHD.height * 16);
    }

    @Test
    public void testAllResolutionsHavePositiveDimensions() {
        for (Resolution res : Resolution.values()) {
            assertTrue(res.width > 0, res.name() + " width should be positive");
            assertTrue(res.height > 0, res.name() + " height should be positive");
        }
    }
}
