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

## Beatmania IIDX INFINITAS (Home Version)
*   **Platform:** PC (Windows).
*   **Key Features:**
    *   **Monthly Subscription:** Requires e-amusement subscription.
    *   **120Hz Support:** Added in later updates to match Lightning Model.
    *   **Bit Unlock System:** Songs unlocked via "Bits" earned in-game.
    *   **Championship Mode:** Online ranking events.
    *   **Missions:** Daily/Repeating missions for Bits.
    *   **Controller Support:** Official Entry Model and Premium Model controllers.
    *   **R-Random & Assist Easy:** Fully supported.

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

1.  **Arena Mode:** Online real-time battle. (Partially implemented locally).
2.  **Step-Up / Story Mode:** Progressive mode.
3.  **Advanced Practice Features:** Time Free / Hazard.
4.  **Multi-Spin Scratch (MSS):** Continuous scratching.
5.  **BGA Thumbnails:** Implemented (Visuals).
6.  **Osu! Support:** Partially implemented (Loader + Audio + 7K mapping).
7.  **Mod Menu:** In-game overlay for settings (Endless Dream feature).

## Completed Features (This Session)
*   **Fast/Slow Separation:** Implemented in ScoreData/JudgeManager and exposed to Skins.
*   **Arena Groundwork:** Manager created, 2P score separation implemented in JudgeManager.
*   **Osu! Decoder:** Basic parsing of `.osu` files to `BMSModel`.
*   **LR2 Features:** Gauge, Judge, LN, Total implemented.
*   **Notes Radar:** Implemented (Logic + Visuals).
*   **Pacemaker Graph:** Implemented (Logic + Visuals).
*   **Same-Random Retry:** Implemented (Logic + UI Event).
*   **Time Hell Gauge:** Implemented backend.
*   **BGA Thumbnails:** Implemented visuals.
