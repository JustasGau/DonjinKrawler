package donjinkrawler;

import donjinkrawler.enemies.Enemy;
import donjinkrawler.enemies.EnemyGenerator;
import donjinkrawler.enemies.big.BigEnemyFactory;
import donjinkrawler.enemies.small.SmallEnemyFactory;
import donjinkrawler.logging.LoggerSingleton;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    private static final LoggerSingleton logger = LoggerSingleton.getInstance();

    private static final EnemyGenerator smallEnemyGenerator = new EnemyGenerator(new SmallEnemyFactory());
    private static final EnemyGenerator bigEnemyGenerator   = new EnemyGenerator(new BigEnemyFactory());

    private static final ArrayList<Enemy> smallEnemies = smallEnemyGenerator.generateRandomEnemies(5);
    private static final ArrayList<Enemy> bigEnemies = bigEnemyGenerator.generateRandomEnemies(1);

    private static int TARGET_FPS = 60;
    private static long OPTIMAL_TIME = 1000000000 / TARGET_FPS;



    public static void main(String[] args) throws IOException {
        new Timer();
        int serverPort = 59001;
        int threadPool = 4;
        var pool = Executors.newFixedThreadPool(threadPool);

        logger.info("Server is running");

        try (var listener = new ServerSocket(serverPort)) {
            while (true) {
                pool.execute(new Server.Handler(listener.accept()));
            }
        }
    }

    public static class Timer extends Thread {
        long now;
        long updateTime;
        long wait;

        public Timer() {
            start();
        }
        public void run() {
            //Main loop to space out updates and entity checking
            while (true) {
                now = System.nanoTime();

                Update();

                updateTime = System.nanoTime() - now;
                wait = (OPTIMAL_TIME - updateTime) / 1000000;

                try {
                    Thread.sleep(wait);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void Update() {
        for (Enemy enemy : smallEnemies) {
            enemy.incrementTick(TARGET_FPS);
        }
        for (Enemy enemy : bigEnemies) {
            enemy.incrementTick(TARGET_FPS);
        }
        sendEnemyInfo(smallEnemies);
        sendEnemyInfo(bigEnemies);
    }

    public static void sendEnemyInfo(ArrayList<Enemy> enemies){
        for (Enemy enemy : enemies) {
            for (PrintWriter writer : writers) {
                writer.println("ENI " + enemy.getID() + " " + enemy.getInfo());
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
                if (!otherName.equals(name)) {
                    logger.debug("Sent name: " + otherName);
                    out.println("CRT " + otherName);
                }
            }
        }

        public void sendEnemies(ArrayList<Enemy> enemies) {
            for (Enemy enemy : enemies) {
                logger.debug("Sent enemy: " + enemy.getName()+ " " + enemy.getID());
                out.println("ENM " + enemy.getName() + " " + enemy.getID() + " " + enemy.getX() + " " + enemy.getY());
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
                logger.debug("Player " + name + " has joined the server");
                sendToAll("MSG A player has joined the server");
                sendToAll("CRT " + name);
                sendCurrentPlayers();
                writers.add(out);

                sendEnemies(smallEnemies);
                sendEnemies(bigEnemies);

                while (true) {
                    String input = in.nextLine();
                    sendToAll(input + " " + name);
                }
            } catch (Exception e) {
                logger.error(e);
            } finally {
                if (out != null) {
                    writers.remove(out);
                    names.remove(name);
                    logger.info("Player " + name + " has left the server");
                    sendToAll("MSG A player has left");
                    sendToAll("DLT " + name);
                }
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
