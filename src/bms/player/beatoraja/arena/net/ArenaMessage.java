package bms.player.beatoraja.arena.net;

public class ArenaMessage {
    public static final int TYPE_LOGIN = 0;
    public static final int TYPE_SCORE_UPDATE = 1;
    public static final int TYPE_PLAYER_JOINED = 2;
    public static final int TYPE_PLAYER_LEFT = 3;
    public static final int TYPE_SONG_SELECT = 4;

    public static final int TYPE_READY = 5;
    public static final int TYPE_START_GAME = 6;
    public static final int TYPE_RULES = 7;

    public int type;
    public String playerName;
    public int score;
    public String songHash;
    public boolean isReady;
    public int ruleGauge; // -1 = default, 0..8 = specific

    public ArenaMessage() {}

    public ArenaMessage(int type, String playerName, int score) {
        this.type = type;
        this.playerName = playerName;
        this.score = score;
    }

    public ArenaMessage(int type, String playerName, String songHash) {
        this.type = type;
        this.playerName = playerName;
        this.songHash = songHash;
    }

    public ArenaMessage(int type, String playerName, boolean isReady) {
        this.type = type;
        this.playerName = playerName;
        this.isReady = isReady;
    }
}
