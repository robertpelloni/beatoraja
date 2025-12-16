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
        model.setLnmode(lntype); // Use setLnmode as per library inspection
        model.setMode(Mode.BEAT_7K); // Default to 7K for now

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            String section = "";
            List<HitObject> hitObjects = new ArrayList<>();
            List<TimingPoint> timingPoints = new ArrayList<>();

            // WAV list map (id -> filename)
            // Osu usually has one audio file. We'll map it to WAV 01.
            String[] wavList = new String[1295]; // Standard BMS WAV limit is large (ZZ or 1295)
            // Initialize with empty strings to be safe? Or null? BMSModel usually expects array.

            double sliderMultiplier = 1.4;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) continue;

                if (line.startsWith("[")) {
                    section = line.substring(1, line.length() - 1);
                    continue;
                }

                if (section.equals("General")) {
                    if (line.startsWith("AudioFilename:")) {
                        String audio = line.split(":", 2)[1].trim();
                        // Assign to index 1 (WAV01)
                        if (audio.length() > 0) {
                            wavList[1] = audio;
                        }
                    }
                } else if (section.equals("Metadata")) {
                    if (line.startsWith("Title:")) model.setTitle(line.split(":", 2)[1].trim());
                    if (line.startsWith("Artist:")) model.setArtist(line.split(":", 2)[1].trim());
                    if (line.startsWith("Version:")) model.setSubTitle(line.split(":", 2)[1].trim());
                } else if (section.equals("Difficulty")) {
                    if (line.startsWith("SliderMultiplier:")) {
                        try {
                            sliderMultiplier = Double.parseDouble(line.split(":", 2)[1].trim());
                        } catch (Exception e) {}
                    }
                } else if (section.equals("TimingPoints")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        try {
                            double offset = Double.parseDouble(parts[0]);
                            double mpb = Double.parseDouble(parts[1]);
                            timingPoints.add(new TimingPoint(offset, mpb));
                        } catch (Exception e) {}
                    }
                } else if (section.equals("HitObjects")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        try {
                            int x = Integer.parseInt(parts[0]);
                            int y = Integer.parseInt(parts[1]);
                            long time = Long.parseLong(parts[2]);
                            int type = Integer.parseInt(parts[3]);

                            int column = (int)(x * 7.0 / 512.0);
                            column = Math.max(0, Math.min(6, column));
                            int lane = column + 1;

                            if ((type & 128) > 0) {
                                String[] subparts = parts[5].split(":");
                                long endTime = Long.parseLong(subparts[0]);
                                hitObjects.add(new HitObject(lane, time, endTime));
                            } else {
                                hitObjects.add(new HitObject(lane, time, 0));
                            }

                        } catch (Exception e) {}
                    }
                }
            }

            model.setWavList(wavList);
            convert(model, hitObjects, timingPoints);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    private void convert(BMSModel model, List<HitObject> hitObjects, List<TimingPoint> timingPoints) {
        Collections.sort(hitObjects);
        Collections.sort(timingPoints);

        double currentBpm = 120;
        for (TimingPoint tp : timingPoints) {
            if (tp.mpb > 0) {
                currentBpm = 60000.0 / tp.mpb;
                model.setBpm(currentBpm);
                break;
            }
        }

        // We need to construct TimeLines.
        // Since we cannot easily add to existing timelines without recreating the array,
        // we will create a list of TimeLines, populate them, and then set them to the model.

        // Map time (ms) -> TimeLine
        Map<Long, TimeLine> timelineMap = new HashMap<>();

        for (HitObject ho : hitObjects) {
            Note note;
            if (ho.endTime > 0) {
                note = new LongNote(1); // Use WAV 01
                note.setMicroDuration((ho.endTime - ho.time) * 1000);
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

        public HitObject(int lane, long time, long endTime) {
            this.lane = lane;
            this.time = time;
            this.endTime = endTime;
        }

        @Override
        public int compareTo(HitObject o) {
            return Long.compare(this.time, o.time);
        }
    }

    private static class TimingPoint implements Comparable<TimingPoint> {
        double time;
        double mpb;

        public TimingPoint(double time, double mpb) {
            this.time = time;
            this.mpb = mpb;
        }

        @Override
        public int compareTo(TimingPoint o) {
            return Double.compare(this.time, o.time);
        }
    }
}
