package bms.player.beatoraja;

import bms.model.Mode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreDataTest {

    @Test
    public void testExScoreCalculation() {
        ScoreData score = new ScoreData(Mode.BEAT_7K);
        score.setEpg(100);
        score.setLpg(50);
        score.setEgr(30);
        score.setLgr(20);
        
        int expected = (100 + 50) * 2 + 30 + 20;
        assertEquals(expected, score.getExscore());
    }

    @Test
    public void testJudgeCount() {
        ScoreData score = new ScoreData(Mode.BEAT_7K);
        score.setEpg(10);
        score.setLpg(5);
        
        assertEquals(15, score.getJudgeCount(0));
        assertEquals(10, score.getJudgeCount(0, true));
        assertEquals(5, score.getJudgeCount(0, false));
    }

    @Test
    public void testAddJudgeCount() {
        ScoreData score = new ScoreData(Mode.BEAT_7K);
        
        score.addJudgeCount(0, true, 5);
        score.addJudgeCount(0, false, 3);
        score.addJudgeCount(1, true, 2);
        
        assertEquals(5, score.getEpg());
        assertEquals(3, score.getLpg());
        assertEquals(2, score.getEgr());
        assertEquals(8, score.getJudgeCount(0));
    }

    @Test
    public void testFastSlowTracking() {
        ScoreData score = new ScoreData(Mode.BEAT_7K);
        
        score.setFastNotes(25);
        score.setSlowNotes(15);
        score.setFastScratch(5);
        score.setSlowScratch(3);
        
        assertEquals(25, score.getFastNotes());
        assertEquals(15, score.getSlowNotes());
        assertEquals(5, score.getFastScratch());
        assertEquals(3, score.getSlowScratch());
    }

    @Test
    public void testDefaultPlayMode() {
        ScoreData score = new ScoreData();
        assertEquals(Mode.BEAT_7K, score.getPlaymode());
    }

    @Test
    public void testSha256() {
        ScoreData score = new ScoreData();
        String hash = "abc123def456";
        score.setSha256(hash);
        assertEquals(hash, score.getSha256());
    }

    @Test
    public void testPlayerName() {
        ScoreData score = new ScoreData();
        score.setPlayer("TestPlayer");
        assertEquals("TestPlayer", score.getPlayer());
        
        score.setPlayer(null);
        assertEquals("", score.getPlayer());
    }

    @Test
    public void testNotesAndCombo() {
        ScoreData score = new ScoreData();
        score.setNotes(1000);
        score.setCombo(500);
        
        assertEquals(1000, score.getNotes());
        assertEquals(500, score.getCombo());
    }

    @Test
    public void testClearStatus() {
        ScoreData score = new ScoreData();
        score.setClear(5);
        score.setPlaycount(10);
        score.setClearcount(7);
        
        assertEquals(5, score.getClear());
        assertEquals(10, score.getPlaycount());
        assertEquals(7, score.getClearcount());
    }
}
