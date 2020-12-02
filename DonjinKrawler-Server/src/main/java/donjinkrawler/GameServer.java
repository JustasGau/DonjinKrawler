package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;
import donjinkrawler.config.ConfigSingleton;
import donjinkrawler.logging.LoggerSingleton;
import donjinkrawler.memento.History;
import donjinkrawler.memento.Memento;
import donjinkrawler.memento.SavedObject;
import donjinkrawler.proxy.BaseListener;
import donjinkrawler.proxy.ListenerProxy;
import krawlercommon.ConnectionManager;
import krawlercommon.PlayerData;
import krawlercommon.RegistrationManager;
import krawlercommon.enemies.Enemy;
import krawlercommon.map.RoomData;
import krawlercommon.packets.*;

import java.io.IOException;
import java.util.*;

public class GameServer {
    protected final com.esotericsoftware.kryonet.Server kryoServer =
            new com.esotericsoftware.kryonet.Server(16384 * 64, 16384 * 64);
    protected final int mapSize = 10;
    protected final int KRYO_TCP_PORT = 54555;
    protected final int KRYO_UDP_PORT = 54777;

    protected final LoggerSingleton logger = LoggerSingleton.getInstance();
    protected final Random rand = new Random();

    protected HashMap<Integer, RoomData> rooms;
    protected int currentRoom = 0;
    protected String currentDirection;
    protected boolean timerAdded = false;
    private int TARGET_FPS = 60;
    private long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    private boolean useProxyListener = false;
    private History history;

    public void startServer() throws IOException {
        Log.set(Log.LEVEL_ERROR);
        useProxyListener = Boolean.parseBoolean(ConfigSingleton.getInstance().getPropertyValue("krawler.useProxy"));
        setupKryo();
        initMap();
        history = new History();
        new TerminalReader();
        logger.info("Server is running");
    }

    private void setupKryo() throws IOException {
        kryoServer.getKryo().setReferences(true);
        kryoServer.start();
        kryoServer.bind(KRYO_TCP_PORT, KRYO_UDP_PORT);
        RegistrationManager.registerKryo(kryoServer.getKryo());
        if (!useProxyListener) {
            kryoServer.addListener(new BaseListener() {

                public void received(Connection connection, Object object) {
                    handleReceivedPacket(connection, object);
                }

                public void disconnected(Connection connection) {
                    handleDisconnect(connection);
                }

            });
        } else {
            kryoServer.addListener(new ListenerProxy() {
                public void received(Connection connection, Object object) {
                    handleReceivedPacket(connection, object);
                }

                public void disconnected(Connection connection) {
                    handleDisconnect(connection);
                }
            });
        }
    }

    public void save() throws CloneNotSupportedException {
        history.push(new Memento(this));
    }

    public void undo() throws InterruptedException {
        history.undo();
    }

    public HashMap<Integer, RoomData> copyRoomMap(HashMap<Integer, RoomData> original)
            throws CloneNotSupportedException {
        HashMap<Integer, RoomData> copy = new HashMap<>();
        for (Map.Entry<Integer, RoomData> entry : original.entrySet()) {
            RoomData orig = entry.getValue();
            RoomData copyRoom = orig.deepCopy();
            copy.put(entry.getKey(), copyRoom);
        }
        return copy;
    }

    public SavedObject backup() throws CloneNotSupportedException {
        return new SavedObject(
                currentRoom,
                currentDirection,
                copyRoomMap(rooms));
    }

    public void restore(SavedObject state) throws InterruptedException {
        boolean changed = false;
        currentDirection = state.getDirection();
        rooms = state.getRooms();
        if (currentRoom != state.getCurrentRoom()) {
            changed = true;
            currentRoom = state.getCurrentRoom();
        }
        sendMapToPlayers();
        Thread.sleep(1000);
        if (changed) {
            sendEnemies(true);
            RoomPacket roomPacket = new RoomPacket();
            roomPacket.direction = currentDirection;
            roomPacket.id = currentRoom;
            kryoServer.sendToAllUDP(roomPacket);
        } else {
            sendEnemies(false);
        }
    }

