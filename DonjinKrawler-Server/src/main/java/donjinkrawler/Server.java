package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import krawlercommon.enemies.*;
import krawlercommon.enemies.big.BigEnemyFactory;
import krawlercommon.enemies.small.SmallEnemyFactory;
import donjinkrawler.logging.LoggerSingleton;
import krawlercommon.RegistrationManager;
import krawlercommon.packets.ConnectPacket;
import krawlercommon.packets.EnemyPacket;
import krawlercommon.packets.MessagePacket;

import java.io.IOException;
import java.util.*;


public class Server {
    private static final com.esotericsoftware.kryonet.Server server =
            new com.esotericsoftware.kryonet.Server(16384 * 64, 16384 * 64);
    private static final Map<Integer, PlayerContainer> playerConnectionMap = new HashMap<>();
    private static int playerIDs = 1;
    private static final int mapSize = 10;
    private static final GameMapGenerator generator = new GameMapGenerator(mapSize);
    private static final String gameMapString = generator.generate();
    private static final LoggerSingleton logger = LoggerSingleton.getInstance();

    private static final EnemyGenerator smallEnemyGenerator = new EnemyGenerator(new SmallEnemyFactory());
    private static final EnemyGenerator bigEnemyGenerator = new EnemyGenerator(new BigEnemyFactory());

    private static final ArrayList<Enemy> smallEnemies = smallEnemyGenerator.generateRandomEnemies(5);
    private static final ArrayList<Enemy> bigEnemies = bigEnemyGenerator.generateRandomEnemies(1);

    private static int TARGET_FPS = 60;
    private static long OPTIMAL_TIME = 1000000000 / TARGET_FPS;


    public static void main(String[] args) throws IOException {
        server.start();
        server.bind(54555, 54777);
        RegistrationManager.registerKryo(server.getKryo());
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof ConnectPacket) {
                    ConnectPacket connectPacket = (ConnectPacket) object;
                    if (playerConnectionMap.get(connection.getID()) == null) {
                        createNewPlayer(connection, connectPacket);
                    }
                    System.out.println(connectPacket.name);
                } else if (object instanceof MessagePacket) {
                    MessagePacket messagePacket = (MessagePacket) object;
                    String playerName = playerConnectionMap.get(connection.getID()).getName();
                    messagePacket.message = messagePacket.message + " " + playerName;
                    server.sendToAllExceptTCP(connection.getID(), messagePacket);
                }
            }

            public void disconnected(Connection connection) {
                PlayerContainer player = playerConnectionMap.get(connection.getID());
                logger.info("Player " + player.getName() + " has left the server");
                MessagePacket messagePacket = new MessagePacket();
                messagePacket.message = "MSG A player has left";
                server.sendToAllExceptTCP(connection.getID(), messagePacket);
                messagePacket.message = "DLT " + player.getName();
                server.sendToAllExceptTCP(connection.getID(), messagePacket);
                playerConnectionMap.remove(connection.getID());
            }
        });
        new Timer();

        logger.info("Server is running");
    }

    private static void createNewPlayer(Connection connection, ConnectPacket connectPacket) {
        PlayerContainer player = new PlayerContainer(connectPacket.name, playerIDs++);
        playerConnectionMap.put(connection.getID(), player);
        MessagePacket messagePacket = new MessagePacket();
        messagePacket.message = "MAP " + mapSize + " " + gameMapString;
        server.sendToTCP(connection.getID(), messagePacket);
        messagePacket.message = "MSG You joined the server";
        server.sendToTCP(connection.getID(), messagePacket);
        logger.debug("Player " + player.getName() + " has joined the server");
        messagePacket.message = "MSG A player has joined the server";
        server.sendToAllExceptTCP(connection.getID(), messagePacket);
        messagePacket.message = "CRT " + player.getName();
        server.sendToAllExceptTCP(connection.getID(), messagePacket);
        sendExistingPlayers(connection.getID(), player);
        sendEnemies(connection.getID(), smallEnemies);
        sendEnemies(connection.getID(), bigEnemies);
    }

    private static void sendEnemies(int id, ArrayList<Enemy> enemies) {
        EnemyPacket enemyPacket = new EnemyPacket();
        for (Enemy enemy : enemies) {
            enemyPacket.addEnemy(enemy);
        }
        server.sendToTCP(id, enemyPacket);
    }

    private static void sendExistingPlayers(int id, PlayerContainer player) {
        for (PlayerContainer tempPlayer : playerConnectionMap.values()) {
            if (tempPlayer != player) {
                MessagePacket messagePacket = new MessagePacket();
                messagePacket.message = "CRT " + tempPlayer.getName();
                server.sendToTCP(id, messagePacket);
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

                update();

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

    private static void update() {
        for (Enemy enemy : smallEnemies) {
            enemy.incrementTick(TARGET_FPS);
        }
        for (Enemy enemy : bigEnemies) {
            enemy.incrementTick(TARGET_FPS);
        }
        sendEnemyInfo(smallEnemies);
        sendEnemyInfo(bigEnemies);
    }

    public static void sendEnemyInfo(ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            MessagePacket messagePacket = new MessagePacket();
            messagePacket.message = "ENI " + enemy.getID() + " " + enemy.getInfo();
            server.sendToAllTCP(messagePacket);
        }
    }
}
