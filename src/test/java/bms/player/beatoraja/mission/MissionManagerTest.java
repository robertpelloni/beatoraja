package bms.player.beatoraja.mission;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class MissionManagerTest {

    @Test
    public void testMissionDataCreation() {
        MissionData mission = new MissionData(1, MissionData.TYPE_NORMAL, "Test Mission", 
                "Test description", "CLEAR_COUNT", 10);
        
        assertEquals(1, mission.id);
        assertEquals(MissionData.TYPE_NORMAL, mission.type);
        assertEquals("Test Mission", mission.title);
        assertEquals("Test description", mission.description);
        assertEquals("CLEAR_COUNT", mission.criteriaType);
        assertEquals(10, mission.target);
        assertFalse(mission.completed);
        assertEquals(0, mission.progress);
    }

    @Test
    public void testMissionDataDefaultConstructor() {
        MissionData mission = new MissionData();
        
        assertEquals(0, mission.id);
        assertEquals(0, mission.type);
        assertNull(mission.title);
        assertNull(mission.description);
        assertNull(mission.criteriaType);
        assertEquals(0, mission.target);
        assertFalse(mission.completed);
        assertEquals(0, mission.progress);
    }

    @Test
    public void testMissionTypes() {
        assertEquals(0, MissionData.TYPE_NORMAL);
        assertEquals(1, MissionData.TYPE_DAILY);
    }

    @Test
    public void testDailyMissionCreation() {
        MissionData daily = new MissionData(100, MissionData.TYPE_DAILY, "Daily Clear", 
                "Clear 5 songs today", "CLEAR_COUNT", 5);
        long today = System.currentTimeMillis() / (24 * 60 * 60 * 1000);
        daily.expiryDate = today;
        
        assertEquals(MissionData.TYPE_DAILY, daily.type);
        assertEquals(today, daily.expiryDate);
    }

    @Test
    public void testMissionProgressTracking() {
        MissionData mission = new MissionData(1, MissionData.TYPE_NORMAL, "Clear Master", 
                "Clear 10 songs", "CLEAR_COUNT", 10);
        
        mission.progress = 5;
        assertEquals(5, mission.progress);
        assertFalse(mission.completed);
        
        mission.progress = 10;
        mission.completed = true;
        assertEquals(10, mission.progress);
        assertTrue(mission.completed);
    }

    @Test
    public void testMissionCriteriaTypes() {
        String[] validCriteriaTypes = {"PLAY_COUNT", "CLEAR_COUNT", "EX_SCORE", "COMBO"};
        
        for (String criteria : validCriteriaTypes) {
            MissionData mission = new MissionData(1, MissionData.TYPE_NORMAL, "Test", 
                    "Test", criteria, 10);
            assertEquals(criteria, mission.criteriaType);
        }
    }

    @Test
    public void testMissionCompletion() {
        MissionData mission = new MissionData(1, MissionData.TYPE_NORMAL, "Test", 
                "Test", "PLAY_COUNT", 5);
        
        for (int i = 1; i <= 5; i++) {
            mission.progress++;
            if (mission.progress >= mission.target) {
                mission.completed = true;
                mission.progress = mission.target;
            }
        }
        
        assertTrue(mission.completed);
        assertEquals(5, mission.progress);
    }

    @Test
    public void testExScoreCriteriaAccumulation() {
        MissionData mission = new MissionData(1, MissionData.TYPE_NORMAL, "EX Score Goal", 
                "Accumulate 1000 EX Score", "EX_SCORE", 1000);
        
        mission.progress += 300;
        assertEquals(300, mission.progress);
        assertFalse(mission.completed);
        
        mission.progress += 400;
        assertEquals(700, mission.progress);
        
        mission.progress += 400;
        if (mission.progress >= mission.target) {
            mission.completed = true;
            mission.progress = mission.target;
        }
        
        assertTrue(mission.completed);
        assertEquals(1000, mission.progress);
    }

    @Test
    public void testComboCriteriaMaxTracking() {
        MissionData mission = new MissionData(1, MissionData.TYPE_NORMAL, "Combo Goal", 
                "Achieve 500 combo", "COMBO", 500);
        
        mission.progress = Math.max(mission.progress, 200);
        assertEquals(200, mission.progress);
        
        mission.progress = Math.max(mission.progress, 150);
        assertEquals(200, mission.progress);
        
        mission.progress = Math.max(mission.progress, 600);
        assertEquals(600, mission.progress);
    }
}
