package bms.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.CatmullRomSpline;

/**
 * Basic Osu file decoder to convert .osu files to BMSModel.
 * Direct port attempt/adaptation for basic 7K support.
 */
public class OsuDecoder {

    private final int lntype;

    public OsuDecoder(int lntype) {
        this.lntype = lntype;
    }

    public BMSModel decode(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return decode(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return new BMSModel();
        }
    }

    public BMSModel decode(java.io.InputStream inputStream) {
        BMSModel model = new BMSModel();
        model.setLnmode(lntype);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            String section = "";
            List<HitObject> hitObjects = new ArrayList<>();
            List<TimingPoint> timingPoints = new ArrayList<>();

            String[] wavList = new String[1295];
            String[] bgaList = new String[1295];
            int keys = 4; // Default to 4K if undefined
            double sliderMultiplier = 1.4; // Default
            List<BGAEvent> bgaEvents = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) continue;

                if (line.startsWith("[")) {
                    section = line.substring(1, line.length() - 1);
                    continue;
                }

                if (section.equals("General")) {
                    if (line.startsWith("AudioFilename:")) {
                        String[] parts = line.split(":", 2);
                        if (parts.length > 1) {
                            String audio = parts[1].trim();
                            if (audio.length() > 0) {
                                wavList[1] = audio;
                            }
                        }
                    }
                } else if (section.equals("Metadata")) {
                    if (line.startsWith("Title:")) model.setTitle(line.split(":", 2)[1].trim());
                    if (line.startsWith("Artist:")) model.setArtist(line.split(":", 2)[1].trim());
                    if (line.startsWith("Version:")) model.setSubTitle(line.split(":", 2)[1].trim());
                } else if (section.equals("Difficulty")) {
                    if (line.startsWith("CircleSize:")) {
                        try {
                            keys = (int) Double.parseDouble(line.split(":", 2)[1].trim());
                        } catch (Exception e) {}
                    } else if (line.startsWith("SliderMultiplier:")) {
                        try {
                            sliderMultiplier = Double.parseDouble(line.split(":", 2)[1].trim());
                        } catch (Exception e) {}
                    }
                } else if (section.equals("Events")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String type = parts[0];
                        String filename = parts[2];
                        if (filename.startsWith("\"") && filename.endsWith("\"")) {
                            filename = filename.substring(1, filename.length() - 1);
                        }

                        if (type.equals("0")) {
                             model.setStagefile(filename);
                             model.setBackbmp(filename);
                        } else if (type.equals("1") || type.equals("Video")) {
                             // Add to BGA List
                             int bgaIndex = -1;
                             for(int i=0; i<bgaList.length; i++) {
                                 if(bgaList[i] == null) {
                                     bgaList[i] = filename;
                                     bgaIndex = i;
                                     break;
                                 } else if (bgaList[i].equals(filename)) {
                                     bgaIndex = i;
                                     break;
                                 }
                             }
                             
                             if (bgaIndex != -1) {
                                 long startTime = 0;
                                 if (parts.length >= 2) {
                                     try {
                                         startTime = Long.parseLong(parts[1]);
                                     } catch (Exception e) {}
                                 }
                                 bgaEvents.add(new BGAEvent(startTime, bgaIndex));
                             }
                        }
                    }
                } else if (section.equals("TimingPoints")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        try {
                            double offset = Double.parseDouble(parts[0]);
                            double beatLength = Double.parseDouble(parts[1]);
                            boolean uninherited = true;
                            if (parts.length >= 7) {
                                uninherited = Integer.parseInt(parts[6]) == 1;
                            }
                            timingPoints.add(new TimingPoint(offset, beatLength, uninherited));
                        } catch (Exception e) {}
                    }
                } else if (section.equals("HitObjects")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        try {
                            int x = Integer.parseInt(parts[0]);
                            int y = Integer.parseInt(parts[1]);
                            long time = Long.parseLong(parts[2]);
                            int type = Integer.parseInt(parts[3]);
                            int hitSound = parts.length >= 5 ? Integer.parseInt(parts[4]) : 0;

                            // Spinner (Type 8) -> Scratch LN (Lane 0)
                            if ((type & 8) > 0) {
                                long endTime = Long.parseLong(parts[5]); // Spinners have end time in 6th field
                                hitObjects.add(new HitObject(0, time, endTime, hitSound)); // Lane 0 = Scratch
                            } else {
                                int column = (int)(x * keys / 512.0);
                                column = Math.max(0, Math.min(keys - 1, column));

                                int lane;
                                if (keys == 8) {
                                    // 8K Mode: 0=Scratch, 1-7=Keys
                                    // Map Column 0 to Scratch (0), 1-7 to Keys 1-7
                                    if (column == 0) lane = 0;
                                    else lane = column; // 1-7 stays 1-7
                                } else {
                                    // Standard: Column 0 -> Key 1
                                    lane = column + 1;
                                }

                                if ((type & 128) > 0) { // Hold Note
                                    String[] subparts = parts[5].split(":");
                                    long endTime = Long.parseLong(subparts[0]);
                                    hitObjects.add(new HitObject(lane, time, endTime, hitSound));
                                } else if ((type & 2) > 0) { // Slider
                                    // x,y,time,type,hitSound,curveType|curvePoints,slides,length
                                    if (parts.length >= 8) {
                                        int slides = Integer.parseInt(parts[6]);
                                        double length = Double.parseDouble(parts[7]);
                                        
                                        String[] curveParts = parts[5].split("\\|");
                                        char curveType = curveParts.length > 0 ? curveParts[0].charAt(0) : 'L';
                                        List<Vector2> points = new ArrayList<>();
                                        points.add(new Vector2(x, y));
                                        for (int i = 1; i < curveParts.length; i++) {
                                            String[] xy = curveParts[i].split(":");
                                            points.add(new Vector2(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
                                        }

                                        // Duration will be calculated in convert()
                                        hitObjects.add(new HitObject(lane, time, 0, length, slides, curveType, points, hitSound));
                                    } else {
                                        hitObjects.add(new HitObject(lane, time, 0, hitSound));
                                    }
                                } else {
                                    hitObjects.add(new HitObject(lane, time, 0, hitSound));
                                }
                            }

                        } catch (Exception e) {}
                    }
                }
            }

            // Set Mode based on Key Count
            if (keys <= 5) model.setMode(Mode.BEAT_5K);
            else if (keys <= 7) model.setMode(Mode.BEAT_7K);
            else if (keys == 8) model.setMode(Mode.BEAT_7K);
            else if (keys == 9) model.setMode(Mode.POPN_9K);
            else model.setMode(Mode.BEAT_14K);

            model.setWavList(wavList);
            model.setBgaList(bgaList);
            convert(model, hitObjects, timingPoints, bgaEvents, sliderMultiplier, keys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    private void convert(BMSModel model, List<HitObject> hitObjects, List<TimingPoint> timingPoints, List<BGAEvent> bgaEvents, double sliderMultiplier, int keys) {
        Collections.sort(hitObjects);
        Collections.sort(timingPoints);
        Collections.sort(bgaEvents);

        double initialBpm = 120;
        for (TimingPoint tp : timingPoints) {
            if (tp.uninherited) {
                initialBpm = 60000.0 / tp.beatLength;
                model.setBpm(initialBpm);
                break;
            }
        }

        // We need to construct TimeLines.
        // Since we cannot easily add to existing timelines without recreating the array,
        // we will create a list of TimeLines, populate them, and then set them to the model.

        // Map time (ms) -> TimeLine
        Map<Long, TimeLine> timelineMap = new HashMap<>();

        // Add BGM
        TimeLine tl0 = new TimeLine(0, 0, 7296);
        tl0.setBPM(initialBpm);
        tl0.setScroll(1.0);
        timelineMap.put(0L, tl0);
        Note bgmNote = new NormalNote(1); // WAV 1
        tl0.addBackGroundNote(bgmNote);

        // Add timeline entries for all timing point changes (BPM and SV changes)
        double currentBpm = initialBpm;
        double currentSv = 1.0;
        for (TimingPoint tp : timingPoints) {
            long timeMs = (long) tp.time;
            if (tp.uninherited) {
                currentBpm = 60000.0 / tp.beatLength;
                currentSv = 1.0; // Reset SV on uninherited points
            } else {
                // Inherited (Green Line) - SV multiplier
                if (tp.beatLength < 0) {
                    currentSv = 100.0 / -tp.beatLength;
                } else {
                    currentSv = 1.0;
                }
            }
            
            TimeLine tl = timelineMap.get(timeMs);
            if (tl == null) {
                tl = new TimeLine(timeMs, timeMs * 1000, 7296);
                timelineMap.put(timeMs, tl);
            }
            tl.setBPM(currentBpm);
            tl.setScroll(currentSv);
        }

        // Add BGA Events
        for (BGAEvent be : bgaEvents) {
            TimeLine tl = timelineMap.get(be.time);
            if (tl == null) {
                tl = new TimeLine(be.time, be.time * 1000, 7296);
                timelineMap.put(be.time, tl);
            }
            tl.setBGA(be.index);
        }

        int timingIndex = 0;
        double currentBeatLength = 500; // Default 120 BPM
        currentBpm = initialBpm;
        currentSv = 1.0;

        for (HitObject ho : hitObjects) {
            // Update Timing
            while (timingIndex < timingPoints.size() && timingPoints.get(timingIndex).time <= ho.time) {
                TimingPoint tp = timingPoints.get(timingIndex);
                if (tp.uninherited) {
                    currentBeatLength = tp.beatLength;
                    currentBpm = 60000.0 / tp.beatLength;
                    currentSv = 1.0;
                } else {
                    // Inherited (Green Line)
                    // beatLength is negative inverse SV percentage
                    // -100 = 1.0x, -50 = 2.0x, -200 = 0.5x
                    if (tp.beatLength < 0) {
                        currentSv = 100.0 / -tp.beatLength;
                    } else {
                        currentSv = 1.0;
                    }
                }
                timingIndex++;
            }

            Note note;
            long endTime = ho.endTime;
            
            // Calculate Slider Duration if it's a slider (length > 0 and endTime == 0 initially)
            if (ho.length > 0 && ho.endTime == 0) {
                double duration = ho.length / (sliderMultiplier * 100.0 * currentSv) * currentBeatLength * ho.slides;
                endTime = ho.time + (long) duration;
            }

            if (ho.points != null && !ho.points.isEmpty()) {
                processSlider(ho, currentBeatLength, currentSv, sliderMultiplier, keys, timelineMap, lntype);
                continue;
            }

            int wavIndex = hitSoundToWav(ho.hitSound);
            if (endTime > 0) {
                note = new LongNote(wavIndex);
                note.setMicroDuration((endTime - ho.time) * 1000);
                ((LongNote)note).setType(lntype);
            } else {
                note = new NormalNote(wavIndex);
            }
            note.setMicroTime(ho.time * 1000);

            // Get or create timeline
            long timeMs = ho.time;
            TimeLine tl = timelineMap.get(timeMs);
            if (tl == null) {
                tl = new TimeLine(timeMs, (long)(timeMs * 1000), 7296);
                timelineMap.put(timeMs, tl);
            }
            // Ensure BPM and scroll are set for this timeline
            tl.setBPM(currentBpm);
            tl.setScroll(currentSv);

            // Set note to lane
            if (ho.lane < 8) {
                tl.setNote(ho.lane, note);
            }
        }

        // Convert map to array and sort
        List<TimeLine> sortedTimelines = new ArrayList<>(timelineMap.values());
        Collections.sort(sortedTimelines, (t1, t2) -> Long.compare(t1.getTime(), t2.getTime()));

        model.setAllTimeLine(sortedTimelines.toArray(new TimeLine[0]));
    }

    private List<Vector2> calculatePath(HitObject ho) {
        List<Vector2> path = new ArrayList<>();
        if (ho.points == null || ho.points.isEmpty()) return path;

        switch (ho.curveType) {
            case 'P': // Perfect Circle
                if (ho.points.size() == 3) {
                    // Fallback to Bezier for now as Circle math is complex to inline
                    // and Bezier approximates it reasonably well for gameplay purposes
                    path.addAll(calculateBezier(ho.points));
                } else {
                    path.addAll(calculateBezier(ho.points));
                }
                break;
            case 'B': // Bezier
                path.addAll(calculateBezier(ho.points));
                break;
            case 'C': // Catmull
                // Catmull not supported yet, fallback to Linear
                path.addAll(ho.points);
                break;
            case 'L': // Linear
            default:
                path.addAll(ho.points);
                break;
        }
        return path;
    }

    private List<Vector2> calculateBezier(List<Vector2> points) {
        List<Vector2> path = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < points.size(); i++) {
            if (i < points.size() - 1 && points.get(i).equals(points.get(i + 1))) {
                List<Vector2> subPoints = points.subList(start, i + 1);
                path.addAll(approximateBezier(subPoints));
                start = i + 1;
            }
        }
        List<Vector2> subPoints = points.subList(start, points.size());
        path.addAll(approximateBezier(subPoints));
        return path;
    }

    private List<Vector2> approximateBezier(List<Vector2> points) {
        if (points.size() < 2) return points;
        // LibGDX Bezier
        Bezier<Vector2> bezier = new Bezier<>(points.toArray(new Vector2[0]));
        List<Vector2> path = new ArrayList<>();
        int segments = 50; 
        for (int i = 0; i <= segments; i++) {
            Vector2 out = new Vector2();
            bezier.valueAt(out, (float)i / segments);
            path.add(out);
        }
        return path;
    }

    private void processSlider(HitObject ho, double currentBeatLength, double currentSv, double sliderMultiplier, int keys, Map<Long, TimeLine> timelineMap, int lntype) {
        List<Vector2> path = calculatePath(ho);
        if (path.isEmpty()) return;

        float[] lengths = new float[path.size()];
        float totalLength = 0;
        for (int i = 1; i < path.size(); i++) {
            totalLength += path.get(i).dst(path.get(i-1));
            lengths[i] = totalLength;
        }
        
        if (totalLength == 0) {
             addNote(ho.lane, ho.time, ho.time + 100, timelineMap, lntype); // Fallback
             return;
        }

        double duration = ho.length / (sliderMultiplier * 100.0 * currentSv) * currentBeatLength; 
        long startTime = ho.time;
        
        for (int slide = 0; slide < ho.slides; slide++) {
            boolean reverse = (slide % 2 == 1);
            long slideStart = startTime + (long)(slide * duration);
            long slideEnd = startTime + (long)((slide + 1) * duration);
            
            int steps = (int)(duration / 20); // 20ms resolution
            if (steps < 1) steps = 1;
            
            int currentLane = -1;
            long currentNoteStart = -1;
            
            for (int i = 0; i <= steps; i++) {
                double t = (double)i / steps;
                if (reverse) t = 1.0 - t;
                
                double d = t * ho.length;
                if (d > totalLength) d = totalLength; 
                
                Vector2 pos = getPointAtDistance(path, lengths, (float)d);
                
                int column = (int)(pos.x * keys / 512.0);
                column = Math.max(0, Math.min(keys - 1, column));
                
                int lane;
                if (keys == 8) {
                    if (column == 0) lane = 0;
                    else lane = column;
                } else {
                    lane = column + 1;
                }
                
                long time = slideStart + (long)((double)i / steps * duration);
                
                if (lane != currentLane) {
                    if (currentLane != -1) {
                        addNote(currentLane, currentNoteStart, time, timelineMap, lntype);
                    }
                    currentLane = lane;
                    currentNoteStart = time;
                }
            }
            if (currentLane != -1) {
                addNote(currentLane, currentNoteStart, slideEnd, timelineMap, lntype);
            }
        }
    }

    private void addNote(int lane, long start, long end, Map<Long, TimeLine> timelineMap, int lntype) {
        if (end <= start) return;
        Note note = new LongNote(0);
        note.setMicroDuration((end - start) * 1000);
        ((LongNote)note).setType(lntype);
        note.setMicroTime(start * 1000);
        
        TimeLine tl = timelineMap.get(start);
        if (tl == null) {
            tl = new TimeLine(start, start * 1000, 7296);
            timelineMap.put(start, tl);
        }
        if (lane < 8) {
            tl.setNote(lane, note);
        }
    }

    private Vector2 getPointAtDistance(List<Vector2> path, float[] lengths, float distance) {
        int index = Arrays.binarySearch(lengths, distance);
        if (index >= 0) return path.get(index);
        
        index = -(index + 1);
        if (index == 0) return path.get(0);
        if (index >= lengths.length) return path.get(path.size() - 1);
        
        float len1 = lengths[index-1];
        float len2 = lengths[index];
        float t = (distance - len1) / (len2 - len1);
        
        Vector2 p1 = path.get(index-1);
        Vector2 p2 = path.get(index);
        
        return new Vector2(p1).lerp(p2, t);
    }

    public static final int WAV_NORMAL = 2;
    public static final int WAV_WHISTLE = 3;
    public static final int WAV_FINISH = 4;
    public static final int WAV_CLAP = 5;

    public static int hitSoundToWav(int hitSound) {
        if ((hitSound & 8) != 0) return WAV_CLAP;
        if ((hitSound & 4) != 0) return WAV_FINISH;
        if ((hitSound & 2) != 0) return WAV_WHISTLE;
        return WAV_NORMAL;
    }

    private static class HitObject implements Comparable<HitObject> {
        int lane;
        long time;
        long endTime;
        double length;
        int slides;
        char curveType;
        List<Vector2> points;
        int hitSound;

        public HitObject(int lane, long time, long endTime) {
            this(lane, time, endTime, 0, 0, 'L', null, 0);
        }

        public HitObject(int lane, long time, long endTime, int hitSound) {
            this(lane, time, endTime, 0, 0, 'L', null, hitSound);
        }

        public HitObject(int lane, long time, long endTime, double length, int slides, char curveType, List<Vector2> points) {
            this(lane, time, endTime, length, slides, curveType, points, 0);
        }

        public HitObject(int lane, long time, long endTime, double length, int slides, char curveType, List<Vector2> points, int hitSound) {
            this.lane = lane;
            this.time = time;
            this.endTime = endTime;
            this.length = length;
            this.slides = slides;
            this.curveType = curveType;
            this.points = points;
            this.hitSound = hitSound;
        }

        @Override
        public int compareTo(HitObject o) {
            return Long.compare(this.time, o.time);
        }
    }

    private static class TimingPoint implements Comparable<TimingPoint> {
        double time;
        double beatLength;
        boolean uninherited;

        public TimingPoint(double time, double beatLength, boolean uninherited) {
            this.time = time;
            this.beatLength = beatLength;
            this.uninherited = uninherited;
        }

        @Override
        public int compareTo(TimingPoint o) {
            int c = Double.compare(this.time, o.time);
            if (c != 0) return c;
            // If times are equal, uninherited (Red) comes first to reset SV?
            // Actually in Osu, if they are same time, usually Red comes first.
            return Boolean.compare(o.uninherited, this.uninherited);
        }
    }

    private static class BGAEvent implements Comparable<BGAEvent> {
        long time;
        int index;

        public BGAEvent(long time, int index) {
            this.time = time;
            this.index = index;
        }

        @Override
        public int compareTo(BGAEvent o) {
            return Long.compare(this.time, o.time);
        }
    }
}
