package bms.player.beatoraja.play.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import bms.player.beatoraja.Config;
import bms.player.beatoraja.PlayConfig;
import bms.player.beatoraja.play.BMSPlayer;
import bms.player.beatoraja.play.GrooveGauge;
import bms.player.beatoraja.pattern.Random;
import bms.player.beatoraja.arena.ArenaManager;
import bms.player.beatoraja.arena.ArenaData;
import bms.player.beatoraja.mission.MissionManager;
import bms.player.beatoraja.mission.MissionData;

public class ModMenu {

    private Stage stage;
    private Skin skin;
    private boolean visible = false;
    private BMSPlayer player;
    private InputProcessor originalInputProcessor;

    private Slider hispeedSlider;
    private Label hispeedLabel;

    private Slider laneCoverSlider;
    private Label laneCoverLabel;
    private TextButton laneCoverToggle;

    private Slider liftSlider;
    private Label liftLabel;
    private TextButton liftToggle;

    private Slider hiddenSlider;
    private Label hiddenLabel;
    private TextButton hiddenToggle;

    private Slider judgeTimingSlider;
    private Label judgeTimingLabel;

    private Slider playSpeedSlider;
    private Label playSpeedLabel;

    private TextButton pacemakerButton;
    private TextButton nonstopButton;
    private TextButton timerButton;
    private TextButton chartPreviewButton;
    private TextButton bgaButton;
    private TextButton fixGnButton;
    private TextButton gaugeButton;
    private TextButton randomButton;
    private TextButton random2Button;
    private TextButton autoAdjustButton;

    // Arena UI
    private Window arenaWindow;
    private TextField hostField;
    private TextField portField;
    private TextField nameField;
    private Label arenaStatusLabel;
    private Label arenaPlayersLabel;

    // Missions UI
    private Window missionsWindow;
    private Label missionsListLabel;

    public ModMenu(BMSPlayer player) {
        this.player = player;
        create();
    }

    private void create() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin();

        // Create simple resources
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Font
        BitmapFont font;
        if (Gdx.files.internal("font/VL-Gothic-Regular.ttf").exists()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/VL-Gothic-Regular.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 16;
            font = generator.generateFont(parameter);
            generator.dispose();
        } else {
            // Fallback to default if not found
            font = new BitmapFont();
        }
        skin.add("default-font", font);

        // Styles
        Window.WindowStyle ws = new Window.WindowStyle();
        ws.titleFont = skin.getFont("default-font");
        ws.background = skin.newDrawable("white", new Color(0, 0, 0, 0.9f));
        skin.add("default", ws);

        Label.LabelStyle ls = new Label.LabelStyle();
        ls.font = skin.getFont("default-font");
        ls.fontColor = Color.WHITE;
        skin.add("default", ls);

        Slider.SliderStyle ss = new Slider.SliderStyle();
        ss.background = skin.newDrawable("white", Color.GRAY);
        ss.knob = skin.newDrawable("white", Color.WHITE);
        ss.knob.setMinWidth(10);
        ss.knob.setMinHeight(20);
        skin.add("default-horizontal", ss);

        // TextField Style
        TextField.TextFieldStyle tfs = new TextField.TextFieldStyle();
        tfs.font = skin.getFont("default-font");
        tfs.fontColor = Color.WHITE;
        tfs.background = skin.newDrawable("white", Color.DARK_GRAY);
        tfs.selection = skin.newDrawable("white", Color.BLUE);
        tfs.cursor = skin.newDrawable("white", Color.WHITE);
        skin.add("default", tfs);

        // TextButton Style
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = skin.getFont("default-font");
        tbs.fontColor = Color.WHITE;
        tbs.up = skin.newDrawable("white", Color.GRAY);
        tbs.down = skin.newDrawable("white", Color.DARK_GRAY);
        tbs.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        skin.add("default", tbs);

        // UI Layout
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Window window = new Window("Mod Menu", skin);
        window.getTitleLabel().setAlignment(1); // Center

