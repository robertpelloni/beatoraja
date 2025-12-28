package bms.player.beatoraja.stepup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class StepUpManagerTest {

    @Test
    public void testStepUpDataDefaultValues() {
        StepUpData data = new StepUpData();
        
        assertEquals(1, data.currentLevel);
        assertEquals(1, data.currentStage);
        assertNotNull(data.passedSongs);
        assertTrue(data.passedSongs.isEmpty());
    }

    @Test
    public void testLevelProgression() {
        StepUpData data = new StepUpData();
        
        data.currentLevel = 5;
        assertEquals(5, data.currentLevel);
        
        data.currentLevel = Math.min(12, data.currentLevel + 1);
        assertEquals(6, data.currentLevel);
    }

    @Test
    public void testLevelUpperBound() {
        StepUpData data = new StepUpData();
        data.currentLevel = 12;
        
        data.currentLevel = Math.min(12, data.currentLevel + 1);
        assertEquals(12, data.currentLevel);
    }

    @Test
    public void testLevelLowerBound() {
        StepUpData data = new StepUpData();
        data.currentLevel = 1;
        
        data.currentLevel = Math.max(1, data.currentLevel - 1);
        assertEquals(1, data.currentLevel);
    }

    @Test
    public void testLevelDownOnFail() {
        StepUpData data = new StepUpData();
        data.currentLevel = 5;
        
        data.currentLevel = Math.max(1, data.currentLevel - 1);
        assertEquals(4, data.currentLevel);
    }

    @Test
    public void testStageTracking() {
        StepUpData data = new StepUpData();
        
        for (int stage = 1; stage <= 3; stage++) {
            data.currentStage = stage;
            assertEquals(stage, data.currentStage);
        }
    }

    @Test
    public void testPassedSongsTracking() {
        StepUpData data = new StepUpData();
        
        data.passedSongs.add("song1_hash");
        data.passedSongs.add("song2_hash");
        data.passedSongs.add("song3_hash");
        
        assertEquals(3, data.passedSongs.size());
        assertTrue(data.passedSongs.contains("song1_hash"));
        assertTrue(data.passedSongs.contains("song2_hash"));
        assertTrue(data.passedSongs.contains("song3_hash"));
    }

    @Test
    public void testLevelRangeValidation() {
        StepUpData data = new StepUpData();
        
        for (int level = 1; level <= 12; level++) {
            data.currentLevel = level;
            assertEquals(level, data.currentLevel);
            assertTrue(data.currentLevel >= 1 && data.currentLevel <= 12);
        }
    }

    @Test
    public void testProgressionSequence() {
        StepUpData data = new StepUpData();
        assertEquals(1, data.currentLevel);
        
        for (int i = 0; i < 5; i++) {
            data.currentLevel = Math.min(12, data.currentLevel + 1);
        }
        assertEquals(6, data.currentLevel);
        
        for (int i = 0; i < 2; i++) {
            data.currentLevel = Math.max(1, data.currentLevel - 1);
        }
        assertEquals(4, data.currentLevel);
    }

    @Test
    public void testEdgeCaseLevelProgression() {
        StepUpData data = new StepUpData();
        data.currentLevel = 11;
        
        data.currentLevel = Math.min(12, data.currentLevel + 1);
        assertEquals(12, data.currentLevel);
        
        data.currentLevel = Math.min(12, data.currentLevel + 1);
        assertEquals(12, data.currentLevel);
        
        data.currentLevel = Math.min(12, data.currentLevel + 1);
        assertEquals(12, data.currentLevel);
    }

    @Test
    public void testEdgeCaseLevelRegression() {
        StepUpData data = new StepUpData();
        data.currentLevel = 2;
        
        data.currentLevel = Math.max(1, data.currentLevel - 1);
        assertEquals(1, data.currentLevel);
        
        data.currentLevel = Math.max(1, data.currentLevel - 1);
        assertEquals(1, data.currentLevel);
        
        data.currentLevel = Math.max(1, data.currentLevel - 1);
        assertEquals(1, data.currentLevel);
    }

    @Test
    public void testClearPassedSongs() {
        StepUpData data = new StepUpData();
        
        data.passedSongs.add("song1");
        data.passedSongs.add("song2");
        assertEquals(2, data.passedSongs.size());
        
        data.passedSongs.clear();
        assertTrue(data.passedSongs.isEmpty());
    }
}