    private void handleReceivedPacket(Connection connection, Object object) {
        if (object instanceof LoginPacket) {
            handleLogin(connection, (LoginPacket) object);
        } else if (object instanceof MessagePacket) {
            handleMessage(connection, (MessagePacket) object);
        } else if (object instanceof MoveCharacter) {
            handlePosUpdate(connection, object);
        } else if (object instanceof RoomPacket) {
            handleRoomChange(connection, (RoomPacket) object);
        } else if (object instanceof ChangeEnemyStrategyPacket) {
            handleEnemyStrategyChange((ChangeEnemyStrategyPacket) object);
        } else if (object instanceof CharacterAttackPacket) {
            handlePlayerAttack(connection, (CharacterAttackPacket) object);
        } else if (object instanceof DamageEnemyPacket) {
            handlePlayerDamageEnemy((DamageEnemyPacket) object);
        }
    }

    private void initMap() {
        GameMapGenerator generator = new GameMapGenerator(mapSize);
        List<String> gameMapString = generator.generate();

        rooms = generator.generateRoomsFromString(gameMapString);
    }

    private void handleLogin(Connection connection, LoginPacket object) {
        createNewPlayer(connection, object);
        if (!timerAdded) {
            new Timer();
            timerAdded = true;
        }
    }

    private void handleMessage(Connection connection, MessagePacket messagePacket) {
        String playerName = ConnectionManager.getInstance().getPlayerFromConnection(connection).getName();
        messagePacket.message = messagePacket.message + " " + playerName;
        kryoServer.sendToAllExceptUDP(connection.getID(), messagePacket);
    }

    private void handlePosUpdate(Connection connection, Object object) {
        MoveCharacter packet = (MoveCharacter) object;
        PlayerData player = ConnectionManager.getInstance().getPlayerFromConnection(connection);
        player.setX(packet.x);
        player.setY(packet.y);
        kryoServer.sendToAllExceptTCP(connection.getID(), object);
    }

    private void handleRoomChange(Connection connection, RoomPacket roomPacket) {
        currentRoom = roomPacket.id;
        currentDirection = roomPacket.direction;
        sendEnemies(true);
        kryoServer.sendToAllExceptUDP(connection.getID(), roomPacket);
    }

    private void handleDisconnect(Connection connection) {
        PlayerData player = ConnectionManager.getInstance().getPlayerFromConnection(connection);
        if (player != null) {
            String message = "Player " + player.getName() + " has left";
            logger.info(message);
            MessageSender.sendMessageToAllExcept(connection, kryoServer, message);
            DisconnectPacket dcPacket = new DisconnectPacket();
            dcPacket.id = player.getId();
            kryoServer.sendToAllExceptTCP(connection.getID(), dcPacket);
            ConnectionManager.getInstance().removeConnection(connection);
        }
    }

    private void handleEnemyStrategyChange(ChangeEnemyStrategyPacket packet) {
        for (Enemy enemy : rooms.get(currentRoom).getEnemies()) {
            if (enemy != null && enemy.getID() == packet.id) {
                enemy.setCurrentStrategy(packet.strategy);
                break;
            }
        }
    }

    private void handlePlayerAttack(Connection connection, CharacterAttackPacket packet) {
        kryoServer.sendToAllExceptUDP(connection.getID(), packet);
    }

    private void handlePlayerDamageEnemy(DamageEnemyPacket packet) {
        for (Enemy enemy : rooms.get(currentRoom).getEnemies()) {
            if (enemy != null && enemy.getID() == packet.id) {
                enemy.damage(packet.damage);
                break;
            }
        }
    }

