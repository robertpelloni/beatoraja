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
        BMSModel model = new BMSModel();
        model.setLnmode(lntype);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            String section = "";
            List<HitObject> hitObjects = new ArrayList<>();
            List<TimingPoint> timingPoints = new ArrayList<>();

            String[] wavList = new String[1295];
            int keys = 4; // Default to 4K if undefined
            double sliderMultiplier = 1.4; // Default

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
                    if (parts.length >= 3) {
                        try {
                            int x = Integer.parseInt(parts[0]);
                            // int y = Integer.parseInt(parts[1]); // Unused
                            long time = Long.parseLong(parts[2]);
                            int type = Integer.parseInt(parts[3]);

                            // Spinner (Type 8) -> Scratch LN (Lane 0)
                            if ((type & 8) > 0) {
                                long endTime = Long.parseLong(parts[5]); // Spinners have end time in 6th field
                                hitObjects.add(new HitObject(0, time, endTime)); // Lane 0 = Scratch
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
                                    hitObjects.add(new HitObject(lane, time, endTime));
                                } else if ((type & 2) > 0) { // Slider
                                    // x,y,time,type,hitSound,curveType|curvePoints,slides,length
                                    if (parts.length >= 8) {
                                        int slides = Integer.parseInt(parts[6]);
                                        double length = Double.parseDouble(parts[7]);
                                        // Duration will be calculated in convert()
                                        hitObjects.add(new HitObject(lane, time, 0, length, slides));
                                    } else {
                                        hitObjects.add(new HitObject(lane, time, 0));
                                    }
                                } else {
                                    hitObjects.add(new HitObject(lane, time, 0));
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
            convert(model, hitObjects, timingPoints, sliderMultiplier);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    private void convert(BMSModel model, List<HitObject> hitObjects, List<TimingPoint> timingPoints, double sliderMultiplier) {
        Collections.sort(hitObjects);
        Collections.sort(timingPoints);

        double currentBpm = 120;
        for (TimingPoint tp : timingPoints) {
            if (tp.uninherited) {
                currentBpm = 60000.0 / tp.beatLength;
                model.setBpm(currentBpm);
                break;
            }
        }

        // We need to construct TimeLines.
        // Since we cannot easily add to existing timelines without recreating the array,
        // we will create a list of TimeLines, populate them, and then set them to the model.

        // Map time (ms) -> TimeLine
        Map<Long, TimeLine> timelineMap = new HashMap<>();

        int timingIndex = 0;
        double currentBeatLength = 500; // Default 120 BPM
        double currentSv = 1.0;

        for (HitObject ho : hitObjects) {
            // Update Timing
            while (timingIndex < timingPoints.size() && timingPoints.get(timingIndex).time <= ho.time) {
                TimingPoint tp = timingPoints.get(timingIndex);
                if (tp.uninherited) {
                    currentBeatLength = tp.beatLength;
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
            // We need to find the *latest* active timing point, but the loop above might overshoot if we just iterate.
            // Actually, we should re-scan or maintain state properly.
            // Since hitObjects are sorted, we can just advance timingIndex.
            // BUT, we need to handle the case where multiple timing points are before the object.
            // The loop `while (timingIndex < ... && time <= ho.time)` does exactly that.
            // It processes ALL timing points up to the object's time, so `currentBeatLength` and `currentSv` are correct for `ho.time`.

            Note note;
            long endTime = ho.endTime;
            
            // Calculate Slider Duration if it's a slider (length > 0 and endTime == 0 initially)
            if (ho.length > 0 && ho.endTime == 0) {
                double duration = ho.length / (sliderMultiplier * 100.0 * currentSv) * currentBeatLength * ho.slides;
                endTime = ho.time + (long) duration;
            }

            if (endTime > 0) {
                note = new LongNote(1); // Use WAV 01
                note.setMicroDuration((endTime - ho.time) * 1000);
                ((LongNote)note).setType(lntype);
            } else {
                note = new NormalNote(1); // Use WAV 01
            }
            note.setMicroTime(ho.time * 1000);

            // Get or create timeline
            long timeMs = ho.time;
            TimeLine tl = timelineMap.get(timeMs);
            if (tl == null) {
                tl = new TimeLine(timeMs, (long)(timeMs * 1000), 7296); // 7296 = 384 * 19 (approx measure resolution) - arbitrary for now
                timelineMap.put(timeMs, tl);
            }

            // Set note to lane
            // Mode 7K: Key 1-7. Lane index 1-7.
            // Osu lane 1-7 -> BMS Lane 1-7.
            // Note: BMS 7K Mode usually has 8 lanes (0=Scratch, 1-7=Keys).
            if (ho.lane < 8) {
                tl.setNote(ho.lane, note);
            }
        }

        // Convert map to array and sort
        List<TimeLine> sortedTimelines = new ArrayList<>(timelineMap.values());
        Collections.sort(sortedTimelines, (t1, t2) -> Long.compare(t1.getTime(), t2.getTime()));

        model.setAllTimeLine(sortedTimelines.toArray(new TimeLine[0]));
    }

    private static class HitObject implements Comparable<HitObject> {
        int lane;
        long time;
        long endTime;
        double length;
        int slides;

        public HitObject(int lane, long time, long endTime) {
            this(lane, time, endTime, 0, 0);
        }

        public HitObject(int lane, long time, long endTime, double length, int slides) {
            this.lane = lane;
            this.time = time;
            this.endTime = endTime;
            this.length = length;
            this.slides = slides;
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
}
