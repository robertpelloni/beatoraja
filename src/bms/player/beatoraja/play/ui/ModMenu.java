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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import bms.player.beatoraja.play.BMSPlayer;

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

        window.pack();
        // Center window
        window.setPosition((Gdx.graphics.getWidth() - window.getWidth()) / 2, (Gdx.graphics.getHeight() - window.getHeight()) / 2);

        root.add(window);
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
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
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
