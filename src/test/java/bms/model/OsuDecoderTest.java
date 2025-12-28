package bms.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class OsuDecoderTest {

    @Test
    public void testSliderDuration() {
        String osuContent = "osu file format v14\n" +
                "\n" +
                "[General]\n" +
                "AudioFilename: audio.mp3\n" +
                "AudioLeadIn: 0\n" +
                "PreviewTime: -1\n" +
                "Countdown: 0\n" +
                "SampleSet: Normal\n" +
                "StackLeniency: 0.7\n" +
                "Mode: 3\n" +
                "LetterboxInBreaks: 0\n" +
                "WidescreenStoryboard: 0\n" +
                "\n" +
                "[Editor]\n" +
                "DistanceSpacing: 0.8\n" +
                "BeatDivisor: 4\n" +
                "GridSize: 32\n" +
                "TimelineZoom: 1\n" +
                "\n" +
                "[Metadata]\n" +
                "Title:Test Song\n" +
                "TitleUnicode:Test Song\n" +
                "Artist:Test Artist\n" +
                "ArtistUnicode:Test Artist\n" +
                "Creator:Test Creator\n" +
                "Version:Normal\n" +
                "Source:\n" +
                "Tags:\n" +
                "BeatmapID:0\n" +
                "BeatmapSetID:0\n" +
                "\n" +
                "[Difficulty]\n" +
                "HPDrainRate:5\n" +
                "CircleSize:4\n" +
                "OverallDifficulty:5\n" +
                "ApproachRate:5\n" +
                "SliderMultiplier:1.4\n" +
                "SliderTickRate:1\n" +
                "\n" +
                "[Events]\n" +
                "//Background and Video events\n" +
                "//Break Periods\n" +
                "//Storyboard Layer 0 (Background)\n" +
                "//Storyboard Layer 1 (Fail)\n" +
                "//Storyboard Layer 2 (Pass)\n" +
                "//Storyboard Layer 3 (Foreground)\n" +
                "//Storyboard Layer 4 (Overlay)\n" +
                "//Storyboard Sound Samples\n" +
                "\n" +
                "[TimingPoints]\n" +
                "0,500,4,1,0,100,1,0\n" +
                "\n" +
                "[HitObjects]\n" +
                "64,192,1000,2,0,L|100:100,1,140\n"; 
                // Time 1000, Type 2 (Slider), Length 140
                // BPM 120 (500ms/beat)
                // SliderMultiplier 1.4
                // SV 1.0 (Default)
                // Duration = 140 / (1.4 * 100 * 1.0) * 500 * 1 = 1 * 500 = 500ms
                // EndTime = 1500

        OsuDecoder decoder = new OsuDecoder(0);
        BMSModel model = decoder.decode(new ByteArrayInputStream(osuContent.getBytes(StandardCharsets.UTF_8)));

        assertNotNull(model);
        assertEquals(Mode.BEAT_5K, model.getMode()); // 4K -> BEAT_5K (internally mapped)

        TimeLine[] timelines = model.getAllTimeLines();
        assertNotNull(timelines);
        assertTrue(timelines.length > 0);

        // Find the note at 1000ms
        boolean found = false;
        for (TimeLine tl : timelines) {
            if (tl.getTime() == 1000) {
                Note note = tl.getNote(1); // Lane 1 (Column 0)
                assertNotNull(note);
                assertTrue(note instanceof LongNote);
                assertEquals(500 * 1000, ((LongNote) note).getMicroDuration()); // 500ms in microseconds
                found = true;
                break;
            }
        }
        assertTrue(found, "Slider note not found at 1000ms");
    }

    @Test
    public void testBackgroundParsing() {
        String osuContent = "osu file format v14\n" +
                "\n" +
                "[General]\n" +
                "AudioFilename: audio.mp3\n" +
                "Mode: 3\n" +
                "\n" +
                "[Metadata]\n" +
                "Title:Test Song\n" +
                "Artist:Test Artist\n" +
                "Version:Normal\n" +
                "\n" +
                "[Events]\n" +
                "//Background and Video events\n" +
                "0,0,\"bg.jpg\",0,0\n" +
                "//Break Periods\n" +
                "\n" +
                "[TimingPoints]\n" +
                "0,500,4,1,0,100,1,0\n" +
                "\n" +
                "[HitObjects]\n" +
                "64,192,1000,1,0,0:0:0:0:\n";

        OsuDecoder decoder = new OsuDecoder(0);
        BMSModel model = decoder.decode(new ByteArrayInputStream(osuContent.getBytes(StandardCharsets.UTF_8)));

        assertNotNull(model);
        assertEquals("bg.jpg", model.getStagefile());
        assertEquals("bg.jpg", model.getBackbmp());
    }

    @Test
    public void testAudioHandling() {
        String osuContent = "osu file format v14\n" +
                "\n" +
                "[General]\n" +
                "AudioFilename: audio.mp3\n" +
                "Mode: 3\n" +
                "\n" +
                "[Metadata]\n" +
                "Title:Test Song\n" +
                "Artist:Test Artist\n" +
                "Version:Normal\n" +
                "\n" +
                "[TimingPoints]\n" +
                "0,500,4,1,0,100,1,0\n" +
                "\n" +
                "[HitObjects]\n" +
                "64,192,1000,1,0,0:0:0:0:\n";

        OsuDecoder decoder = new OsuDecoder(0);
        BMSModel model = decoder.decode(new ByteArrayInputStream(osuContent.getBytes(StandardCharsets.UTF_8)));

        assertNotNull(model);
        
        // Check Background Audio
        TimeLine[] timelines = model.getAllTimeLines();
        boolean bgmFound = false;
        for (TimeLine tl : timelines) {
            if (tl.getTime() == 0) {
                Note[] bgNotes = tl.getBackGroundNotes();
                if (bgNotes != null) {
                    for (Note n : bgNotes) {
                        if (n.getWav() == 1) { // WAV 1 is audio.mp3
                            bgmFound = true;
                            break;
                        }
                    }
                }
            }
        }
        assertTrue(bgmFound, "Background audio not found at time 0");

        // Check Note Audio (Should be 0/Silent)
        boolean noteFound = false;
        for (TimeLine tl : timelines) {
            if (tl.getTime() == 1000) {
                Note note = tl.getNote(1);
                assertNotNull(note);
                assertEquals(0, note.getWav(), "Note should be silent (WAV 0)");
                noteFound = true;
                break;
            }
        }
        assertTrue(noteFound, "Note not found at 1000ms");
    }

    @Test
    public void testVideoParsing() {
        String osuContent = "osu file format v14\n" +
                "\n" +
                "[General]\n" +
                "AudioFilename: audio.mp3\n" +
                "Mode: 3\n" +
                "\n" +
                "[Events]\n" +
                "//Background and Video events\n" +
                "0,0,\"bg.jpg\",0,0\n" +
                "Video,0,\"video.avi\"\n" +
                "\n" +
                "[TimingPoints]\n" +
                "0,500,4,1,0,100,1,0\n" +
                "\n" +
                "[HitObjects]\n" +
                "64,192,1000,1,0,0:0:0:0:\n";

        OsuDecoder decoder = new OsuDecoder(0);
        BMSModel model = decoder.decode(new ByteArrayInputStream(osuContent.getBytes(StandardCharsets.UTF_8)));

        assertNotNull(model);
        assertEquals("bg.jpg", model.getStagefile());
        assertEquals("bg.jpg", model.getBackbmp());
        
        // Check BGA List
        String[] bgaList = model.getBgaList();
        assertNotNull(bgaList);
        boolean videoFound = false;
        int videoIndex = -1;
        for (int i = 0; i < bgaList.length; i++) {
            if ("video.avi".equals(bgaList[i])) {
                videoFound = true;
                videoIndex = i;
                break;
            }
        }
        assertTrue(videoFound, "Video file not found in BGA list");

        // Check BGA Event
        TimeLine[] timelines = model.getAllTimeLines();
        boolean bgaEventFound = false;
        for (TimeLine tl : timelines) {
            if (tl.getTime() == 0) {
                if (tl.getBGA() == videoIndex) {
                    bgaEventFound = true;
                    break;
                }
            }
        }
        assertTrue(bgaEventFound, "BGA event not found at time 0");
    }

    @Test
    public void testSpinnerParsing() {
        // Spinner format: x,y,time,type,hitSound,endTime,extras
        // Type 8 = Spinner
        String osuContent = "osu file format v14\n" +
                "\n" +
                "[General]\n" +
                "AudioFilename: audio.mp3\n" +
                "Mode: 3\n" +
                "\n" +
                "[Metadata]\n" +
                "Title:Test Song\n" +
                "Artist:Test Artist\n" +
                "Version:Normal\n" +
                "\n" +
                "[Difficulty]\n" +
                "CircleSize:7\n" +
                "\n" +
                "[TimingPoints]\n" +
                "0,500,4,1,0,100,1,0\n" +
                "\n" +
                "[HitObjects]\n" +
                "256,192,2000,8,0,4000,0:0:0:0:\n"; // Spinner from 2000ms to 4000ms

        OsuDecoder decoder = new OsuDecoder(0);
        BMSModel model = decoder.decode(new ByteArrayInputStream(osuContent.getBytes(StandardCharsets.UTF_8)));

        assertNotNull(model);
        assertEquals(Mode.BEAT_7K, model.getMode());

        // Find the spinner (should be on scratch lane 0)
        TimeLine[] timelines = model.getAllTimeLines();
        boolean spinnerFound = false;
        for (TimeLine tl : timelines) {
            if (tl.getTime() == 2000) {
                Note note = tl.getNote(0); // Lane 0 = Scratch
                assertNotNull(note, "Spinner note should be on lane 0");
                assertTrue(note instanceof LongNote, "Spinner should be a LongNote");
                // Duration = 4000 - 2000 = 2000ms = 2000000 microseconds
                assertEquals(2000 * 1000, ((LongNote) note).getMicroDuration());
                spinnerFound = true;
                break;
            }
        }
        assertTrue(spinnerFound, "Spinner not found at 2000ms");
    }

    @Test
    public void testTimingPointChanges() {
        // Test multiple BPM changes and inherited timing points (SV changes)
        // TimingPoints format: time,beatLength,meter,sampleSet,sampleIndex,volume,uninherited,effects
        // uninherited=1 means red line (BPM change), uninherited=0 means green line (SV change)
        // For inherited points, beatLength is negative: -100 = 1.0x SV, -50 = 2.0x SV, -200 = 0.5x SV
        String osuContent = "osu file format v14\n" +
                "\n" +
                "[General]\n" +
                "AudioFilename: audio.mp3\n" +
                "Mode: 3\n" +
                "\n" +
                "[Metadata]\n" +
                "Title:Test Song\n" +
                "Artist:Test Artist\n" +
                "Version:Normal\n" +
                "\n" +
                "[Difficulty]\n" +
                "CircleSize:7\n" +
                "SliderMultiplier:1.4\n" +
                "\n" +
                "[TimingPoints]\n" +
                "0,500,4,1,0,100,1,0\n" +       // 0ms: 120 BPM (500ms/beat), uninherited
                "2000,400,4,1,0,100,1,0\n" +    // 2000ms: 150 BPM (400ms/beat), uninherited
                "4000,-50,4,1,0,100,0,0\n" +    // 4000ms: 2.0x SV (inherited), BPM stays 150
                "6000,300,4,1,0,100,1,0\n" +    // 6000ms: 200 BPM (300ms/beat), uninherited (resets SV to 1.0)
                "\n" +
                "[HitObjects]\n" +
                "64,192,1000,1,0,0:0:0:0:\n" +  // Note at 1000ms (should be 120 BPM)
                "64,192,3000,1,0,0:0:0:0:\n" +  // Note at 3000ms (should be 150 BPM)
                "64,192,5000,1,0,0:0:0:0:\n" +  // Note at 5000ms (should be 150 BPM, 2.0x SV)
                "64,192,7000,1,0,0:0:0:0:\n";   // Note at 7000ms (should be 200 BPM, 1.0x SV)

        OsuDecoder decoder = new OsuDecoder(0);
        BMSModel model = decoder.decode(new ByteArrayInputStream(osuContent.getBytes(StandardCharsets.UTF_8)));

        assertNotNull(model);
        
        // Check initial BPM is set to first uninherited timing point
        assertEquals(120.0, model.getBpm(), 0.01, "Initial BPM should be 120");

        TimeLine[] timelines = model.getAllTimeLines();
        assertNotNull(timelines);
        assertTrue(timelines.length > 0);

        // Verify BPM changes at timing points
        boolean found0 = false, found2000 = false, found4000 = false, found6000 = false;
        boolean found1000 = false, found3000 = false, found5000 = false, found7000 = false;
        
        for (TimeLine tl : timelines) {
            long time = tl.getTime();
            
            // Check timing point timelines
            if (time == 0) {
                assertEquals(120.0, tl.getBPM(), 0.01, "BPM at 0ms should be 120");
                assertEquals(1.0, tl.getScroll(), 0.01, "SV at 0ms should be 1.0");
                found0 = true;
            } else if (time == 2000) {
                assertEquals(150.0, tl.getBPM(), 0.01, "BPM at 2000ms should be 150");
                assertEquals(1.0, tl.getScroll(), 0.01, "SV at 2000ms should be 1.0 (uninherited resets SV)");
                found2000 = true;
            } else if (time == 4000) {
                assertEquals(150.0, tl.getBPM(), 0.01, "BPM at 4000ms should still be 150");
                assertEquals(2.0, tl.getScroll(), 0.01, "SV at 4000ms should be 2.0");
                found4000 = true;
            } else if (time == 6000) {
                assertEquals(200.0, tl.getBPM(), 0.01, "BPM at 6000ms should be 200");
                assertEquals(1.0, tl.getScroll(), 0.01, "SV at 6000ms should be 1.0 (uninherited resets SV)");
                found6000 = true;
            }
            
            // Check note timelines inherit correct BPM/SV
            if (time == 1000) {
                assertEquals(120.0, tl.getBPM(), 0.01, "Note at 1000ms should have 120 BPM");
                assertEquals(1.0, tl.getScroll(), 0.01, "Note at 1000ms should have 1.0 SV");
                assertNotNull(tl.getNote(1), "Note should exist at 1000ms");
                found1000 = true;
            } else if (time == 3000) {
                assertEquals(150.0, tl.getBPM(), 0.01, "Note at 3000ms should have 150 BPM");
                assertEquals(1.0, tl.getScroll(), 0.01, "Note at 3000ms should have 1.0 SV");
                assertNotNull(tl.getNote(1), "Note should exist at 3000ms");
                found3000 = true;
            } else if (time == 5000) {
                assertEquals(150.0, tl.getBPM(), 0.01, "Note at 5000ms should have 150 BPM");
                assertEquals(2.0, tl.getScroll(), 0.01, "Note at 5000ms should have 2.0 SV");
                assertNotNull(tl.getNote(1), "Note should exist at 5000ms");
                found5000 = true;
            } else if (time == 7000) {
                assertEquals(200.0, tl.getBPM(), 0.01, "Note at 7000ms should have 200 BPM");
                assertEquals(1.0, tl.getScroll(), 0.01, "Note at 7000ms should have 1.0 SV");
                assertNotNull(tl.getNote(1), "Note should exist at 7000ms");
                found7000 = true;
            }
        }
        
        // Verify all timing point timelines were found
        assertTrue(found0, "Timeline at 0ms not found");
        assertTrue(found2000, "Timeline at 2000ms not found");
        assertTrue(found4000, "Timeline at 4000ms not found");
        assertTrue(found6000, "Timeline at 6000ms not found");
        
        // Verify all note timelines were found
        assertTrue(found1000, "Note timeline at 1000ms not found");
        assertTrue(found3000, "Note timeline at 3000ms not found");
        assertTrue(found5000, "Note timeline at 5000ms not found");
        assertTrue(found7000, "Note timeline at 7000ms not found");
    }
}
