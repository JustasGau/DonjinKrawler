package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import krawlercommon.ConnectionManager;
import krawlercommon.PlayerData;
import krawlercommon.enemies.*;
import donjinkrawler.logging.LoggerSingleton;
import krawlercommon.RegistrationManager;
import krawlercommon.map.RoomData;
import krawlercommon.packets.*;
import krawlercommon.strategies.MoveTowardPlayer;

import java.io.IOException;
import java.util.*;


public class Server {
    private static final com.esotericsoftware.kryonet.Server server =
            new com.esotericsoftware.kryonet.Server(16384 * 64, 16384 * 64);
    public static final Map<Integer, PlayerData> playerConnectionMap = new HashMap<>();

    private static final int mapSize = 10;

    private static final LoggerSingleton logger = LoggerSingleton.getInstance();
    private static final Random rand = new Random();

    private static HashMap<Integer, RoomData> rooms;
    private static int playerIDs = 1;
    private static int currentRoom = 0;

    private static int TARGET_FPS = 60;
    private static long OPTIMAL_TIME = 1000000000 / TARGET_FPS;


    public static void main(String[] args) throws IOException {
        Log.set(Log.LEVEL_ERROR);
        server.getKryo().setReferences(true);
        server.start();
        server.bind(54555, 54777);
        RegistrationManager.registerKryo(server.getKryo());
        ConnectionManager.init(playerConnectionMap);
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof LoginPacket) {
                    handleLogin(connection, (LoginPacket) object);
                } else if (object instanceof MessagePacket) {
                    handleMessage(connection, (MessagePacket) object);
                } else if (object instanceof MoveCharacter) {
                    handlePosUpdate(connection, object);
                } else if (object instanceof RoomPacket) {
                    handleRoomChange(connection, (RoomPacket) object);
                } else if (object instanceof ChangeEnemyStrategyPacket) {
                    handleEnemyStrategyChange(connection, (ChangeEnemyStrategyPacket) object);
                }
            }

            public void disconnected(Connection connection) {
                handleDisconnect(connection);
            }

        });
        initMap();
        logger.info("Server is running");
    }

    private static void initMap() {
        GameMapGenerator generator = new GameMapGenerator(mapSize);
        List<String> gameMapString = generator.generate();

        rooms = generator.generateRoomsFromString(gameMapString);
        new Timer();
    }

    private static void handleLogin(Connection connection, LoginPacket object) {
        if (playerConnectionMap.get(connection.getID()) == null) {
            createNewPlayer(connection, object);
        }
    }

    private static void handleMessage(Connection connection, MessagePacket messagePacket) {
        String playerName = playerConnectionMap.get(connection.getID()).getName();
        messagePacket.message = messagePacket.message + " " + playerName;
        server.sendToAllExceptUDP(connection.getID(), messagePacket);
    }

    private static void handlePosUpdate(Connection connection, Object object) {
        MoveCharacter packet = (MoveCharacter) object;
        PlayerData player = playerConnectionMap.get(connection.getID());
        player.setX(packet.x);
        player.setY(packet.y);
        server.sendToAllExceptTCP(connection.getID(), object);
    }

    private static void handleRoomChange(Connection connection, RoomPacket roomPacket) {
        currentRoom = roomPacket.id;
        sendEnemies(true);
        server.sendToAllExceptUDP(connection.getID(), roomPacket);
    }

    private static void handleDisconnect(Connection connection) {
        PlayerData player = playerConnectionMap.get(connection.getID());
        if (player != null) {
            String message = "Player " + player.getName() + " has left";
            logger.info(message);
            MessageSender.sendMessageToAllExcept(connection, server, message);
            DisconnectPacket dcPacket = new DisconnectPacket();
            dcPacket.id = player.getId();
            server.sendToAllExceptTCP(connection.getID(), dcPacket);
            playerConnectionMap.remove(connection.getID());
        }
    }

    private static void handleEnemyStrategyChange(Connection connection, ChangeEnemyStrategyPacket packet) {
        server.sendToAllTCP(packet);
    }

    private static void createNewPlayer(Connection connection, LoginPacket loginPacket) {
        // TODO: maybe add common config for starting position?
        if (!isValidName(loginPacket.name)) {
            loginPacket.name = loginPacket.name + rand.nextInt(69420);
        }
        PlayerData player = new PlayerData(loginPacket.name, playerIDs++, 250, 250);
        playerConnectionMap.put(connection.getID(), player);
        sendMapToPlayer(connection);
        sendWelcomeMessages(connection);
        sendIdentificationMessage(connection, player);
        logger.debug("Player " + player.getName() + " has joined the server");
        sendPlayerToExistingClients(connection, player);
        sendExistingPlayersToClient(connection.getID(), player);
        sendEnemiesToPlayer(connection);
    }

    private static void sendPlayerToExistingClients(Connection connection, PlayerData player) {
        CreatePlayerPacket pck = new CreatePlayerPacket();
        pck.player = player;
        server.sendToAllExceptTCP(connection.getID(), pck);
    }

    private static void sendMapToPlayer(Connection connection) {
        MapPacket mapPacket = new MapPacket();
        mapPacket.gridSize = mapSize;
        mapPacket.rooms = rooms;
        server.sendToTCP(connection.getID(), mapPacket);
    }

    private static void sendWelcomeMessages(Connection connection) {
        MessageSender.sendMessageToSingle(connection, server, "You joined the server");
        MessageSender.sendMessageToAllExcept(connection, server, "A player has joined the server");
    }

    private static void sendIdentificationMessage(Connection connection, PlayerData player) {
        IdPacket idPacket = new IdPacket();
        idPacket.currentRoom = currentRoom;
        idPacket.playerData = player;
        server.sendToTCP(connection.getID(), idPacket);
    }

    private static boolean isValidName(String name) {
        return playerConnectionMap.values().stream().noneMatch(p -> p.getName().equals(name));
    }

    private static void sendExistingPlayersToClient(int id, PlayerData player) {
        for (PlayerData tempPlayer : playerConnectionMap.values()) {
            if (tempPlayer != player) {
                CreatePlayerPacket createPlayerPacket = new CreatePlayerPacket();
                createPlayerPacket.player = tempPlayer;
                server.sendToTCP(id, createPlayerPacket);
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
        if (rooms.get(currentRoom).getEnemies().size() > 0) {
            for (Enemy enemy : rooms.get(currentRoom).getEnemies()) {
                enemy.incrementTick(TARGET_FPS, server);
                sendEnemies(false);
            }
        }
    }

    private static void sendEnemies(Boolean create) {
        EnemyPacket enemyPacket = new EnemyPacket();
        if (create)
            enemyPacket.setCreate();
        enemyPacket.getEnemies().addAll(rooms.get(currentRoom).getEnemies());
        server.sendToAllTCP(enemyPacket);
    }

    private static void sendEnemiesToPlayer(Connection connection) {
        EnemyPacket enemyPacket = new EnemyPacket();
        enemyPacket.getEnemies().addAll(rooms.get(currentRoom).getEnemies());
        server.sendToTCP(connection.getID(), enemyPacket);
    }

    public static void sendEnemyInfo(ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            MessagePacket messagePacket = new MessagePacket();
            messagePacket.message = "ENI " + enemy.getID() + " " + enemy.getInfo();
            server.sendToAllTCP(messagePacket);
        }
    }

}
