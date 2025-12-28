package bms.player.beatoraja.arena;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ArenaManagerTest {

    private ArenaManager manager;

    @BeforeEach
    public void setUp() {
        manager = new ArenaManager();
    }

    @Test
    public void testInitialization() {
        assertNotNull(manager.getPlayers());
        assertTrue(manager.getPlayers().isEmpty());
        assertFalse(manager.isHost());
    }

    @Test
    public void testAddPlayer() {
        manager.addPlayer("Player1");
        
        assertEquals(1, manager.getPlayers().size());
        assertNotNull(manager.getPlayer("Player1"));
        assertEquals("Player1", manager.getPlayer("Player1").getPlayerName());
    }

    @Test
    public void testAddDuplicatePlayer() {
        manager.addPlayer("Player1");
        manager.addPlayer("Player1");
        
        assertEquals(1, manager.getPlayers().size());
    }

    @Test
    public void testAddMultiplePlayers() {
        manager.addPlayer("Player1");
        manager.addPlayer("Player2");
        manager.addPlayer("Player3");
        
        assertEquals(3, manager.getPlayers().size());
        assertNotNull(manager.getPlayer("Player1"));
        assertNotNull(manager.getPlayer("Player2"));
        assertNotNull(manager.getPlayer("Player3"));
    }

    @Test
    public void testGetNonExistentPlayer() {
        assertNull(manager.getPlayer("NonExistent"));
    }

    @Test
    public void testUpdateScoreExistingPlayer() {
        manager.addPlayer("Player1");
        manager.updateScore("Player1", 1500);
        
        assertEquals(1500, manager.getPlayer("Player1").getScore());
    }

    @Test
    public void testUpdateScoreCreatesPlayer() {
        manager.updateScore("NewPlayer", 2000);
        
        assertEquals(1, manager.getPlayers().size());
        assertNotNull(manager.getPlayer("NewPlayer"));
        assertEquals(2000, manager.getPlayer("NewPlayer").getScore());
    }

    @Test
    public void testCalculateRanksWithSinglePlayer() {
        manager.addPlayer("Player1");
        manager.updateScore("Player1", 1000);
        
        assertEquals(1, manager.getPlayer("Player1").getRank());
        assertEquals(2, manager.getPlayer("Player1").getArenaPoints());
    }

    @Test
    public void testCalculateRanksWithMultiplePlayers() {
        manager.addPlayer("Player1");
        manager.addPlayer("Player2");
        manager.addPlayer("Player3");
        manager.addPlayer("Player4");
        
        manager.updateScore("Player1", 1000);
        manager.updateScore("Player2", 2000);
        manager.updateScore("Player3", 1500);
        manager.updateScore("Player4", 500);
        
        assertEquals(1, manager.getPlayer("Player2").getRank());
        assertEquals(2, manager.getPlayer("Player2").getArenaPoints());
        
        assertEquals(2, manager.getPlayer("Player3").getRank());
        assertEquals(1, manager.getPlayer("Player3").getArenaPoints());
        
        assertEquals(3, manager.getPlayer("Player1").getRank());
        assertEquals(0, manager.getPlayer("Player1").getArenaPoints());
        
        assertEquals(4, manager.getPlayer("Player4").getRank());
        assertEquals(-1, manager.getPlayer("Player4").getArenaPoints());
    }

    @Test
    public void testResetScores() {
        manager.addPlayer("Player1");
        manager.addPlayer("Player2");
        
        manager.updateScore("Player1", 1500);
        manager.updateScore("Player2", 2000);
        
        manager.resetScores();
        
        assertEquals(0, manager.getPlayer("Player1").getScore());
        assertEquals(0, manager.getPlayer("Player1").getRank());
        assertEquals(0, manager.getPlayer("Player1").getArenaPoints());
        
        assertEquals(0, manager.getPlayer("Player2").getScore());
        assertEquals(0, manager.getPlayer("Player2").getRank());
        assertEquals(0, manager.getPlayer("Player2").getArenaPoints());
    }

    @Test
    public void testSongHashTracking() {
        assertNull(manager.getCurrentSongHash());
        
        manager.sendSongSelect("abc123");
        assertEquals("abc123", manager.getCurrentSongHash());
    }

    @Test
    public void testRuleGauge() {
        assertEquals(-1, manager.getRuleGauge());
        
        manager.onRemoteRules(3);
        assertEquals(3, manager.getRuleGauge());
    }

    @Test
    public void testOnRemoteReady() {
        manager.addPlayer("Player1");
        assertFalse(manager.getPlayer("Player1").isReady());
        
        manager.onRemoteReady("Player1", true);
        assertTrue(manager.getPlayer("Player1").isReady());
        
        manager.onRemoteReady("Player1", false);
        assertFalse(manager.getPlayer("Player1").isReady());
    }

    @Test
    public void testOnRemoteReadyNonExistentPlayer() {
        manager.onRemoteReady("NonExistent", true);
    }

    @Test
    public void testListenerManagement() {
        final boolean[] songSelected = {false};
        final boolean[] gameStarted = {false};
        
        ArenaManager.ArenaListener listener = new ArenaManager.ArenaListener() {
            @Override
            public void onSongSelected(String songHash) {
                songSelected[0] = true;
            }
            
            @Override
            public void onStartGame() {
                gameStarted[0] = true;
            }
        };
        
        manager.addListener(listener);
        
        manager.onRemoteSongSelected("hash123");
        assertTrue(songSelected[0]);
        assertEquals("hash123", manager.getCurrentSongHash());
        
        manager.onStartGame();
        assertTrue(gameStarted[0]);
        
        manager.removeListener(listener);
    }

    @Test
    public void testCalculateRanksEmptyPlayers() {
        manager.calculateRanks();
    }
}
