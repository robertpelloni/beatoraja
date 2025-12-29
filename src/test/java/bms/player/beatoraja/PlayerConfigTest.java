package bms.player.beatoraja;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerConfigTest {

    private PlayerConfig config;

    @BeforeEach
    public void setUp() {
        config = new PlayerConfig();
    }

    @Test
    public void testDefaultName() {
        assertEquals("NO NAME", config.getName());
    }

    @Test
    public void testDefaultGauge() {
        assertEquals(0, config.getGauge());
    }

    @Test
    public void testDefaultRandom() {
        assertEquals(0, config.getRandom());
    }

    @Test
    public void testDefaultRandom2() {
        assertEquals(0, config.getRandom2());
    }

    @Test
    public void testDefaultDoubleoption() {
        assertEquals(0, config.getDoubleoption());
    }

    @Test
    public void testDefaultTargetId() {
        assertEquals("MAX", config.getTargetid());
    }

    @Test
    public void testDefaultJudgeTiming() {
        assertEquals(0, config.getJudgetiming());
    }

    @Test
    public void testDefaultLnMode() {
        assertEquals(0, config.getLnmode());
    }

    @Test
    public void testDefaultMisslayerDuration() {
        assertEquals(500, config.getMisslayerDuration());
    }

    @Test
    public void testDefaultHranThresholdBPM() {
        assertEquals(120, config.getHranThresholdBPM());
    }

    @Test
    public void testDefaultGaugeAutoShift() {
        assertEquals(PlayerConfig.GAUGEAUTOSHIFT_NONE, config.getGaugeAutoShift());
    }

    @Test
    public void testDefaultExitPressDuration() {
        assertEquals(1000, config.getExitPressDuration());
    }

    @Test
    public void testDefaultBooleanOptions() {
        assertFalse(config.isBpmguide());
        assertFalse(config.isGuideSE());
        assertFalse(config.isWindowHold());
        assertFalse(config.isShowhiddennote());
        assertFalse(config.isShowpastnote());
        assertFalse(config.isCustomJudge());
    }

    @Test
    public void testDefaultChartPreview() {
        assertTrue(config.isChartPreview());
    }

    @Test
    public void testDefaultJudgeWindowRates() {
        assertEquals(400, config.getKeyJudgeWindowRatePerfectGreat());
        assertEquals(400, config.getKeyJudgeWindowRateGreat());
        assertEquals(100, config.getKeyJudgeWindowRateGood());
        assertEquals(400, config.getScratchJudgeWindowRatePerfectGreat());
        assertEquals(400, config.getScratchJudgeWindowRateGreat());
        assertEquals(100, config.getScratchJudgeWindowRateGood());
    }

    @Test
    public void testDefaultScrollSettings() {
        assertEquals(0, config.getScrollMode());
        assertEquals(4, config.getScrollSection());
        assertEquals(0.5, config.getScrollRate(), 0.001);
    }

    @Test
    public void testDefaultLongnoteSettings() {
        assertEquals(0, config.getLongnoteMode());
        assertEquals(1.0, config.getLongnoteRate(), 0.001);
    }

    @Test
    public void testDefaultMineMode() {
        assertEquals(0, config.getMineMode());
    }

    @Test
    public void testSetName() {
        config.setName("TestPlayer");
        assertEquals("TestPlayer", config.getName());
    }

    @Test
    public void testSetId() {
        config.setId("player123");
        assertEquals("player123", config.getId());
    }

    @Test
    public void testSetGauge() {
        config.setGauge(3);
        assertEquals(3, config.getGauge());
    }

    @Test
    public void testSetRandom() {
        config.setRandom(5);
        assertEquals(5, config.getRandom());
    }

    @Test
    public void testSetRandom2() {
        config.setRandom2(3);
        assertEquals(3, config.getRandom2());
    }

    @Test
    public void testSetDoubleoption() {
        config.setDoubleoption(2);
        assertEquals(2, config.getDoubleoption());
    }

    @Test
    public void testSetTargetId() {
        config.setTargetid("RATE_AAA");
        assertEquals("RATE_AAA", config.getTargetid());
    }

    @Test
    public void testSetJudgeTiming() {
        config.setJudgetiming(50);
        assertEquals(50, config.getJudgetiming());
    }

    @Test
    public void testSetLnMode() {
        config.setLnmode(1);
        assertEquals(1, config.getLnmode());
    }

    @Test
    public void testSetBpmGuide() {
        config.setBpmguide(true);
        assertTrue(config.isBpmguide());
    }

    @Test
    public void testSetGuideSE() {
        config.setGuideSE(true);
        assertTrue(config.isGuideSE());
    }

    @Test
    public void testSetWindowHold() {
        config.setWindowHold(true);
        assertTrue(config.isWindowHold());
    }

    @Test
    public void testSetCustomJudge() {
        config.setCustomJudge(true);
        assertTrue(config.isCustomJudge());
    }

    @Test
    public void testSetShowHiddenNote() {
        config.setShowhiddennote(true);
        assertTrue(config.isShowhiddennote());
    }

    @Test
    public void testSetShowPastNote() {
        config.setShowpastnote(true);
        assertTrue(config.isShowpastnote());
    }

    @Test
    public void testSetHranThresholdBPM() {
        config.setHranThresholdBPM(180);
        assertEquals(180, config.getHranThresholdBPM());
    }

    @Test
    public void testSetGaugeAutoShift() {
        config.setGaugeAutoShift(PlayerConfig.GAUGEAUTOSHIFT_CONTINUE);
        assertEquals(PlayerConfig.GAUGEAUTOSHIFT_CONTINUE, config.getGaugeAutoShift());
    }

    @Test
    public void testSetExitPressDuration() {
        config.setExitPressDuration(2000);
        assertEquals(2000, config.getExitPressDuration());
    }

    @Test
    public void testSetScrollMode() {
        config.setScrollMode(1);
        assertEquals(1, config.getScrollMode());
    }

    @Test
    public void testSetScrollSection() {
        config.setScrollSection(8);
        assertEquals(8, config.getScrollSection());
    }

    @Test
    public void testSetScrollRate() {
        config.setScrollRate(0.75);
        assertEquals(0.75, config.getScrollRate(), 0.001);
    }

    @Test
    public void testSetLongnoteMode() {
        config.setLongnoteMode(1);
        assertEquals(1, config.getLongnoteMode());
    }

    @Test
    public void testSetLongnoteRate() {
        config.setLongnoteRate(0.8);
        assertEquals(0.8, config.getLongnoteRate(), 0.001);
    }

    @Test
    public void testSetMineMode() {
        config.setMineMode(1);
        assertEquals(1, config.getMineMode());
    }

    @Test
    public void testSetMisslayerDuration() {
        config.setMisslayerDuration(1000);
        assertEquals(1000, config.getMisslayerDuration());
    }

    @Test
    public void testSetJudgeWindowRates() {
        config.setKeyJudgeWindowRatePerfectGreat(300);
        config.setKeyJudgeWindowRateGreat(200);
        config.setKeyJudgeWindowRateGood(50);
        config.setScratchJudgeWindowRatePerfectGreat(350);
        config.setScratchJudgeWindowRateGreat(250);
        config.setScratchJudgeWindowRateGood(75);

        assertEquals(300, config.getKeyJudgeWindowRatePerfectGreat());
        assertEquals(200, config.getKeyJudgeWindowRateGreat());
        assertEquals(50, config.getKeyJudgeWindowRateGood());
        assertEquals(350, config.getScratchJudgeWindowRatePerfectGreat());
        assertEquals(250, config.getScratchJudgeWindowRateGreat());
        assertEquals(75, config.getScratchJudgeWindowRateGood());
    }

    @Test
    public void testSetChartPreview() {
        config.setChartPreview(false);
        assertFalse(config.isChartPreview());
    }

    @Test
    public void testSetEventMode() {
        config.setEventMode(true);
        assertTrue(config.isEventMode());
    }

    @Test
    public void testSetChartReplicationMode() {
        config.setChartReplicationMode("NONE");
        assertEquals("NONE", config.getChartReplicationMode());
    }

    @Test
    public void testValidateClampsGauge() {
        config.setGauge(10);
        config.validate();
        assertEquals(5, config.getGauge());

        config.setGauge(-5);
        config.validate();
        assertEquals(0, config.getGauge());
    }

    @Test
    public void testValidateClampsRandom() {
        config.setRandom(20);
        config.validate();
        assertEquals(9, config.getRandom());

        config.setRandom(-5);
        config.validate();
        assertEquals(0, config.getRandom());
    }

    @Test
    public void testValidateClampsJudgeTiming() {
        config.setJudgetiming(1000);
        config.validate();
        assertEquals(PlayerConfig.JUDGETIMING_MAX, config.getJudgetiming());

        config.setJudgetiming(-1000);
        config.validate();
        assertEquals(PlayerConfig.JUDGETIMING_MIN, config.getJudgetiming());
    }

    @Test
    public void testValidateClampsLnMode() {
        config.setLnmode(10);
        config.validate();
        assertEquals(2, config.getLnmode());
    }

    @Test
    public void testValidateClampsMisslayerDuration() {
        config.setMisslayerDuration(10000);
        config.validate();
        assertEquals(5000, config.getMisslayerDuration());

        config.setMisslayerDuration(-100);
        config.validate();
        assertEquals(0, config.getMisslayerDuration());
    }

    @Test
    public void testValidateClampsHranThresholdBPM() {
        config.setHranThresholdBPM(5000);
        config.validate();
        assertEquals(1000, config.getHranThresholdBPM());

        config.setHranThresholdBPM(0);
        config.validate();
        assertEquals(1, config.getHranThresholdBPM());
    }

    @Test
    public void testValidateClampsExitPressDuration() {
        config.setExitPressDuration(200000);
        config.validate();
        assertEquals(100000, config.getExitPressDuration());

        config.setExitPressDuration(-100);
        config.validate();
        assertEquals(0, config.getExitPressDuration());
    }

    @Test
    public void testValidateClampsJudgeWindowRates() {
        config.setKeyJudgeWindowRatePerfectGreat(10);
        config.setKeyJudgeWindowRateGreat(500);
        config.validate();
        assertEquals(25, config.getKeyJudgeWindowRatePerfectGreat());
        assertEquals(400, config.getKeyJudgeWindowRateGreat());
    }

    @Test
    public void testValidateClampsScrollSettings() {
        config.setScrollRate(2.0);
        config.setScrollSection(5000);
        config.validate();
        assertEquals(1.0, config.getScrollRate(), 0.001);
        assertEquals(1024, config.getScrollSection());

        config.setScrollRate(-1.0);
        config.setScrollSection(0);
        config.validate();
        assertEquals(0.0, config.getScrollRate(), 0.001);
        assertEquals(1, config.getScrollSection());
    }

    @Test
    public void testValidateClampsLongnoteRate() {
        config.setLongnoteRate(5.0);
        config.validate();
        assertEquals(1.0, config.getLongnoteRate(), 0.001);

        config.setLongnoteRate(-1.0);
        config.validate();
        assertEquals(0.0, config.getLongnoteRate(), 0.001);
    }

    @Test
    public void testValidateNullTargetId() {
        config.setTargetid(null);
        config.validate();
        assertEquals("MAX", config.getTargetid());
    }

    @Test
    public void testValidateNullChartReplicationMode() {
        config.setChartReplicationMode(null);
        config.validate();
        assertEquals("NONE", config.getChartReplicationMode());
    }

    @Test
    public void testJudgeTimingConstants() {
        assertEquals(500, PlayerConfig.JUDGETIMING_MAX);
        assertEquals(-500, PlayerConfig.JUDGETIMING_MIN);
    }

    @Test
    public void testGaugeAutoShiftConstants() {
        assertEquals(0, PlayerConfig.GAUGEAUTOSHIFT_NONE);
        assertEquals(1, PlayerConfig.GAUGEAUTOSHIFT_CONTINUE);
        assertEquals(2, PlayerConfig.GAUGEAUTOSHIFT_SURVIVAL_TO_GROOVE);
        assertEquals(3, PlayerConfig.GAUGEAUTOSHIFT_BESTCLEAR);
        assertEquals(4, PlayerConfig.GAUGEAUTOSHIFT_SELECT_TO_UNDER);
    }

    @Test
    public void testSetNotesDisplayTimingAutoAdjust() {
        assertFalse(config.isNotesDisplayTimingAutoAdjust());
        config.setNotesDisplayTimingAutoAdjust(true);
        assertTrue(config.isNotesDisplayTimingAutoAdjust());
    }

    @Test
    public void testSetRandomSelect() {
        assertFalse(config.isRandomSelect());
        config.setRandomSelect(true);
        assertTrue(config.isRandomSelect());
    }
}
