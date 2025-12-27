package bms.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class OsuDecoderBezierTest {

    @Test
    public void testBezierSlider() {
        // A simple Bezier slider
        // 100,100 -> 150,150 -> 200,100
        // Curve type B
        String osuContent = "osu file format v14\n" +
                "\n" +
                "[General]\n" +
                "AudioFilename: audio.mp3\n" +
                "Mode: 3\n" + // Mania
                "\n" +
                "[Difficulty]\n" +
                "CircleSize:4\n" +
                "SliderMultiplier:1.0\n" +
                "\n" +
                "[TimingPoints]\n" +
                "0,1000,4,1,0,100,1,0\n" + // 60 BPM
                "\n" +
                "[HitObjects]\n" +
                // x,y,time,type,hitSound,curveType|curvePoints,slides,length
                // x=0 (Column 0/Lane 1 in 4K)
                // time=0
                // type=2 (Slider)
                // B|50:50|100:0
                // slides=1
                // length=100
                "0,0,0,2,0,B|50:50|100:0,1,100\n";

        OsuDecoder decoder = new OsuDecoder(0);
        BMSModel model = decoder.decode(new ByteArrayInputStream(osuContent.getBytes(StandardCharsets.UTF_8)));

        assertNotNull(model);
        
        // Check if notes are generated
        TimeLine[] timelines = model.getAllTimeLine();
        assertTrue(timelines.length > 0);
        
        // The slider starts at x=0 (Lane 1)
        // It curves to x=50 (approx Lane 1 or 2 depending on width) and x=100 (Lane 2)
        // 4K width is 512. Column width = 128.
        // Col 0: 0-128. Col 1: 128-256.
        // x=0 -> Col 0 -> Lane 1
        // x=50 -> Col 0 -> Lane 1
        // x=100 -> Col 0 -> Lane 1
        // Wait, 100 is still in Col 0.
        
        // Let's make it span columns.
        // x=0 -> Col 0
        // x=200 -> Col 1 (200 > 128)
        
        // Let's try a wider curve.
    }
    
    @Test
    public void testBezierSliderCrossColumn() {
        String osuContent = "osu file format v14\n" +
                "\n" +
                "[General]\n" +
                "AudioFilename: audio.mp3\n" +
                "Mode: 3\n" +
                "\n" +
                "[Difficulty]\n" +
                "CircleSize:4\n" +
                "SliderMultiplier:1.0\n" +
                "\n" +
                "[TimingPoints]\n" +
                "0,1000,4,1,0,100,1,0\n" +
                "\n" +
                "[HitObjects]\n" +
                // Start at x=0 (Col 0)
                // Curve to x=200 (Col 1)
                // Length large enough to ensure travel
                "0,0,0,2,0,B|100:0|200:0,1,200\n";

        OsuDecoder decoder = new OsuDecoder(0);
        BMSModel model = decoder.decode(new ByteArrayInputStream(osuContent.getBytes(StandardCharsets.UTF_8)));

        assertNotNull(model);
        
        TimeLine[] timelines = model.getAllTimeLine();
        
        boolean foundLane1 = false;
        boolean foundLane2 = false;
        
        for (TimeLine tl : timelines) {
            if (tl.getNote(1) != null) foundLane1 = true;
            if (tl.getNote(2) != null) foundLane2 = true;
        }
        
        assertTrue(foundLane1, "Should have notes in Lane 1");
        assertTrue(foundLane2, "Should have notes in Lane 2 (crossed column)");
    }
}
