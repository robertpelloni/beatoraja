package bms.player.beatoraja.song;

import bms.model.BMSModel;
import bms.model.LongNote;
import bms.model.MineNote;
import bms.model.Note;
import bms.model.TimeLine;

/**
 * Chart analysis radar (Notes, Chord, Peak, Scratch, Soflan, Charge)
 */
public class NotesRadar {

    public final int notes;
    public final int chord;
    public final int peak;
    public final int scratch;
    public final int soflan;
    public final int charge;

    private static final int MAX_RADAR = 200;

    public NotesRadar(BMSModel model) {
        this.notes = calculateNotes(model);
        this.chord = calculateChord(model);
        this.peak = calculatePeak(model);
        this.scratch = calculateScratch(model);
        this.soflan = calculateSoflan(model);
        this.charge = calculateCharge(model);
    }

    private int normalize(double val, double max) {
        return Math.min(MAX_RADAR, (int) ((val / max) * 100)); // Normalized to roughly 100 as base, max 200
    }

    private int calculateNotes(BMSModel model) {
        // Density based on total notes and length
        double notesPerSecond = (double) model.getTotalNotes() / (model.getLastTime() / 1000.0);
        // Base expectation: 12 NPS = 100?
        return normalize(notesPerSecond, 12.0);
    }

    private int calculateChord(BMSModel model) {
        // Chord density
        int totalChords = 0;
        int chordNotes = 0;
        for (TimeLine tl : model.getAllTimeLines()) {
            int count = 0;
            for (int i = 0; i < model.getMode().key; i++) {
                if (tl.getNote(i) != null && !(tl.getNote(i) instanceof MineNote)) {
                    count++;
                }
            }
            if (count >= 2) {
                totalChords++;
                chordNotes += count;
            }
        }
        if (model.getTotalNotes() == 0) return 0;
        double ratio = (double) chordNotes / model.getTotalNotes();
        // If 50% of notes are in chords, score 100?
        return normalize(ratio, 0.5);
    }

    private int calculatePeak(BMSModel model) {
        // Peak NPS
        // Sliding window of 1 second?
        // Simple approximation: bin by 1 second
        int[] histogram = new int[(int)(model.getLastTime() / 1000) + 2];
        for (TimeLine tl : model.getAllTimeLines()) {
            int sec = (int) (tl.getTime() / 1000);
            int count = 0;
            for (int i = 0; i < model.getMode().key; i++) {
                if (tl.getNote(i) != null && !(tl.getNote(i) instanceof MineNote)) {
                    count++;
                }
            }
            histogram[sec] += count;
        }
        int maxNps = 0;
        for (int c : histogram) maxNps = Math.max(maxNps, c);

        // 25 NPS peak = 100?
        return normalize(maxNps, 25.0);
    }

    private int calculateScratch(BMSModel model) {
        int scratchCount = 0;
        int[] scratchKeys = model.getMode().scratchKey;
        if (scratchKeys.length == 0) return 0;

        for (TimeLine tl : model.getAllTimeLines()) {
            for (int key : scratchKeys) {
                if (tl.getNote(key) != null && !(tl.getNote(key) instanceof MineNote)) {
                    scratchCount++;
                }
            }
        }

        if (model.getTotalNotes() == 0) return 0;
        double ratio = (double) scratchCount / model.getTotalNotes();
        // 15% scratch = 100?
        return normalize(ratio, 0.15);
    }

    private int calculateSoflan(BMSModel model) {
        // BPM changes variance
        // Or count of BPM changes
        // Or min/max ratio
        if (model.getMinBPM() == model.getMaxBPM()) return 0;

        double ratio = model.getMaxBPM() / model.getMinBPM();
        // 2x range = 100?
        return normalize(ratio - 1.0, 1.0);
    }

    private int calculateCharge(BMSModel model) {
        int lnCount = 0;
        for (TimeLine tl : model.getAllTimeLines()) {
            for (int i = 0; i < model.getMode().key; i++) {
                if (tl.getNote(i) instanceof LongNote) {
                    lnCount++;
                }
            }
        }
        if (model.getTotalNotes() == 0) return 0;
        // Note: lnCount counts heads.
        double ratio = (double) lnCount / model.getTotalNotes();
        // 20% LN = 100?
        return normalize(ratio, 0.2);
    }

    @Override
    public String toString() {
	return String.format("NOTES:%d CHORD:%d PEAK:%d SCRATCH:%d SOFLAN:%d CHARGE:%d", notes, chord, peak, scratch, soflan, charge);
    }
}
