# Research: Beatmania IIDX Feature History & Competitor Comparison

## Beatmania IIDX Version History & Notable Features

### TWINKLE Era (1999-2003)
*   **1st Style (1999):** 5-key and 7-key modes. Expert Mode. Options: Hidden, Mirror, Random.
*   **2nd Style (1999):** **Hi-Speed** options. **Battle Mode**.
*   **3rd Style (2000):** **Sudden** modifier. Light7/Light14 (Normal difficulty).
*   **4th Style (2000):** **Easy Gauge**. **Free Mode**.
*   **5th Style (2001):** **Auto-Scratch**. Hi-Speed 1-3. Flashing 7s (Difficulty).
*   **6th Style (2001):** **Hard Gauge** (Optional). Letter Grades (AAA, AA, etc.). Options configurable in song select.
*   **7th Style (2002):** **Dan (Class) Mode** (Skill ranking). 5-Keys became an option (modifier).
*   **8th Style (2002):** 30% Red Zone for Dan Gauge.

### SD / e-AMUSEMENT Era (2003-2012)
*   **9th Style (2003):** **e-Amusement** support. PC-based hardware. Folders in Song Select. Beginner Mode.
*   **10th Style (2004):** **DJ Point**. **Full Combo** visual effect. Independent 1P/2P options. **Judge Display**.
*   **IIDX RED (2004):** **Hi-Speed 4**. **Random+**. **Clear Rate** display. **Pacemaker Graph**.
*   **HAPPY SKY (2005):** **Green Number** (approximate). **Hidden+ / Sudden+** (adjustable covers). Difficulty scale 1-12. **Joint Double**.
*   **DistorteD (2006):** **Hi-Speed 5**. **Flip** (Double Play option). **Kaiden** rank.
*   **GOLD (2007):** **Assist Clear**. **Regul-Speed**.
*   **DJ TROOPERS (2007):** **Weekly Ranking**. Battle mode default for 2P.
*   **EMPRESS (2008):** **Hazard Mode** (Combo break = Fail). **Green Number** (official display).
*   **SIRIUS (2009):** **Charge Notes (CN)**. **Backspin Scratch (BSS)**. **Legacy Note** option. **Fast/Slow** indicator (in-game).
*   **Resort Anthem (2010):** **PASELI** support. **Premium Free** mode (Time-based).
*   **Lincle (2011):** **Ex-Hard Gauge**. **Step Up Mode**. **Free Plus**. **Sudden+ Type B**.

### HD Era (2012-2019)
*   **tricoro (2012):** HD Resolution (720p). **Floating Hi-Speed**. **Timing Offset**. **Sync/Symmetry Random** (DP).
*   **SPADA (2013):** **V-Disc** system. **Leggendaria** charts.
*   **PENDUAL (2014):** **Expand-Judge**. **R-Random**. **Ex-Combo**.
*   **copula (2015):** **Hell Charge Note (HCN)**. **Assist Easy Gauge**. Mirror allowed in Class Mode.
*   **SINOBUZ (2016):** **Time Free / Time Hazard**. **Pacemaker Next**. **Normal Retry**.
*   **CANNON BALLERS (2017):** **Arena Mode**. **Time Hell**. Windows 7 Embedded. Camera support.
*   **Rootage (2018):** **Playlist** feature. **Normal Hi-Speed** (10 levels).

### Lightning Model Era (2019-Present)
*   **HEROIC VERSE (2019):** **Lightning Model** cabinet (120Hz monitor, Touchscreen). **Notes Radar**. **M-Ran**.
*   **BISTROVER (2020):** **Play Record**. BPL Battle Mode.
*   **CastHour (2021):** **Multi-Spin Scratch (MSS)**. **Auto Offset Adjustment**.
*   **RESIDENT (2022):** **FHD (1080p)** resolution. **Separated Fast/Slow** (Notes vs Scratch).
*   **EPOLIS (2023):** **Same-Random Retry**.
*   **Pinky Crush (2024):** **Categories** in song select. **BGA Thumbnails**.
*   **Sparkle Shower (2025):** **DJ Training**.

## Competitor Feature Comparison

### EZ2DJ (EZ2AC)
*   **Controller:** 5 Keys + 1 Turntable + 1 Pedal + 4 Effect buttons (top).
*   **Modes:**
    *   **5K Only / 7K Only:** Standard gameplay.
    *   **Space Mix:** Uses all keys, turntable, and effect buttons (14+ inputs). Very high skill ceiling layout.
    *   **Ruby Mix:** Beginner mode.
