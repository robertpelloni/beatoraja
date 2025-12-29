package bms.player.beatoraja;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClearTypeTest {

    @Test
    public void testClearTypeValues() {
        assertEquals(11, ClearType.values().length);
        assertEquals(0, ClearType.NoPlay.id);
        assertEquals(1, ClearType.Failed.id);
        assertEquals(2, ClearType.AssistEasy.id);
        assertEquals(3, ClearType.LightAssistEasy.id);
        assertEquals(4, ClearType.Easy.id);
        assertEquals(5, ClearType.Normal.id);
        assertEquals(6, ClearType.Hard.id);
        assertEquals(7, ClearType.ExHard.id);
        assertEquals(8, ClearType.FullCombo.id);
        assertEquals(9, ClearType.Perfect.id);
        assertEquals(10, ClearType.Max.id);
    }

    @Test
    public void testGetClearTypeByID() {
        assertEquals(ClearType.NoPlay, ClearType.getClearTypeByID(0));
        assertEquals(ClearType.Failed, ClearType.getClearTypeByID(1));
        assertEquals(ClearType.Easy, ClearType.getClearTypeByID(4));
        assertEquals(ClearType.Hard, ClearType.getClearTypeByID(6));
        assertEquals(ClearType.ExHard, ClearType.getClearTypeByID(7));
        assertEquals(ClearType.FullCombo, ClearType.getClearTypeByID(8));
        assertEquals(ClearType.Perfect, ClearType.getClearTypeByID(9));
        assertEquals(ClearType.Max, ClearType.getClearTypeByID(10));
    }

    @Test
    public void testGetClearTypeByIDInvalid() {
        assertEquals(ClearType.NoPlay, ClearType.getClearTypeByID(-1));
        assertEquals(ClearType.NoPlay, ClearType.getClearTypeByID(100));
        assertEquals(ClearType.NoPlay, ClearType.getClearTypeByID(999));
    }

    @Test
    public void testGetClearTypeByGauge() {
        // LightAssistEasy has gauge type 0
        assertEquals(ClearType.LightAssistEasy, ClearType.getClearTypeByGauge(0));
        
        // Easy has gauge type 1
        assertEquals(ClearType.Easy, ClearType.getClearTypeByGauge(1));
        
        // Normal has gauge types 2, 6
        assertEquals(ClearType.Normal, ClearType.getClearTypeByGauge(2));
        assertEquals(ClearType.Normal, ClearType.getClearTypeByGauge(6));
        
        // Hard has gauge types 3, 7, 9
        assertEquals(ClearType.Hard, ClearType.getClearTypeByGauge(3));
        assertEquals(ClearType.Hard, ClearType.getClearTypeByGauge(7));
        assertEquals(ClearType.Hard, ClearType.getClearTypeByGauge(9));
        
        // ExHard has gauge types 4, 8
        assertEquals(ClearType.ExHard, ClearType.getClearTypeByGauge(4));
        assertEquals(ClearType.ExHard, ClearType.getClearTypeByGauge(8));
        
        // FullCombo has gauge type 5
        assertEquals(ClearType.FullCombo, ClearType.getClearTypeByGauge(5));
    }

    @Test
    public void testGetClearTypeByGaugeInvalid() {
        assertNull(ClearType.getClearTypeByGauge(-1));
        assertNull(ClearType.getClearTypeByGauge(100));
    }

    @Test
    public void testGaugeTypeArrays() {
        assertEquals(0, ClearType.NoPlay.gaugetype.length);
        assertEquals(0, ClearType.Failed.gaugetype.length);
        assertEquals(1, ClearType.LightAssistEasy.gaugetype.length);
        assertEquals(1, ClearType.Easy.gaugetype.length);
        assertEquals(2, ClearType.Normal.gaugetype.length);
        assertEquals(3, ClearType.Hard.gaugetype.length);
        assertEquals(2, ClearType.ExHard.gaugetype.length);
        assertEquals(1, ClearType.FullCombo.gaugetype.length);
    }
}
