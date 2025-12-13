package bms.player.beatoraja.arena;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArenaManager {

    private final List<ArenaData> players;

    public ArenaManager() {
        this.players = new ArrayList<>();
    }

    public void addPlayer(String name) {
        players.add(new ArenaData(name));
    }

    public void updateScore(String name, int score) {
        for (ArenaData player : players) {
            if (player.getPlayerName().equals(name)) {
                player.setScore(score);
                break;
            }
        }
        calculateRanks();
    }

    public void calculateRanks() {
        if (players.isEmpty()) return;

        // Sort by score descending
        players.sort(Comparator.comparingInt(ArenaData::getScore).reversed());

        // Assign ranks (1-based)
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setRank(i + 1);

            // Assign points based on rank (IIDX style: 1st=2, 2nd=1, 3rd=0, 4th=-1 ? Or just 2/1/0/0)
            // Let's use simple points: 1st=2, 2nd=1, 3rd=0, 4th=-1
            int points = 0;
            switch(i) {
                case 0: points = 2; break;
                case 1: points = 1; break;
                case 2: points = 0; break;
                case 3: points = -1; break;
            }
            players.get(i).setArenaPoints(points);
        }
    }

    public List<ArenaData> getPlayers() {
        return players;
    }

    public ArenaData getPlayer(String name) {
	for(ArenaData p : players) {
		if(p.getPlayerName().equals(name)) {
			return p;
		}
	}
	return null;
    }
}
