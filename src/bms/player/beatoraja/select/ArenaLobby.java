package bms.player.beatoraja.select;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import bms.player.beatoraja.arena.ArenaManager;
import bms.player.beatoraja.arena.ArenaData;

public class ArenaLobby extends Stage {

    private MusicSelector selector;
    private Skin skin;
    private Window arenaWindow;
    private TextField hostField;
    private TextField portField;
    private TextField nameField;
    private Label arenaStatusLabel;
    private Label arenaPlayersLabel;

    public ArenaLobby(MusicSelector selector) {
        super(new FitViewport(selector.main.getConfig().getResolution().width, selector.main.getConfig().getResolution().height));
        this.selector = selector;
        create();
    }

    private void create() {
        skin = new Skin();

        // Create simple resources
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Font
        BitmapFont font;
        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(selector.main.getConfig().getSystemfontpath()));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 24;
            font = generator.generateFont(parameter);
            generator.dispose();
        } catch (Exception e) {
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

        TextField.TextFieldStyle tfs = new TextField.TextFieldStyle();
        tfs.font = skin.getFont("default-font");
        tfs.fontColor = Color.WHITE;
        tfs.background = skin.newDrawable("white", Color.DARK_GRAY);
        tfs.selection = skin.newDrawable("white", Color.BLUE);
        tfs.cursor = skin.newDrawable("white", Color.WHITE);
        skin.add("default", tfs);

        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = skin.getFont("default-font");
        tbs.fontColor = Color.WHITE;
        tbs.up = skin.newDrawable("white", Color.GRAY);
        tbs.down = skin.newDrawable("white", Color.DARK_GRAY);
        tbs.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        skin.add("default", tbs);

        createArenaWindow();
    }

    private void createArenaWindow() {
        arenaWindow = new Window("Arena Lobby", skin);
        arenaWindow.getTitleLabel().setAlignment(1);
        arenaWindow.setFillParent(true);

        Table content = new Table();
        arenaWindow.add(content).expand().fill();

        Label nameLabel = new Label("Name:", skin);
        nameField = new TextField("Player", skin);

        Label hostLabel = new Label("Host:", skin);
        hostField = new TextField("localhost", skin);

        Label portLabel = new Label("Port:", skin);
        portField = new TextField("5073", skin);

        Table form = new Table();
        form.add(nameLabel).padRight(10);
        form.add(nameField).width(200).padBottom(10);
        form.row();
        form.add(hostLabel).padRight(10);
        form.add(hostField).width(200).padBottom(10);
        form.row();
        form.add(portLabel).padRight(10);
        form.add(portField).width(200).padBottom(10);

        content.add(form).top().pad(20);

        TextButton connectButton = new TextButton("Connect", skin);
        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    String host = hostField.getText();
                    int port = Integer.parseInt(portField.getText());
                    String name = nameField.getText();
                    selector.main.getArenaManager().connect(host, port, name);
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
                    selector.main.getArenaManager().startServer(port);
                    arenaStatusLabel.setText("Status: Server Started");
                } catch (Exception e) {
                    arenaStatusLabel.setText("Error: " + e.getMessage());
                }
            }
        });

        TextButton disconnectButton = new TextButton("Disconnect", skin);
        disconnectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    selector.main.getArenaManager().dispose();
                    arenaStatusLabel.setText("Status: Disconnected");
                    arenaPlayersLabel.setText("Players: ");
                } catch (Exception e) {
                    arenaStatusLabel.setText("Error: " + e.getMessage());
                }
            }
        });
        
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selector.closeArenaLobby();
            }
        });

        Table buttons = new Table();
        buttons.add(connectButton).width(150).pad(10);
        buttons.add(serverButton).width(150).pad(10);
        buttons.add(disconnectButton).width(150).pad(10);
        buttons.add(closeButton).width(150).pad(10);

        content.row();
        content.add(buttons).pad(20);

        arenaStatusLabel = new Label("Status: Idle", skin);
        content.row();
        content.add(arenaStatusLabel).pad(10);

        arenaPlayersLabel = new Label("Players: ", skin);
        content.row();
        content.add(arenaPlayersLabel).pad(10);

        addActor(arenaWindow);
    }

    private String lastStatus = "";

    @Override
    public void act(float delta) {
        super.act(delta);
        updateArenaStatus();
    }

    private void updateArenaStatus() {
        ArenaManager am = selector.main.getArenaManager();
        if (am != null) {
            StringBuilder sb = new StringBuilder("Players: ");
            for (ArenaData p : am.getPlayers()) {
                sb.append(p.getPlayerName()).append(" (").append(p.getScore()).append(") ");
            }
            String currentStatus = sb.toString();
            if (!currentStatus.equals(lastStatus)) {
                arenaPlayersLabel.setText(currentStatus);
                lastStatus = currentStatus;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