*   **Notable Features:**
    *   Pedal input is unique compared to IIDX.
    *   Effect buttons integrated into gameplay (unlike IIDX where they are for settings/effects only).
    *   "Long Note" mechanics differ slightly in strictness in older versions.

### Sound Voltex (SDVX)
*   **Controller:** 4 BT (White) buttons + 2 FX (Black) buttons + 2 Analog Knobs (Lasers).
*   **Gameplay:**
    *   **Lasers:** Analog cursors that must follow a path. Unique mechanic not found in IIDX.
    *   **Camera Rotation:** Screen rotates/tilts based on gameplay (Lasers).
*   **Features:**
    *   **Exceed Gear (120Hz):** Valkyrie Model (120Hz) similar to IIDX Lightning Model.
    *   **S-Critical:** Tighter timing window added in later versions.
    *   **Hexa Diver:** Unlock system similar to Step Up/Events.

## Missing Features in Beatoraja (Gap Analysis)

Based on the research, the following features are potentially missing or could be improved in Beatoraja:

1.  **Arena Mode:** Online real-time battle with up to 4 players. Beatoraja has IR and Rivals, but no lobby-based real-time match system.
2.  **Step-Up / Story Mode:** A progressive mode with level restrictions and unlocking mechanics. Beatoraja is mostly Free Play / Course based.
3.  **Advanced Practice Features:**
    *   **Time Free / Hazard:** Beatoraja has Practice mode, but specific "Time Free" (limitless retry within time) vs "Premium Free" logic could be refined.
    *   **DJ Training:** Specific patterns or drills (Sparkle Shower feature).
4.  **Lightning Model Specifics:**
    *   **Notes Radar:** Visual analysis of chart attributes.
    *   **Touchscreen Sub-monitor:** Not applicable to standard PC setup, but "Information Panel" improvements could mimic this.
5.  **Newer IIDX Mechanics:**
    *   **Multi-Spin Scratch (MSS):** Continuous scratching direction changes. May require BMS format extension (BMSON might support it).
    *   **M-Ran (Mono Random?):** Specific random options.
6.  **Visual Polish:**
    *   **BGA Thumbnails** in song select.
    *   **Play Record:** Native recording/streaming integration (Beatoraja has some ffmpeg support, might need update).

## LR2oraja Features to Port (Priority)

1.  **LR2 Gauge Behavior:** 60% clear for Easy (Assist Easy in Beatoraja?), Hard gauge damage/recovery rates.
2.  **LR2 Judge Windows:** Interpolation behavior for Easy/Normal/Hard/VeryHard judges.
3.  **LR2 Long Note Mechanics:** Bad on early release (strict).
4.  **TOTAL Calculation:** LR2 specific formula for charts without `#TOTAL`.

## LR2oraja Feature Analysis

The `lr2oraja` fork implements several specific behaviors to mimic Lunatic Rave 2 (LR2).

### 1. LR2 Gauges
*   **Values:**
    *   **Assist Easy:** 60% border. +1.2% (PG/GR), +0.6% (GD). -3.2% (BD), -4.8% (PR), -1.6% (MS).
    *   **Easy:** 80% border. Same gains/losses as Assist Easy.
    *   **Hard:** Death at < 2% (not 0%). Damage protection (0.6x) starts at < 32% (not 30%).
*   **Current State:** `beatoraja` has `GaugeProperty.LR2` defined in `GaugeProperty.java` with values that closely match, but need verification on exact "death" threshold and "damage protection" threshold.

### 2. LR2 Judge Timing
*   **Behavior:** Instead of linear scaling for Easy/Normal/Hard/VeryHard, LR2 interpolates between fixed tables.
*   **Practice Mode:** Slider settings in LR2 mode interpolate between these tables (e.g., 70% is a mix of Normal and Hard).
*   **Very Easy:** Same as Normal (75%).

### 3. LR2 LN Mechanics
*   **Premature Release:** Releasing an LN early results in a **BAD** (POOR in default beatoraja).
*   **Release Window:** Tolerance is equal to the **GOOD** window of a standard note.
*   **Judgement:** No split judgement (start/end).

### 4. TOTAL Calculation
*   **Formula:** If `#TOTAL` is missing: `160 + (N + clamp(N-400, 0, 200)) * 0.16`.
*   **Current State:** Need to modify the BMS parser or model initialization to apply this override.
