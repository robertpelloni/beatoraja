package bms.player.beatoraja;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;

import bms.player.beatoraja.AudioConfig.DriverType;
import bms.player.beatoraja.MainState.MainStateType;
import bms.player.beatoraja.MessageRenderer.Message;
import bms.player.beatoraja.audio.*;
import bms.player.beatoraja.config.KeyConfiguration;
import bms.player.beatoraja.config.SkinConfiguration;
import bms.player.beatoraja.decide.MusicDecide;
import bms.player.beatoraja.external.*;
import bms.player.beatoraja.input.BMSPlayerInputProcessor;
import bms.player.beatoraja.input.KeyCommand;
import bms.player.beatoraja.ir.*;
import bms.player.beatoraja.play.BMSPlayer;
import bms.player.beatoraja.play.TargetProperty;
import bms.player.beatoraja.result.CourseResult;
import bms.player.beatoraja.result.MusicResult;
import bms.player.beatoraja.select.MusicSelector;
import bms.player.beatoraja.select.bar.TableBar;
import bms.player.beatoraja.skin.SkinLoader;
import bms.player.beatoraja.skin.SkinObject.SkinOffset;
import bms.player.beatoraja.skin.SkinProperty;
import bms.player.beatoraja.song.*;
import bms.player.beatoraja.stream.StreamController;
import bms.tool.mdprocessor.MusicDownloadProcessor;
import bms.tool.crawler.Crawler;
import bms.player.beatoraja.arena.ArenaManager;
import bms.player.beatoraja.stepup.StepUpManager;
import bms.player.beatoraja.manager.UpdateManager;
import bms.player.beatoraja.manager.ScreenshotManager;
import bms.player.beatoraja.manager.InputManager;
import bms.player.beatoraja.manager.DownloadManager;
import bms.player.beatoraja.mission.MissionManager;

/**
 * アプリケーションのルートクラス
 *
 * @author exch
 */
public class MainController {

	private static String VERSION = "beatoraja 0.8.9";

