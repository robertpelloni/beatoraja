package bms.player.beatoraja.arena.net;

public class ArenaMessage {
    public static final int TYPE_LOGIN = 0;
    public static final int TYPE_SCORE_UPDATE = 1;
    public static final int TYPE_PLAYER_JOINED = 2;
    public static final int TYPE_PLAYER_LEFT = 3;
    public static final int TYPE_SONG_SELECT = 4;

    public int type;
    public String playerName;
    public int score;
    public String payload;

    public ArenaMessage() {}

    public ArenaMessage(int type, String playerName, int score) {
        this.type = type;
        this.playerName = playerName;
        this.score = score;
    }

    public ArenaMessage(int type, String playerName, String payload) {
        this.type = type;
        this.playerName = playerName;
        this.payload = payload;
    }
}
