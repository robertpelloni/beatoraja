package bms.player.beatoraja.arena;

import bms.player.beatoraja.ScoreData;

public class ArenaData {

    private final String playerName;
    private int score;
    private int rank;
    private int arenaPoints;

    public ArenaData(String playerName) {
        this.playerName = playerName;
        this.score = 0;
        this.rank = 0;
        this.arenaPoints = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getArenaPoints() {
        return arenaPoints;
    }

    public void setArenaPoints(int points) {
        this.arenaPoints = points;
    }
}
