package main.java.donjinkrawler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executors;


public class Server {
    // The set of all the print writers for all the clients, used for broadcast.
    private static final Set<PrintWriter> writers = new HashSet<>();
    private static final Set<String> names = new HashSet<>();
    private static final int mapSize = 10;
    private static final GameMapGenerator generator = new GameMapGenerator(mapSize);
    private static final String gameMapString = generator.generate();


    public static void main(String[] args) throws Exception {
        int serverPort = 59001;
        int threadPool = 4;

        System.out.println("Server is running");
        var pool = Executors.newFixedThreadPool(threadPool);
        try (var listener = new ServerSocket(serverPort)) {
            while (true) {
                pool.execute(new Server.Handler(listener.accept()));
            }
        }
    }


    private static class Handler implements Runnable {
        private final Socket socket;
        private Scanner in;
        private PrintWriter out;
        private String name;


        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void sendToAll(String message) {
            for (PrintWriter writer : writers) {
                if (writer != out) {
                    writer.println(message);
                }
            }
        }

        public void sendCurrentPlayers() {
            for (String otherName : names) {
                if (otherName != name) {
                    System.out.println("Sent name: " + otherName);
                    out.println("CRT " + otherName);
                }
            }
        }

        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    out.println("SBN");
                    name = in.nextLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!name.isBlank() && !names.contains(name)) {
                            names.add(name);
                            break;
                        }
                    }
                }
                out.println("MAP " + mapSize + " " + gameMapString);
                out.println("MSG You joined the server");
                System.out.println("Player " + name + " has joined the server");
                sendToAll("MSG A player has joined the server");
                sendToAll("CRT " + name);
                sendCurrentPlayers();
                writers.add(out);

                while (true) {
                    String input = in.nextLine();
                    sendToAll(input + " " + name);
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (out != null) {
                    writers.remove(out);
                    names.remove(name);
                    System.out.println("Player " + name + " has left the server");
                    sendToAll("MSG A player has left");
                    sendToAll("DLT " + name);
                }
                try {
                    socket.close();
                } catch (IOException expected) {
                }
            }
        }
    }
}