	static {
		try {
			// Try reading from VERSION.md in classpath (packaged in jar)
			java.io.InputStream is = MainController.class.getResourceAsStream("/VERSION.md");
			if (is != null) {
				java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
				String version = s.hasNext() ? s.next().trim() : "";
				if (!version.isEmpty()) {
					VERSION = "beatoraja " + version;
				}
				is.close();
			} else {
				// Fallback to reading from VERSION.md file (Dev environment root)
				java.nio.file.Path versionPath = java.nio.file.Paths.get("VERSION.md");
				if (java.nio.file.Files.exists(versionPath)) {
					String version = new String(java.nio.file.Files.readAllBytes(versionPath)).trim();
					VERSION = "beatoraja " + version;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean debug = false;
	public static final int debugTextXpos = 10;

	/**
	 * 起動時間
	 */
	private final long boottime = System.currentTimeMillis();
	private final Calendar cl = Calendar.getInstance();

	private BMSPlayer bmsplayer;
	private MusicDecide decide;
	private MusicSelector selector;
	private MusicResult result;
	private CourseResult gresult;
	private KeyConfiguration keyconfig;
	private SkinConfiguration skinconfig;

	private AudioDriver audio;

	private PlayerResource resource;

	private BitmapFont systemfont;
	private MessageRenderer messageRenderer;

	private MainState current;
	
	private TimerManager timer;

	private Config config;
	private PlayerConfig player;
	private BMSPlayerMode auto;
	private boolean songUpdated;

	private SongInformationAccessor infodb;

	private IRStatus[] ir;

	private RivalDataAccessor rivals = new RivalDataAccessor();

	private RankingDataCache ircache = new RankingDataCache();

	private SpriteBatch sprite;
	/**
	 * 1曲プレイで指定したBMSファイル
	 */
	private Path bmsfile;

	/**
	 * FPSを描画するかどうか
	 */
	private boolean showfps;
	/**
	 * プレイデータアクセサ
	 */
	private PlayDataAccessor playdata;

	private SystemSoundManager sound;

	private ArenaManager arenaManager;

	private StepUpManager stepUpManager;

	private StreamController streamController;
	
	private UpdateManager updateManager;
	
	private ScreenshotManager screenshotManager;
	
	private InputManager inputManager;
	
	private DownloadManager downloadManager;

	private MissionManager missionManager;

	public static final int offsetCount = SkinProperty.OFFSET_MAX + 1;
	private final SkinOffset[] offset = new SkinOffset[offsetCount];

	protected TextureRegion black;
	protected TextureRegion white;

	private final Array<MainStateListener> stateListener = new Array<MainStateListener>();

	public MainController(Path f, Config config, PlayerConfig player, BMSPlayerMode auto, boolean songUpdated) {
		this.auto = auto;
		this.config = config;
		this.songUpdated = songUpdated;

		for(int i = 0;i < offset.length;i++) {
			offset[i] = new SkinOffset();
		}

		if(player == null) {
			player = PlayerConfig.readPlayerConfig(config.getPlayerpath(), config.getPlayername());
		}
		this.player = player;

		this.bmsfile = f;

		try {
			Class.forName("org.sqlite.JDBC");
			if(config.isUseSongInfo()) {
				infodb = new SongInformationAccessor(config.getSonginfopath());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		playdata = new PlayDataAccessor(config);

		Array<IRStatus> irarray = new Array<IRStatus>();
		for(IRConfig irconfig : player.getIrconfig()) {
			final IRConnection ir = IRConnectionManager.getIRConnection(irconfig.getIrname());
			if(ir != null) {
				if(irconfig.getUserid().length() == 0 || irconfig.getPassword().length() == 0) {
				} else {
					IRResponse<IRPlayerData> response = ir.login(new IRAccount(irconfig.getUserid(), irconfig.getPassword(), ""));
					if(response.isSucceeded()) {
						irarray.add(new IRStatus(irconfig, ir, response.getData()));
					} else {
						Logger.getGlobal().warning("IRへのログイン失敗 : " + response.getMessage());
					}
				}
			}

		}
		ir = irarray.toArray(IRStatus.class);
		
		rivals.update(this);

		switch(config.getAudioConfig().getDriver()) {
		case PortAudio:
			try {
				audio = new PortAudioDriver(config);
			} catch(Throwable e) {
				e.printStackTrace();
				config.getAudioConfig().setDriver(DriverType.OpenAL);
			}
			break;
		}

		timer = new TimerManager();
		sound = new SystemSoundManager(this);
		
		if(config.isUseDiscordRPC()) {
			stateListener.add(new DiscordListener());
		}
	}

	public SkinOffset getOffset(int index) {
		return offset[index];
	}

	public SongDatabaseAccessor getSongDatabase() {
		return MainLoader.getScoreDatabaseAccessor();
	}

	public SongInformationAccessor getInfoDatabase() {
		return infodb;
	}

	public PlayDataAccessor getPlayDataAccessor() {
		return playdata;
	}
	
	public RivalDataAccessor getRivalDataAccessor() {
		return rivals;
	}
	
	public RankingDataCache getRankingDataCache() {
		return ircache;
	}

	public SpriteBatch getSpriteBatch() {
		return sprite;
	}

	public PlayerResource getPlayerResource() {
		return resource;
	}

	public Config getConfig() {
		return config;
	}

	public PlayerConfig getPlayerConfig() {
		return player;
	}

	public MusicSelector getSelector() {
		return selector;
	}

	public void changeState(MainStateType state) {
		MainState newState = null;
		switch (state) {
		case MUSICSELECT:
			if (this.bmsfile != null) {
				exit();
			} else {
				newState = selector;
			}
			break;
		case DECIDE:
			newState = decide;
			break;
		case PLAY:
			if (bmsplayer != null) {
				bmsplayer.dispose();
			}
			bmsplayer = new BMSPlayer(this, resource);
			newState = bmsplayer;
			break;
		case RESULT:
			newState = result;
			break;
		case COURSERESULT:
			newState = gresult;
			break;
		case CONFIG:
			newState = keyconfig;
			break;
		case SKINCONFIG:
			newState = skinconfig;
			break;
		}

		if (newState != null && current != newState) {
			if(current != null) {
				current.shutdown();
				current.setSkin(null);
			}
			newState.create();
			if(newState.getSkin() != null) {
				newState.getSkin().prepare(newState);
			}
			current = newState;
			timer.setMainState(newState);
			current.prepare();
			updateMainStateListener(0);
		}
		if (current.getStage() != null) {
			Gdx.input.setInputProcessor(new InputMultiplexer(current.getStage(), inputManager.getInputProcessor().getKeyBoardInputProcesseor()));
		} else {
			Gdx.input.setInputProcessor(inputManager.getInputProcessor().getKeyBoardInputProcesseor());
		}
	}

	public MainState getCurrentState() {
		return current;
	}

	public void setPlayMode(BMSPlayerMode auto) {
		this.auto = auto;

	}

	public void create() {
		final long t = System.currentTimeMillis();
		sprite = new SpriteBatch();
		SkinLoader.initPixmapResourcePool(config.getSkinPixmapGen());

		try {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(config.getSystemfontpath()));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 24;
			systemfont = generator.generateFont(parameter);
			generator.dispose();
		} catch (GdxRuntimeException e) {
			Logger.getGlobal().severe("System Font読み込み失敗");
		}
		messageRenderer = new MessageRenderer(config.getMessagefontpath());

		inputManager = new InputManager(this);
		switch(config.getAudioConfig().getDriver()) {
		case OpenAL:
			audio = new GdxSoundDriver(config);
			break;
//		case AudioDevice:
//			audio = new GdxAudioDeviceDriver(config);
//			break;
		}

		resource = new PlayerResource(audio, config, player);
		selector = new MusicSelector(this, songUpdated);
		if(player.getRequestEnable()) {
		    streamController = new StreamController(selector, (player.getRequestNotify() ? messageRenderer : null));
	        streamController.run();
		}
		decide = new MusicDecide(this);
		result = new MusicResult(this);
		gresult = new CourseResult(this);
		keyconfig = new KeyConfiguration(this);
		skinconfig = new SkinConfiguration(this, player);
		if (bmsfile != null) {
			if(resource.setBMSFile(bmsfile, auto)) {
				changeState(MainStateType.PLAY);
			} else {
				// ダミーステートに移行してすぐexitする
				changeState(MainStateType.CONFIG);
				exit();
			}
		} else {
			changeState(MainStateType.MUSICSELECT);
		}

		Logger.getGlobal().info("初期化時間(ms) : " + (System.currentTimeMillis() - t));

		Array<String> targetlist = new Array<String>(player.getTargetlist());
		for(int i = 0;i < rivals.getRivalCount();i++) {
			targetlist.add("RIVAL_" + (i + 1));
		}
		TargetProperty.setTargets(targetlist.toArray(String.class), this);

		Pixmap plainPixmap = new Pixmap(2,1, Pixmap.Format.RGBA8888);
		plainPixmap.drawPixel(0,0, Color.toIntBits(255,0,0,0));
		plainPixmap.drawPixel(1,0, Color.toIntBits(255,255,255,255));
		Texture plainTexture = new Texture(plainPixmap);
		black = new TextureRegion(plainTexture,0,0,1,1);
		white = new TextureRegion(plainTexture,1,0,1,1);
		plainPixmap.dispose();

		Gdx.gl.glClearColor(0, 0, 0, 1);

		downloadManager = new DownloadManager(this);

		arenaManager = new ArenaManager();
		arenaManager.addPlayer("1P");

		stepUpManager = new StepUpManager(this);
		
		missionManager = new MissionManager(this);

		updateManager = new UpdateManager(this);
		
		screenshotManager = new ScreenshotManager(this);

		if(ir.length > 0) {
			messageRenderer.addMessage(ir.length + " IR Connection Succeed" ,5000, Color.GREEN, 1);
		}
	}

	private long prevtime;

	private final StringBuilder message = new StringBuilder();

	public void render() {
//		input.poll();
		timer.update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		current.render();
		sprite.begin();
		if (current.getSkin() != null) {
			current.getSkin().updateCustomObjects(current);
			current.getSkin().drawAllObjects(sprite, current);
		}
		sprite.end();

		final Stage stage = current.getStage();
		if (stage != null) {
			stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
			stage.draw();
		}

		// show fps
		if (showfps && systemfont != null) {
			sprite.begin();
			systemfont.setColor(Color.CYAN);
			message.setLength(0);
			systemfont.draw(sprite, message.append("FPS ").append(Gdx.graphics.getFramesPerSecond()), debugTextXpos,
					config.getResolution().height - 2);
					if(debug) {
				message.setLength(0);
				systemfont.draw(sprite, message.append("Skin Pixmap Images ").append(SkinLoader.getResource().size()), debugTextXpos,
						config.getResolution().height - 26);
				message.setLength(0);
				systemfont.draw(sprite, message.append("Total Memory Used(MB) ").append(Runtime.getRuntime().totalMemory() / (1024 * 1024)), debugTextXpos,
						config.getResolution().height - 50);
				message.setLength(0);
				systemfont.draw(sprite, message.append("Total Free Memory(MB) ").append(Runtime.getRuntime().freeMemory() / (1024 * 1024)), debugTextXpos,
						config.getResolution().height - 74);
				message.setLength(0);
				systemfont.draw(sprite, message.append("Max Sprite In Batch ").append(sprite.maxSpritesInBatch), debugTextXpos,
						config.getResolution().height - 98);
				message.setLength(0);
				systemfont.draw(sprite, message.append("Skin Pixmap Resource Size ").append(SkinLoader.getResource().size()), debugTextXpos,
						config.getResolution().height - 122);
				message.setLength(0);
				systemfont.draw(sprite, message.append("Stagefile Pixmap Resource Size ").append(selector.getStagefileResource().size()), debugTextXpos,
						config.getResolution().height - 146);
				message.setLength(0);
				systemfont.draw(sprite, message.append("Banner Pixmap Resource Size ").append(selector.getBannerResource().size()), debugTextXpos,
						config.getResolution().height - 170);
						if (current.getSkin() != null) {
					message.setLength(0);
					systemfont.draw(sprite, message.append("Skin Prepare Time ").append(current.getSkin().pcntPrepare), debugTextXpos,
							config.getResolution().height - 194);
					message.setLength(0);
					systemfont.draw(sprite, message.append("Skin Draw Time ").append(current.getSkin().pcntDraw), debugTextXpos,
							config.getResolution().height - 218);
					var i = 0;
					var l = current.getSkin().pcntmap.keySet().stream().mapToInt(c->c.getSimpleName().length()).max().orElse(1);
					var f = "%" + l + "s";
					message.setLength(0);
					message.append(String.format(f,"SkinObject")).append(" num // prepare cur/avg/max // draw cur/avg/max");
					systemfont.draw(sprite, message, debugTextXpos, config.getResolution().height - 242);
					var entrys = current.getSkin().pcntmap.entrySet().stream()
						.sorted((e1,e2) -> e1.getKey().getSimpleName().compareTo(e2.getKey().getSimpleName()))
						.toList();
					for (Map.Entry<Class, long[]> e : entrys) {
						message.setLength(0);
						message.append(String.format(f,e.getKey().getSimpleName())).append(" ")
						.append(e.getValue()[0]).append(" // ")
						.append(e.getValue()[1]/100).append(" / ")
						.append(e.getValue()[2]/100000).append(" / ")
						.append(e.getValue()[3]/100).append(" // ")
						.append(e.getValue()[4]/100).append(" / ")
						.append(e.getValue()[5]/100000).append(" / ")
						.append(e.getValue()[6]/100);
						systemfont.draw(sprite, message, debugTextXpos, config.getResolution().height - (266 + i * 24));
						i++;
					}
				}
			}

			sprite.end();
		}

		// show message
		sprite.begin();
		messageRenderer.render(current, sprite, 100, config.getResolution().height - 2);
		sprite.end();

		if (downloadManager != null) {
			downloadManager.update();
		}

		final long time = System.currentTimeMillis();
		if(time > prevtime) {
		    prevtime = time;
            
			inputManager.update(time);

            // screen shot
            if (inputManager.getInputProcessor().isActivated(KeyCommand.SAVE_SCREENSHOT)) {
                screenshotManager.saveScreenshot();
            }

            if (inputManager.getInputProcessor().isActivated(KeyCommand.POST_TWITTER)) {
                screenshotManager.postTwitter();
            }

			if (updateManager != null) {
				updateManager.poll();
			}
        }
	}

	public boolean isShowFps() {
		return showfps;
	}

	public void setShowFps(boolean showfps) {
		this.showfps = showfps;
	}

	public void dispose() {
		saveConfig();

		if (bmsplayer != null) {
			bmsplayer.dispose();
		}
		if (selector != null) {
			selector.dispose();
		}
		if (streamController != null) {
		    streamController.dispose();
        }
		if (decide != null) {
			decide.dispose();
		}
		if (result != null) {
			result.dispose();
		}
		if (gresult != null) {
			gresult.dispose();
		}
		if (keyconfig != null) {
			keyconfig.dispose();
		}
		if (skinconfig != null) {
			skinconfig.dispose();
		}
		resource.dispose();
//		input.dispose();
		SkinLoader.getResource().dispose();
		ShaderManager.dispose();
		if (downloadManager != null) {
			downloadManager.dispose();
		}
		if (arenaManager != null) {
			arenaManager.dispose();
		}

		Logger.getGlobal().info("全リソース破棄完了");
	}

	public void pause() {
		current.pause();
	}

	public void resize(int width, int height) {
		current.resize(width, height);
	}

	public void resume() {
		current.resume();
	}

	public void saveConfig(){
		Config.write(config);
		PlayerConfig.write(config.getPlayerpath(), player);
		Logger.getGlobal().info("設定情報を保存");
	}

	public void exit() {
		Gdx.app.exit();
	}

	public BMSPlayerInputProcessor getInputProcessor() {
		return inputManager.getInputProcessor();
	}

	public AudioDriver getAudioProcessor() {
		return audio;
	}

	public IRStatus[] getIRStatus() {
		return ir;
	}

	public SystemSoundManager getSoundManager() {
		return sound;
	}

	public MusicDownloadProcessor getMusicDownloadProcessor(){
		return downloadManager.getMusicDownloadProcessor();
	}

	public Crawler getCrawler() {
		return downloadManager.getCrawler();
	}

	public ArenaManager getArenaManager() {
		return arenaManager;
	}

	public StepUpManager getStepUpManager() {
		return stepUpManager;
	}

	public MissionManager getMissionManager() {
		return missionManager;
	}

	public MessageRenderer getMessageRenderer() {
		return messageRenderer;
	}
	
	public void updateMainStateListener(int status) {
		for(MainStateListener listener : stateListener) {
			listener.update(current, status);
		}
	}

	public long getPlayTime() {
		return System.currentTimeMillis() - boottime;
	}

	public Calendar getCurrnetTime() {
		cl.setTimeInMillis(System.currentTimeMillis());
		return cl;
	}

	public TimerManager getTimer() {
		return timer;
	}

	public long getStartTime() {
		return timer.getStartTime();
	}

	public long getStartMicroTime() {
		return timer.getStartMicroTime();
	}

	public long getNowTime() {
		return timer.getNowTime();
	}

	public long getNowTime(int id) {
		return timer.getNowTime(id);
	}

	public long getNowMicroTime() {
		return timer.getNowMicroTime();
	}

	public long getNowMicroTime(int id) {
		return timer.getNowMicroTime(id);
	}

	public long getTimer(int id) {
		return getMicroTimer(id) / 1000;
	}

	public long getMicroTimer(int id) {
		return timer.getMicroTimer(id);
	}

	public boolean isTimerOn(int id) {
		return getMicroTimer(id) != Long.MIN_VALUE;
	}

	public void setTimerOn(int id) {
		timer.setTimerOn(id);
	}

	public void setTimerOff(int id) {
		setMicroTimer(id, Long.MIN_VALUE);
	}

	public void setMicroTimer(int id, long microtime) {
		timer.setMicroTimer(id, microtime);
	}

	public void switchTimer(int id, boolean on) {
		timer.switchTimer(id, on);
	}

	public void updateSong(String path) {
		updateManager.updateSong(path);
	}

	public void updateTable(TableBar reader) {
		updateManager.updateTable(reader);
	}

	public void downloadIpfsMessageRenderer(String message) {
		updateManager.downloadIpfsMessageRenderer(message);
	}

	public static String getVersion() {
		return VERSION;
	}

	public static class IRStatus {

		public final IRConfig config;
		public final IRConnection connection;
		public final IRPlayerData player;

		public IRStatus(IRConfig config, IRConnection connection, IRPlayerData player) {
			this.config = config;
			this.connection = connection;
			this.player = player;
		}
	}
}
