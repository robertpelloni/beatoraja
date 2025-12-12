# Research: Rhythm Games & Feature History

## Definitive List of Rhythm Games & Classifications

### Arcade Rhythm Games

#### Konami (BEMANI)
*   **beatmania (1997):** 5-key + turntable. The original.
*   **beatmania IIDX (1999-Present):** 7-key + turntable. High difficulty ceiling.
*   **Pop'n Music (1998-Present):** 9 large buttons. Cute aesthetic, hardcore gameplay.
*   **Dance Dance Revolution (DDR) (1998-Present):** 4-panel foot stomping.
*   **GuitarFreaks & DrumMania (Gitadora) (1999-Present):** Instrument simulation.
*   **Keyboardmania (2000-2001):** 2-octave keyboard. Very difficult.
*   **jubeat (2008-Present):** 4x4 grid of panels. Whack-a-mole style timing.
*   **Reflec Beat (2010-Inactive):** Touchscreen "Air Hockey" rhythm game.
*   **Sound Voltex (SDVX) (2012-Present):** 4 buttons + 2 FX buttons + 2 Analog Lasers.
*   **MUSECA (2015-Inactive):** 5 spinner buttons + foot pedal.
*   **Nostalgia (2017-Present):** Piano simulation with falling notes.
*   **DANCERUSH STARDOM (2018-Present):** Shuffle dance / sliding feet.
*   **Dance Around (2022-Present):** Camera-based motion tracking.
*   **Polaris Chord (2024-Present):** Newest title.

#### Other Arcade
*   **EZ2DJ / EZ2AC (AmuseWorld/SquarePixels):** Closest IIDX competitor. 5K/7K/10K/14K modes. Pedal input.
*   **Pump It Up (Andamiro):** 5-panel dance game (corners + center).
*   **Chunithm (Sega):** Touch slider + air sensors (hand waving).
*   **maimai (Sega):** Washing machine style. Circular buttons + touchscreen.
*   **WACCA (Marvelous):** 360-degree circular touch interface.
*   **Groove Coaster (Taito):** Vector graphics, single button/joystick input.
*   **Taiko no Tatsujin (Bandai Namco):** Drum peripheral. Don (center) and Ka (rim).

### Mobile Rhythm Games

*   **Cytus / Cytus II (Rayark):** Scanline moves up/down. Tap notes as line passes.
*   **Deemo (Rayark):** Piano emulation without lanes.
*   **Voez (Rayark):** Dynamic lanes that move and change color.
*   **Arcaea (Lowiro):** "3D" lanes. Floor notes + Sky input (arcs).
*   **Phigros (Pigeon Games):** Judgement line rotates and moves dynamically. No fixed lanes.
*   **Lanota (Noxy Games):** Circular play area that rotates.
*   **BanG Dream! / Project Sekai (Bushiroad/Sega):** Standard VSRG (Vertical Scrolling Rhythm Game) with gacha/story elements. "Flick" notes are prominent.
*   **Rizline:** Simpler VSRG with moving lines.

### PC Rhythm Games

*   **osu!:** Mouse aiming + clicking to beat. (Modes: Standard, Taiko, Catch, Mania).
*   **StepMania:** Open source DDR simulator.
*   **BMS Players (Lunatic Rave 2, beatoraja):** IIDX simulators.
*   **DJMAX Respect V:** Polished commercial VSRG. 4K-8K modes.
*   **A Dance of Fire and Ice (7th Beat):** One-button rhythm logic game.

---

## Feature Analysis & Missing Features in Beatoraja

### Unique Features from Competitors
*   **EZ2DJ:** **Pedal Input**. Affects gameplay (sustains or bangs). **Effect Buttons** as active lanes (Space Mix).
*   **Sound Voltex:** **Lasers** (Analog knobs). Camera rotation/tilting based on gameplay intensity. **Exceed Gear** (120Hz support).
*   **Arcaea:** **Sky Notes/Arcs**. Adds verticality to standard lanes.
*   **Chunithm:** **Air Notes**. Requiring hand to be raised.

### Detailed IIDX Feature History (Gap Analysis)

#### Early Era
*   **5th Style:** **Auto-Scratch**.
*   **6th Style:** **Hard Gauge**.
*   **10th Style:** **DJ Point** (Metric for skill). **Judge Display** (In-game).

#### Expansion Era
*   **IIDX RED:** **Pacemaker Graph** (Visual target). **Clear Rate**.
*   **HAPPY SKY:** **Hidden+ / Sudden+**.
*   **EMPRESS:** **Green Number** (Fixed visual reaction time). **Hazard Mode** (Combo break = Fail).
*   **SIRIUS:** **Fast/Slow** (Combined). **Charge Notes** (CN). **Backspin Scratch** (BSS).

#### Modern Era (Tricoro - Rootage)
*   **Tricoro:** **Floating Hi-Speed**. **Timing Offset**.
*   **SPADA:** **Leggendaria** (Extra difficulty).
*   **PENDUAL:** **Expand-Judge** (Widens timing window visually). **R-Random** (Rotation).
*   **SINOBUZ:** **Time Free** (Practice mode). **Pacemaker Next**.
*   **Rootage:** **Playlist**. **Normal Hi-Speed** (10 levels).

#### Lightning Model Era (Heroic Verse - Present)
*   **HEROIC VERSE:** **Notes Radar**. **M-Ran**.
*   **BISTROVER:** **Play Record**.
*   **CastHour:** **MSS (Multi-Spin Scratch)**.
*   **RESIDENT:** **Separated Fast/Slow** (Notes vs Scratch). **FHD Resolution**.
*   **EPOLIS:** **Same-Random Retry**.
*   **Pinky Crush:** **BGA Thumbnails** in song select. **Categories**.
*   **Sparkle Shower (2025):** **DJ Training**.

---

## Priority Implementation Plan

1.  **Separated Fast/Slow (RESIDENT):**
    *   Currently, beatoraja likely tracks global Fast/Slow.
    *   Goal: Track Notes and Scratch F/S independently and display them.
2.  **Pacemaker Graph Enhancements:**
    *   Ensure Target Score graph is visible and configurable (A, AA, AAA, NEXT).
3.  **BGA Thumbnails (Pinky Crush):**
    *   Load `stagefile` BMP/JPG in song select.
4.  **Playlist / Categories:**
    *   Allow tagging favorites.
