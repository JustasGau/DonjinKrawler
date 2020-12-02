package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import donjinkrawler.config.ConfigSingleton;
import donjinkrawler.logging.LoggerSingleton;
import donjinkrawler.memento.History;
import donjinkrawler.memento.Memento;
import donjinkrawler.memento.SavedObject;
import donjinkrawler.pckcontrol.PacketControlUnit;
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

        PacketControlUnit pcu = new PacketControlUnit();
        GameServer gameServer = this;

        if (!useProxyListener) {
            kryoServer.addListener(new Listener() {

                public void received(Connection connection, Object object) {
                    pcu.handle(gameServer, object, connection);
                }

                public void disconnected(Connection connection) {
                    handleDisconnect(connection);
                }

            });
        } else {
            kryoServer.addListener(new ListenerProxy() {
                public void received(Connection connection, Object object) {
                    pcu.handle(gameServer, object, connection);
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

    public HashMap<Integer, RoomData> copyRoomMap(HashMap<Integer, RoomData> original) throws CloneNotSupportedException {
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
        Boolean changed = false;
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

    private void initMap() {
        GameMapGenerator generator = new GameMapGenerator(mapSize);
        List<String> gameMapString = generator.generate();

        rooms = generator.generateRoomsFromString(gameMapString);
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

    public void createNewPlayer(Connection connection, LoginPacket loginPacket) {
        // TODO: maybe add common config for starting position?
        if (!isValidName(loginPacket.name)) {
            loginPacket.name = loginPacket.name + rand.nextInt(69420);
        }
        PlayerData player = ConnectionManager.getInstance().getPlayerFromConnection(connection);
        if (player == null) {
            player = new PlayerData(
                    loginPacket.name,
                    ConnectionManager.getInstance().getIncrementingPlayerIDs(),
                    250,
                    250
            );
            ConnectionManager.getInstance().addPlayer(connection, player);
        } else {
            player.setName(loginPacket.name);
        }
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
        MessageSender.sendMessageToSingle(connection, kryoServer, "You joined the server");
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

    public void sendEnemies(Boolean create) {
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

    public void addTimer() {
        if (!timerAdded) {
            new Timer();
            timerAdded = true;
        }
    }

    public com.esotericsoftware.kryonet.Server getKryo() {
        return this.kryoServer;
    }

    public void setCurrentDirection(String direction) {
        this.currentDirection = direction;
    }

    public RoomData getCurrentRoom() {
        return this.rooms.get(this.currentRoom);
    }

    public void setCurrentRoom(int room) {
        this.currentRoom = room;
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