    private void createNewPlayer(Connection connection, LoginPacket loginPacket) {
        // TODO: maybe add common config for starting position?
        if (!isValidName(loginPacket.name)) {
            loginPacket.name = loginPacket.name + rand.nextInt(69420);
        }
        PlayerData player = ConnectionManager.getInstance().getPlayerFromConnection(connection);
        player.setName(loginPacket.name);

        sendMapToPlayer(connection);
        sendWelcomeMessages(connection);
        sendIdentificationMessage(connection, player);
        logger.debug("Player " + player.getName() + " has joined the server");
        sendPlayerToExistingClients(connection, player);
        sendExistingPlayersToClient(connection.getID(), player);
        sendEnemiesToPlayer(connection, true);
    }

    private void sendPlayerToExistingClients(Connection connection, PlayerData player) {
        CreatePlayerPacket pck = new CreatePlayerPacket();
        pck.player = player;
        kryoServer.sendToAllExceptTCP(connection.getID(), pck);
    }

    private void sendMapToPlayer(Connection connection) {
        MapPacket mapPacket = new MapPacket();
        mapPacket.gridSize = mapSize;
        mapPacket.rooms = rooms;
        kryoServer.sendToTCP(connection.getID(), mapPacket);
    }

    private void sendMapToPlayers() {
        MapPacket mapPacket = new MapPacket();
        mapPacket.gridSize = mapSize;
        mapPacket.rooms = rooms;
        mapPacket.update = true;
        kryoServer.sendToAllTCP(mapPacket);
    }

    private void sendWelcomeMessages(Connection connection) {
        MessageSender.sendMessageToAllExcept(connection, kryoServer, "A player has joined the server");
    }

    private void sendIdentificationMessage(Connection connection, PlayerData player) {
        IdPacket idPacket = new IdPacket();
        idPacket.currentRoom = currentRoom;
        idPacket.playerData = player;
        kryoServer.sendToTCP(connection.getID(), idPacket);
    }

    private boolean isValidName(String name) {
        return ConnectionManager.getInstance().getAllPlayers()
                .stream().noneMatch(p -> p.getName().equals(name));
    }

    private void sendExistingPlayersToClient(int id, PlayerData player) {
        for (PlayerData tempPlayer : ConnectionManager.getInstance().getAllPlayers()) {
            if (tempPlayer != player) {
                CreatePlayerPacket createPlayerPacket = new CreatePlayerPacket();
                createPlayerPacket.player = tempPlayer;
                kryoServer.sendToTCP(id, createPlayerPacket);
            }
        }
    }

    private void sendEnemies(Boolean create) {
        EnemyPacket enemyPacket = new EnemyPacket();
        if (create) {
            enemyPacket.setCreate();
        }
        enemyPacket.getEnemies().addAll(rooms.get(currentRoom).getEnemies());
        kryoServer.sendToAllTCP(enemyPacket);
    }

    private void sendEnemiesToPlayer(Connection connection, Boolean create) {
        EnemyPacket enemyPacket = new EnemyPacket();
        if (create) {
            enemyPacket.setCreate();
        }
        enemyPacket.getEnemies().addAll(rooms.get(currentRoom).getEnemies());
        kryoServer.sendToTCP(connection.getID(), enemyPacket);
    }

    private void update() {
        if (rooms.get(currentRoom).getEnemies().size() > 0) {
            for (Enemy enemy : rooms.get(currentRoom).getEnemies()) {
                if (enemy != null) {
                    enemy.incrementTick(TARGET_FPS, kryoServer);
                }
            }
        }
    }

    public class Timer extends Thread {
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
                for (Integer conId : ConnectionManager.getInstance().getAllConnections()) {
                    EnemyPacket enemyPacket = new EnemyPacket();
                    enemyPacket.getEnemies().addAll(rooms.get(currentRoom).getEnemies());
                    kryoServer.sendToTCP(conId, enemyPacket);
                }
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

    public class TerminalReader extends Thread {

        public TerminalReader() {
            start();
        }

        public void run() {
            Scanner in = new Scanner(System.in);
            while (true) {
                String s = in.nextLine();
                if (s.equals("save")) {
                    try {
                        save();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                } else if (s.equals("load")) {
                    try {
                        undo();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
