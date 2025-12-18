package bms.player.beatoraja.arena.net;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import com.badlogic.gdx.utils.Json;

public class ArenaServer {

    private ServerSocket serverSocket;
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private boolean running;
    private final Json json = new Json();

    public ArenaServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        new Thread(this::listen).start();
    }

    private void listen() {
        try {
            while (running) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            if (running) e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null) serverSocket.close();
            for (ClientHandler client : clients) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(String message, ClientHandler exclude) {
        for (ClientHandler client : clients) {
            if (client != exclude) {
                client.send(message);
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String playerName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    // Parse minimal info to get name if needed, but mostly just broadcast
                    ArenaMessage msg = json.fromJson(ArenaMessage.class, line);
                    if (msg != null) {
                        if (msg.type == ArenaMessage.TYPE_LOGIN) {
                            this.playerName = msg.playerName;
                        }
                        // Broadcast to others
                        broadcast(line, this);
                    }
                }
            } catch (IOException e) {
                // Client disconnected
            } finally {
                close();
                clients.remove(this);
                // Notify others of disconnect?
            }
        }

        public void send(String msg) {
            if (out != null) {
                out.println(msg);
            }
        }

        public void close() {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
