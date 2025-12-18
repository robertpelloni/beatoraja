package bms.player.beatoraja.arena.net;

import java.io.*;
import java.net.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import bms.player.beatoraja.arena.ArenaManager;

public class ArenaClient {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final ArenaManager manager;
    private final String playerName;
    private boolean running;
    private final Json json;

    public ArenaClient(ArenaManager manager, String host, int port, String playerName) throws IOException {
        this.manager = manager;
        this.playerName = playerName;
        this.json = new Json();

        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Send login
        sendMessage(new ArenaMessage(ArenaMessage.TYPE_LOGIN, playerName, 0));

        running = true;
        new Thread(this::listen).start();
    }

    private void listen() {
        try {
            String line;
            while (running && (line = in.readLine()) != null) {
                final String jsonStr = line;
                Gdx.app.postRunnable(() -> handleMessage(jsonStr));
            }
        } catch (IOException e) {
            if (running) e.printStackTrace();
        } finally {
            close();
        }
    }

    private void handleMessage(String jsonStr) {
        try {
            ArenaMessage msg = json.fromJson(ArenaMessage.class, jsonStr);
            if (msg == null) return;

            switch (msg.type) {
                case ArenaMessage.TYPE_SCORE_UPDATE:
                case ArenaMessage.TYPE_PLAYER_JOINED:
                    // Only update if it's not us (though server should handle echo)
                    if (!msg.playerName.equals(this.playerName)) {
                        manager.updateScore(msg.playerName, msg.score);
                    }
                    break;
                case ArenaMessage.TYPE_PLAYER_LEFT:
                    // Handle player left if needed
                    break;
                case ArenaMessage.TYPE_SONG_SELECT:
                    if (!msg.playerName.equals(this.playerName)) {
                        manager.onRemoteSongSelected(msg.songHash);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendScore(int score) {
        sendMessage(new ArenaMessage(ArenaMessage.TYPE_SCORE_UPDATE, playerName, score));
    }

    public void sendSongSelect(String hash) {
        sendMessage(new ArenaMessage(ArenaMessage.TYPE_SONG_SELECT, playerName, hash));
    }

    private void sendMessage(ArenaMessage msg) {
        if (out != null) {
            String jsonStr = json.toJson(msg);
            out.println(jsonStr);
        }
    }

    public void close() {
        running = false;
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
