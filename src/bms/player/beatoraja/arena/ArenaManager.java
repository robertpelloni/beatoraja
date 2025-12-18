package bms.player.beatoraja.arena;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import bms.player.beatoraja.arena.net.ArenaClient;
import bms.player.beatoraja.arena.net.ArenaServer;

public class ArenaManager {

    public interface ArenaListener {
        void onSongSelected(String songHash);
        void onStartGame();
    }

    private final List<ArenaData> players;
    private final List<ArenaListener> listeners = new CopyOnWriteArrayList<>();
    private ArenaServer server;
    private ArenaClient client;
    private boolean isHost = false;

    public ArenaManager() {
        this.players = new ArrayList<>();
    }

    public void addListener(ArenaListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ArenaListener listener) {
        listeners.remove(listener);
    }

    public boolean isHost() {
        return isHost;
    }

    public void startServer(int port) throws IOException {
        server = new ArenaServer(port);
        isHost = true;
    }

    public void connect(String host, int port, String playerName) throws IOException {
        client = new ArenaClient(this, host, port, playerName);
        // Ensure local player exists (usually handled by BMSPlayer adding "1P")
        if (getPlayer("1P") == null) {
            addPlayer("1P");
        }
    }

    public void onRemoteSongSelected(String hash) {
        for (ArenaListener listener : listeners) {
            listener.onSongSelected(hash);
        }
    }

    public void onRemoteReady(String name, boolean ready) {
        ArenaData player = getPlayer(name);
        if (player != null) {
            player.setReady(ready);
        }
    }

    public void onStartGame() {
        for (ArenaListener listener : listeners) {
            listener.onStartGame();
        }
    }

    public void sendSongSelect(String hash) {
        if (client != null) {
            client.sendSongSelect(hash);
        }
    }

    public void sendReady(boolean ready) {
        if (client != null) {
            client.sendReady(ready);
        }
    }

    public void sendStartGame() {
        if (client != null) {
            client.sendStartGame();
        }
    }

    public void dispose() {
        if (client != null) client.close();
        if (server != null) server.stop();
    }

    public void addPlayer(String name) {
        if (getPlayer(name) == null) {
            players.add(new ArenaData(name));
        }
    }

    public void updateScore(String name, int score) {
        boolean found = false;
        for (ArenaData player : players) {
            if (player.getPlayerName().equals(name)) {
                player.setScore(score);
                found = true;
                break;
            }
        }
        if (!found) {
            addPlayer(name);
            getPlayer(name).setScore(score);
        }

        calculateRanks();

        // If this update is for the local player ("1P"), broadcast it via client
        if (client != null && name.equals("1P")) {
            client.sendScore(score);
        }
    }

    public void resetScores() {
        for (ArenaData player : players) {
            player.setScore(0);
            player.setRank(0);
            player.setArenaPoints(0);
        }
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
