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

import bms.player.beatoraja.play.BMSPlayer;
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
        ws.background = skin.newDrawable("white", new Color(0, 0, 0, 0.8f));
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
                hispeedLabel.setText(String.format("Hi-Speed: %.2f", hispeedSlider.getValue()));
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

        window.add(hispeedLabel).pad(5);
        window.row();
        window.add(hispeedSlider).width(300).pad(5);
        window.row();
        window.add(laneCoverLabel).pad(5);
        window.row();
        window.add(laneCoverSlider).width(300).pad(5);

        window.row();
        TextButton arenaButton = new TextButton("Arena Mode...", skin);
        arenaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (arenaWindow == null) createArenaWindow();
                arenaWindow.setVisible(!arenaWindow.isVisible());
                if (arenaWindow.isVisible()) arenaWindow.toFront();
            }
        });
        window.add(arenaButton).pad(10);

        TextButton missionsButton = new TextButton("Missions...", skin);
        missionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (missionsWindow == null) createMissionsWindow();
                missionsWindow.setVisible(!missionsWindow.isVisible());
                if (missionsWindow.isVisible()) missionsWindow.toFront();
            }
        });
        window.add(missionsButton).pad(10);

        window.pack();
        // Center window
        window.setPosition((Gdx.graphics.getWidth() - window.getWidth()) / 2, (Gdx.graphics.getHeight() - window.getHeight()) / 2);

        root.add(window);
    }

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

        Table buttons = new Table();
        buttons.add(connectButton).pad(5);
        buttons.add(serverButton).pad(5);
        buttons.row();
        buttons.add(readyButton).pad(5);
        buttons.add(startGameButton).pad(5);

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

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            toggle();
        }

        if (visible) {
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
            hispeedLabel.setText(String.format("Hi-Speed: %.2f", hispeedSlider.getValue()));

            laneCoverSlider.setValue(getLaneCover() * 1000);
            laneCoverLabel.setText("Lane Cover: " + (int)laneCoverSlider.getValue());

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
