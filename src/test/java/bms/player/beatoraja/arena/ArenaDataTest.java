package bms.player.beatoraja.arena;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArenaDataTest {

    @Test
    public void testInitialization() {
        ArenaData data = new ArenaData("Player1");
        
        assertEquals("Player1", data.getPlayerName());
        assertEquals(0, data.getScore());
        assertEquals(0, data.getRank());
        assertEquals(0, data.getArenaPoints());
        assertFalse(data.isReady());
    }

    @Test
    public void testScoreTracking() {
        ArenaData data = new ArenaData("Player1");
        
        data.setScore(1500);
        assertEquals(1500, data.getScore());
        
        data.setScore(2000);
        assertEquals(2000, data.getScore());
    }

    @Test
    public void testRankTracking() {
        ArenaData data = new ArenaData("Player1");
        
        data.setRank(1);
        assertEquals(1, data.getRank());
        
        data.setRank(3);
        assertEquals(3, data.getRank());
    }

    @Test
    public void testArenaPoints() {
        ArenaData data = new ArenaData("Player1");
        
        data.setArenaPoints(2);
        assertEquals(2, data.getArenaPoints());
        
        data.setArenaPoints(-1);
        assertEquals(-1, data.getArenaPoints());
    }

    @Test
    public void testReadyState() {
        ArenaData data = new ArenaData("Player1");
        
        assertFalse(data.isReady());
        
        data.setReady(true);
        assertTrue(data.isReady());
        
        data.setReady(false);
        assertFalse(data.isReady());
    }

    @Test
    public void testPlayerNameImmutable() {
        ArenaData data = new ArenaData("OriginalName");
        assertEquals("OriginalName", data.getPlayerName());
    }
}