        // Hi-Speed
        hispeedLabel = new Label("Hi-Speed: " + String.format("%.2f", 0.0f), skin);
        hispeedSlider = new Slider(0.5f, 10.0f, 0.01f, false, skin);
        hispeedSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setHiSpeed(hispeedSlider.getValue());
                // Label updated in update()
            }
        });

        // Lane Cover
        laneCoverLabel = new Label("Lane Cover: 0", skin);
        laneCoverSlider = new Slider(0, 1000, 1, false, skin);
        laneCoverSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setLaneCover(laneCoverSlider.getValue() / 1000f);
                laneCoverLabel.setText("Lane Cover: " + (int)laneCoverSlider.getValue());
            }
        });

        laneCoverToggle = new TextButton("On", skin);
        laneCoverToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.getLanerender() != null) {
                    boolean b = !player.getLanerender().isEnableLanecover();
                    player.getLanerender().setEnableLanecover(b);
                    updateLaneCoverToggle();
                }
            }
        });

        // Lift
        liftLabel = new Label("Lift: 0", skin);
        liftSlider = new Slider(0, 1000, 1, false, skin);
        liftSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setLift(liftSlider.getValue() / 1000f);
                liftLabel.setText("Lift: " + (int)liftSlider.getValue());
            }
        });

        liftToggle = new TextButton("On", skin);
        liftToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.getLanerender() != null) {
                    boolean b = !player.getLanerender().getPlayConfig().isEnablelift();
                    player.getLanerender().getPlayConfig().setEnablelift(b);
                    updateLiftToggle();
                }
            }
        });

        // Hidden
        hiddenLabel = new Label("Hidden: 0", skin);
        hiddenSlider = new Slider(0, 1000, 1, false, skin);
        hiddenSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setHidden(hiddenSlider.getValue() / 1000f);
                hiddenLabel.setText("Hidden: " + (int)hiddenSlider.getValue());
            }
        });

        hiddenToggle = new TextButton("On", skin);
        hiddenToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.getLanerender() != null) {
                    boolean b = !player.getLanerender().getPlayConfig().isEnablehidden();
                    player.getLanerender().getPlayConfig().setEnablehidden(b);
                    updateHiddenToggle();
                }
            }
        });

        // Judge Timing
        judgeTimingLabel = new Label("Judge Timing: 0ms", skin);
        judgeTimingSlider = new Slider(-500, 500, 1, false, skin);
        judgeTimingSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setJudgeTiming((int)judgeTimingSlider.getValue());
                judgeTimingLabel.setText("Judge Timing: " + (int)judgeTimingSlider.getValue() + "ms");
            }
        });

        // Play Speed
        playSpeedLabel = new Label("Play Speed: 1.00x", skin);
        playSpeedSlider = new Slider(0.5f, 1.5f, 0.05f, false, skin);
        playSpeedSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float val = playSpeedSlider.getValue();
                if (Math.abs(val - 1.0f) < 0.025f) val = 1.0f;
                player.setPlaybackRate(val);
                playSpeedLabel.setText(String.format("Play Speed: %.2fx", val));
            }
        });

        window.add(hispeedLabel).pad(2);
        window.row();
        window.add(hispeedSlider).width(300).pad(2);
        window.row();

        Table lcTable = new Table();
        lcTable.add(laneCoverLabel).padRight(10);
        lcTable.add(laneCoverToggle).width(50).height(20);
        window.add(lcTable).pad(2);
        window.row();
        window.add(laneCoverSlider).width(300).pad(2);
        window.row();

        Table liftTable = new Table();
        liftTable.add(liftLabel).padRight(10);
        liftTable.add(liftToggle).width(50).height(20);
        window.add(liftTable).pad(2);
        window.row();
        window.add(liftSlider).width(300).pad(2);
        window.row();

        Table hiddenTable = new Table();
        hiddenTable.add(hiddenLabel).padRight(10);
        hiddenTable.add(hiddenToggle).width(50).height(20);
        window.add(hiddenTable).pad(2);
        window.row();
        window.add(hiddenSlider).width(300).pad(2);
        window.row();

        window.add(judgeTimingLabel).pad(2);
        window.row();
        window.add(judgeTimingSlider).width(300).pad(2);
        window.row();

        window.add(playSpeedLabel).pad(2);
        window.row();
        window.add(playSpeedSlider).width(300).pad(2);

        // Buttons Row 1
        Table buttons1 = new Table();
        pacemakerButton = new TextButton("Pacemaker: Rival", skin);
        pacemakerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int current = getPacemakerType();
                current = (current + 1) % 5;
                setPacemakerType(current);
                updatePacemakerButton();
                player.updateTargetScore();
            }
        });
        buttons1.add(pacemakerButton).width(145).pad(2);

        nonstopButton = new TextButton("Nonstop: Off", skin);
        nonstopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean current = player.resource.isNonstop();
                player.resource.setNonstop(!current);
                nonstopButton.setText("Nonstop: " + (!current ? "On" : "Off"));
            }
        });
        buttons1.add(nonstopButton).width(145).pad(2);
        window.row();
        window.add(buttons1);

        // Buttons Row 2
        Table buttons2 = new Table();
        timerButton = new TextButton("Timer: Off", skin);
        timerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                long current = player.resource.getSessionEndTime();
                if (current == 0) {
                    // Set 10 mins
                    player.resource.setSessionEndTime(System.currentTimeMillis() + 10 * 60 * 1000);
                    timerButton.setText("Timer: 10m");
                } else {
                    player.resource.setSessionEndTime(0);
                    timerButton.setText("Timer: Off");
                }
            }
        });
        buttons2.add(timerButton).width(145).pad(2);

        chartPreviewButton = new TextButton("Chart Preview: On", skin);
        chartPreviewButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean current = player.resource.getPlayerConfig().isChartPreview();
                player.resource.getPlayerConfig().setChartPreview(!current);
                chartPreviewButton.setText("Chart Preview: " + (!current ? "On" : "Off"));
            }
        });
        buttons2.add(chartPreviewButton).width(145).pad(2);
        window.row();
        window.add(buttons2);

        // Buttons Row 3
        Table buttons3 = new Table();
        bgaButton = new TextButton("BGA: On", skin);
        bgaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int current = player.main.getConfig().getBga();
                current = (current + 1) % 3;
                player.main.getConfig().setBga(current);
                updateBgaButton();
            }
        });
        buttons3.add(bgaButton).width(145).pad(2);

        fixGnButton = new TextButton("Fix GN: Off", skin);
        fixGnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.getLanerender() != null) {
                    int current = player.getLanerender().getPlayConfig().getFixhispeed();
                    current = (current + 1) % 5;
                    player.getLanerender().getPlayConfig().setFixhispeed(current);
                    updateFixGnButton();
                }
            }
        });
        buttons3.add(fixGnButton).width(145).pad(2);

        gaugeButton = new TextButton("Gauge: A-Easy", skin);
        gaugeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int current = player.resource.getPlayerConfig().getGauge();
                // Cycle 0,1,2,3,4,5,9
                if (current == 5) current = 9;
                else if (current == 9) current = 0;
                else current++;

                player.resource.getPlayerConfig().setGauge(current);
                updateGaugeButton();
            }
        });
        buttons3.add(gaugeButton).width(145).pad(2);

        window.row();
        window.add(buttons3);

        // Buttons Row 4
        Table buttons4 = new Table();
        randomButton = new TextButton("Random: Off", skin);
        randomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int current = player.resource.getPlayerConfig().getRandom();
                if (player.getMode().player == 2 && current == 4) current = 10; // Jump to M-RAN
                else if (current == 10) current = 0;
                else current = (current + 1) % 5;

                player.resource.getPlayerConfig().setRandom(current);
                updateRandomButton();
            }
        });
        buttons4.add(randomButton).width(145).pad(2);

        if (player.getMode().player == 2) {
            random2Button = new TextButton("R2: Off", skin);
            random2Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    int current = player.resource.getPlayerConfig().getRandom2();
                    if (current == 4) current = 10; // Jump to M-RAN
                    else if (current == 10) current = 0;
                    else current = (current + 1) % 5;

                    player.resource.getPlayerConfig().setRandom2(current);
                    updateRandom2Button();
                }
            });
            buttons4.add(random2Button).width(145).pad(2);
        }

        autoAdjustButton = new TextButton("Auto Adjust", skin);
        autoAdjustButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                double avg = player.getJudgeManager().getAverageTimingError();
                int current = getJudgeTiming();
                setJudgeTiming(current - (int)avg);
                judgeTimingSlider.setValue(getJudgeTiming());
                judgeTimingLabel.setText("Judge Timing: " + getJudgeTiming() + "ms");
            }
        });
        buttons4.add(autoAdjustButton).width(145).pad(2);
        window.row();
        window.add(buttons4);

        window.row();
        Table buttons5 = new Table();
        TextButton arenaButton = new TextButton("Arena Mode...", skin);
        arenaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (arenaWindow == null) createArenaWindow();
                arenaWindow.setVisible(!arenaWindow.isVisible());
                if (arenaWindow.isVisible()) arenaWindow.toFront();
            }
        });
        buttons5.add(arenaButton).width(145).pad(2);

        TextButton missionsButton = new TextButton("Missions...", skin);
        missionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (missionsWindow == null) createMissionsWindow();
                missionsWindow.setVisible(!missionsWindow.isVisible());
                if (missionsWindow.isVisible()) missionsWindow.toFront();
            }
        });
        buttons5.add(missionsButton).width(145).pad(2);
        window.row();
        window.add(buttons5);

        window.pack();
        // Center window
        window.setPosition((Gdx.graphics.getWidth() - window.getWidth()) / 2, (Gdx.graphics.getHeight() - window.getHeight()) / 2);

        root.add(window);
    }

    // ... Arena/Mission methods ...
    private void createArenaWindow() {
        arenaWindow = new Window("Arena Mode", skin);
        arenaWindow.getTitleLabel().setAlignment(1);

        Table content = new Table();
        arenaWindow.add(content).pad(10);

        Label nameLabel = new Label("Name:", skin);
        nameField = new TextField("Player", skin);

        Label hostLabel = new Label("Host:", skin);
        hostField = new TextField("localhost", skin);

        Label portLabel = new Label("Port:", skin);
        portField = new TextField("5073", skin);

        content.add(nameLabel);
        content.add(nameField).width(150);
        content.row();
        content.add(hostLabel);
        content.add(hostField).width(150);
        content.row();
        content.add(portLabel);
        content.add(portField).width(150);

        TextButton connectButton = new TextButton("Connect", skin);
        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    String host = hostField.getText();
                    int port = Integer.parseInt(portField.getText());
                    String name = nameField.getText();
                    player.main.getArenaManager().connect(host, port, name);
                    arenaStatusLabel.setText("Status: Connecting...");
                } catch (Exception e) {
                    arenaStatusLabel.setText("Error: " + e.getMessage());
                }
            }
        });

        TextButton serverButton = new TextButton("Start Server", skin);
        serverButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    int port = Integer.parseInt(portField.getText());
                    player.main.getArenaManager().startServer(port);
                    arenaStatusLabel.setText("Status: Server Started");
                } catch (Exception e) {
                    arenaStatusLabel.setText("Error: " + e.getMessage());
                }
            }
        });

        TextButton readyButton = new TextButton("Ready", skin);
        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.main.getArenaManager().sendReady(true);
                arenaStatusLabel.setText("Status: Ready sent");
            }
        });

        TextButton startGameButton = new TextButton("Start (Host)", skin);
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.main.getArenaManager().isHost()) {
                    player.main.getArenaManager().sendStartGame();
                    arenaStatusLabel.setText("Status: Starting...");
                } else {
                    arenaStatusLabel.setText("Error: Only Host can start.");
                }
            }
        });

        TextButton gaugeRuleButton = new TextButton("Rule: Default", skin);
        gaugeRuleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.main.getArenaManager().isHost()) {
                    int current = player.main.getArenaManager().getRuleGauge();
                    current++;
                    if (current > 5) current = -1; // -1, 0, 1, 2, 3, 4, 5

                    player.main.getArenaManager().sendRules(current);

                    String text = "Rule: ";
                    switch(current) {
                        case -1: text += "Default"; break;
                        case 0: text += "A-Easy"; break;
                        case 1: text += "Easy"; break;
                        case 2: text += "Normal"; break;
                        case 3: text += "Hard"; break;
                        case 4: text += "ExHard"; break;
                        case 5: text += "Hazard"; break;
                    }
                    gaugeRuleButton.setText(text);
                }
            }
        });

        Table buttons = new Table();
        buttons.add(connectButton).pad(5);
        buttons.add(serverButton).pad(5);
        buttons.row();
        buttons.add(readyButton).pad(5);
        buttons.add(startGameButton).pad(5);
        buttons.row();
        buttons.add(gaugeRuleButton).colspan(2).pad(5);

        content.row();
        content.add(buttons).colspan(2).pad(10);

        arenaStatusLabel = new Label("Status: Idle", skin);
        content.row();
        content.add(arenaStatusLabel).colspan(2);

        arenaPlayersLabel = new Label("Players: ", skin);
        content.row();
        content.add(arenaPlayersLabel).colspan(2).padTop(10);

        arenaWindow.pack();
        arenaWindow.setPosition((Gdx.graphics.getWidth() - arenaWindow.getWidth()) / 2 + 50, (Gdx.graphics.getHeight() - arenaWindow.getHeight()) / 2 - 50);

        stage.addActor(arenaWindow);
    }

    private void createMissionsWindow() {
        missionsWindow = new Window("Missions", skin);
        missionsWindow.getTitleLabel().setAlignment(1);

        Table content = new Table();
        missionsWindow.add(content).pad(10);

        missionsListLabel = new Label("", skin);
        content.add(missionsListLabel).width(400);

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missionsWindow.setVisible(false);
            }
        });

        content.row();
        content.add(closeButton).padTop(10);

        missionsWindow.pack();
        missionsWindow.setPosition((Gdx.graphics.getWidth() - missionsWindow.getWidth()) / 2 - 50, (Gdx.graphics.getHeight() - missionsWindow.getHeight()) / 2 - 50);
        stage.addActor(missionsWindow);
    }

    private float getHiSpeed() {
        return player.getLanerender() != null ? player.getLanerender().getHispeed() : 1.0f;
    }

    private void setHiSpeed(float val) {
        if (player.getLanerender() != null) {
            player.getLanerender().getPlayConfig().setHispeed(val);
        }
    }

    private float getLaneCover() {
        return player.getLanerender() != null ? player.getLanerender().getLanecover() : 0f;
    }

    private void setLaneCover(float val) {
        if (player.getLanerender() != null) {
            player.getLanerender().getPlayConfig().setLanecover(val);
        }
    }

    private float getLift() {
        return player.getLanerender() != null ? player.getLanerender().getLiftRegion() : 0f;
    }

    private void setLift(float val) {
        if (player.getLanerender() != null) {
            player.getLanerender().setLiftRegion(val);
        }
    }

    private float getHidden() {
        return player.getLanerender() != null ? player.getLanerender().getHiddenCover() : 0f;
    }

    private void setHidden(float val) {
        if (player.getLanerender() != null) {
            player.getLanerender().setHiddenCover(val);
        }
    }

    private int getJudgeTiming() {
        return player.resource.getPlayerConfig().getJudgetiming();
    }

    private void setJudgeTiming(int val) {
        player.resource.getPlayerConfig().setJudgetiming(val);
    }

    private int getGreenNumber() {
        return player.getLanerender() != null ? player.getLanerender().getCurrentDuration() : 0;
    }

    private int getPacemakerType() {
        if (player.getLanerender() != null) {
            return player.getLanerender().getPlayConfig().getPacemakerType();
        }
        return 0;
    }

    private void setPacemakerType(int type) {
        if (player.getLanerender() != null) {
            player.getLanerender().getPlayConfig().setPacemakerType(type);
        }
    }

    private void updatePacemakerButton() {
        int type = getPacemakerType();
        String text = "Pacemaker: ";
        switch (type) {
            case 0: text += "Rival"; break;
            case 1: text += "Best"; break;
            case 2: text += "AAA"; break;
            case 3: text += "AA"; break;
            case 4: text += "A"; break;
        }
        pacemakerButton.setText(text);
    }

    private void updateBgaButton() {
        int val = player.main.getConfig().getBga();
        String text = "BGA: ";
        switch(val) {
            case Config.BGA_ON: text += "On"; break;
            case Config.BGA_AUTO: text += "Auto"; break;
            case Config.BGA_OFF: text += "Off"; break;
        }
        bgaButton.setText(text);
    }

    private void updateFixGnButton() {
        if(player.getLanerender() != null) {
            int val = player.getLanerender().getPlayConfig().getFixhispeed();
            String text = "Fix GN: ";
            switch(val) {
                case PlayConfig.FIX_HISPEED_OFF: text += "Off"; break;
                case PlayConfig.FIX_HISPEED_STARTBPM: text += "Start"; break;
                case PlayConfig.FIX_HISPEED_MAXBPM: text += "Max"; break;
                case PlayConfig.FIX_HISPEED_MAINBPM: text += "Main"; break;
                case PlayConfig.FIX_HISPEED_MINBPM: text += "Min"; break;
            }
            fixGnButton.setText(text);
        }
    }

    private void updateRandomButton() {
        int val = player.resource.getPlayerConfig().getRandom();
        String text = "Random: ";
        switch(val) {
            case 0: text += "Off"; break;
            case 1: text += "Mirror"; break;
            case 2: text += "Random"; break;
            case 3: text += "R-Rand"; break;
            case 4: text += "S-Rand"; break;
            case 10: text += "M-Ran"; break;
            default: text += "Other"; break;
        }
        randomButton.setText(text);
    }

    private void updateRandom2Button() {
        int val = player.resource.getPlayerConfig().getRandom2();
        String text = "R2: ";
        switch(val) {
            case 0: text += "Off"; break;
            case 1: text += "Mirror"; break;
            case 2: text += "Random"; break;
            case 3: text += "R-Rand"; break;
            case 4: text += "S-Rand"; break;
            case 10: text += "M-Ran"; break;
            default: text += "Other"; break;
        }
        random2Button.setText(text);
    }

    private void updateGaugeButton() {
        int val = player.resource.getPlayerConfig().getGauge();
        String text = "Gauge: ";
        switch(val) {
            case GrooveGauge.ASSISTEASY: text += "A-Easy"; break;
            case GrooveGauge.EASY: text += "Easy"; break;
            case GrooveGauge.NORMAL: text += "Normal"; break;
            case GrooveGauge.HARD: text += "Hard"; break;
            case GrooveGauge.EXHARD: text += "ExHard"; break;
            case GrooveGauge.HAZARD: text += "Hazard"; break;
            case GrooveGauge.TIMEHELL: text += "T-Hell"; break;
            default: text += "Other"; break;
        }
        gaugeButton.setText(text);
    }

    private void updateLaneCoverToggle() {
        if(player.getLanerender() != null) {
            laneCoverToggle.setText(player.getLanerender().isEnableLanecover() ? "On" : "Off");
        }
    }

    private void updateLiftToggle() {
        if(player.getLanerender() != null) {
            liftToggle.setText(player.getLanerender().getPlayConfig().isEnablelift() ? "On" : "Off");
        }
    }

    private void updateHiddenToggle() {
        if(player.getLanerender() != null) {
            hiddenToggle.setText(player.getLanerender().getPlayConfig().isEnablehidden() ? "On" : "Off");
        }
    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            toggle();
        }

        if (visible) {
            // Update live labels
            hispeedLabel.setText(String.format("Hi-Speed: %.2f (GN: %d)", getHiSpeed(), getGreenNumber()));
            if(autoAdjustButton != null) {
                autoAdjustButton.setText(String.format("Auto Adjust (Avg: %.1fms)", player.getJudgeManager().getAverageTimingError()));
            }

            if (arenaWindow != null && arenaWindow.isVisible()) {
                updateArenaStatus();
            }
            if (missionsWindow != null && missionsWindow.isVisible()) {
                updateMissions();
            }
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }

    private void updateMissions() {
        if (player.main.getMissionManager() != null) {
            StringBuilder sb = new StringBuilder();
            for (MissionData m : player.main.getMissionManager().getMissions()) {
                sb.append(m.completed ? "[X] " : "[ ] ");
                sb.append(m.title).append(": ").append(m.description);
                if (!m.completed && m.target > 0) {
                    sb.append(" (").append(m.progress).append("/").append(m.target).append(")");
                }
                sb.append("\n");
            }
            missionsListLabel.setText(sb.toString());
        }
    }

    private void updateArenaStatus() {
        ArenaManager am = player.main.getArenaManager();
        if (am != null) {
            StringBuilder sb = new StringBuilder("Players: ");
            for (ArenaData p : am.getPlayers()) {
                sb.append(p.getPlayerName())
                  .append(p.isReady() ? " [R]" : "")
                  .append(" (").append(p.getScore()).append(") ");
            }
            arenaPlayersLabel.setText(sb.toString());
        }
    }

    private void toggle() {
        visible = !visible;
        if (visible) {
            originalInputProcessor = Gdx.input.getInputProcessor();
            InputMultiplexer multiplexer = new InputMultiplexer();
            multiplexer.addProcessor(stage);
            if (originalInputProcessor != null) {
                multiplexer.addProcessor(originalInputProcessor);
            }
            Gdx.input.setInputProcessor(multiplexer);

            // Refresh values
            hispeedSlider.setValue(getHiSpeed());

            laneCoverSlider.setValue(getLaneCover() * 1000);
            laneCoverLabel.setText("Lane Cover: " + (int)laneCoverSlider.getValue());

            liftSlider.setValue(getLift() * 1000);
            liftLabel.setText("Lift: " + (int)liftSlider.getValue());

            hiddenSlider.setValue(getHidden() * 1000);
            hiddenLabel.setText("Hidden: " + (int)hiddenSlider.getValue());

            judgeTimingSlider.setValue(getJudgeTiming());
            judgeTimingLabel.setText("Judge Timing: " + getJudgeTiming() + "ms");

            playSpeedSlider.setValue(player.getPlaybackRate());
            playSpeedLabel.setText(String.format("Play Speed: %.2fx", player.getPlaybackRate()));

            updatePacemakerButton();
            nonstopButton.setText("Nonstop: " + (player.resource.isNonstop() ? "On" : "Off"));
            long endTime = player.resource.getSessionEndTime();
            if (endTime == 0) {
                timerButton.setText("Timer: Off");
            } else {
                long remaining = (endTime - System.currentTimeMillis()) / 60000;
                timerButton.setText("Timer: " + Math.max(0, remaining) + "m");
            }
            chartPreviewButton.setText("Chart Preview: " + (player.resource.getPlayerConfig().isChartPreview() ? "On" : "Off"));
            updateBgaButton();
            updateFixGnButton();
            updateGaugeButton();
            updateRandomButton();
            if (player.getMode().player == 2) updateRandom2Button();
            updateLaneCoverToggle();
            updateLiftToggle();
            updateHiddenToggle();

        } else {
            // Return control to game
            Gdx.input.setInputProcessor(originalInputProcessor);
        }
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public boolean isVisible() {
        return visible;
    }
}
