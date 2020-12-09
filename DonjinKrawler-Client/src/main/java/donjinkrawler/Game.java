package donjinkrawler;

import donjinkrawler.decorator.EnemyClothingDecorator;
import donjinkrawler.decorator.MaracasEnemy;
import donjinkrawler.decorator.PonchosEnemy;
import donjinkrawler.decorator.SombrerosEnemy;
import donjinkrawler.facade.MusicMaker;
import donjinkrawler.flyweight.EnemyFlyweight;
import donjinkrawler.flyweight.EnemySelector;
import donjinkrawler.logging.LoggerSingleton;
import donjinkrawler.map.GameMap;
import donjinkrawler.map.Room;
import krawlercommon.PlayerData;
import krawlercommon.enemies.Enemy;
import krawlercommon.map.DoorDirection;
import krawlercommon.map.RoomData;
import krawlercommon.map.RoomType;
import krawlercommon.packets.MoveCharacter;
import krawlercommon.packets.RoomPacket;
import krawlercommon.strategies.EnemyStrategy;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class Game extends JPanel implements ActionListener {
    public static Map<Integer, AbstractShellInterface> shells = new ConcurrentHashMap<>();
    static com.esotericsoftware.kryonet.Client client;
    private static Player player = null;
    private static int TARGET_FPS = 60;
    private static long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    private final Timer timer;
    private final int DELAY = 10;
    private final Random random = new Random();
    private final JLabel label;
    LoggerSingleton logger = LoggerSingleton.getInstance();
    private GameMap gameMap;
    private MusicMaker musicMaker = new MusicMaker();

    public Game(com.esotericsoftware.kryonet.Client client,
                JLabel label,
                Player newPlayer,
                Map<Integer, RoomData> rooms,
                int currentRoom) {
        this(client, label, newPlayer, rooms, currentRoom, true);
    }

    public Game(com.esotericsoftware.kryonet.Client client,
                JLabel label,
                Player newPlayer,
                Map<Integer, RoomData> rooms,
                int currentRoom,
                boolean shouldAddTimer) {
        this.label = label;
        player = newPlayer;
        this.gameMap = new GameMap(new Room(rooms.get(currentRoom)));
        this.client = client;

        addKeyListener(new Game.TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        timer = new Timer(DELAY, this);
        new GameLoop();

        if (shouldAddTimer) {
            timer.start();
        }
    }

    private static void sendPositionUpdate() {
        MoveCharacter msg = new MoveCharacter();
        msg.id = player.getId();
        msg.x = player.getX();
        msg.y = player.getY();
        client.sendTCP(msg);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        gameMap.getCurrentRoom().draw(g);
        drawCurrentPlayer(g);
        for (AbstractShellInterface sh : shells.values()) {
            if (gameMap.getCurrentRoom().getRoomData().getRoomType() != RoomType.ITEM) {
                drawShell(g2d, sh);
                sh.drawClothes(g2d, this);
            } else if (!(sh instanceof EnemyShell)) {
                drawShell(g2d, sh);
            }
        }
    }

    public void updateMap(HashMap<Integer, RoomData> rooms) {
        this.gameMap = new GameMap(new Room(rooms.get(0)));
    }

    private void drawCurrentPlayer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
        g2d.drawImage(player.getAttackImage(), player.getX() - 10, player.getY() - 10, this);
        SwingUtils.drawHealthBar(g2d, player.getX(), player.getY(), 20, 5, player.getHealth());
        g2d.setColor(Color.GREEN);
        g2d.drawString(String.valueOf(player.getHealth()), player.getX() + 5, player.getY() - 20);
        g2d.drawString(player.getName(), player.getX(), player.getY() + 30);
    }

    private void drawShell(Graphics2D g2d, AbstractShellInterface pl) {
        g2d.drawImage(pl.getImage(), pl.getX(), pl.getY(), this);
        g2d.setColor(Color.BLUE);
        int offset = 30;
        if (pl.getName().equals("Boss")) {
            offset = 60;
        }
        g2d.drawString(pl.getName() + " " + pl.getID(), pl.getX(), pl.getY() + offset);
        g2d.drawImage(pl.getAttackImage(), pl.getX() - 10, pl.getY() - 10, this);
        g2d.setColor(Color.YELLOW);
        if (pl.getInfo() != null) {
            g2d.drawString(pl.getInfo(), pl.getX(), pl.getY() + offset + 20);
        }
        SwingUtils.drawHealthBar(g2d, pl.getX(), pl.getY(), 20, 5, pl.getHealth());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameUpdate();
    }

    private void gameUpdate() {
        DoorDirection direction = gameMap.update(player);
        if (direction != null) {
            if (direction == DoorDirection.LEFT) {
                player.setCoordinates(400, 250);
                movePlayerShells(400, 250);
            } else if (direction == DoorDirection.TOP) {
                player.setCoordinates(250, 400);
                movePlayerShells(250, 400);
            } else if (direction == DoorDirection.RIGHT) {
                player.setCoordinates(40, 250);
                movePlayerShells(40, 250);
            } else if (direction == DoorDirection.BOTTOM) {
                player.setCoordinates(250, 40);
                movePlayerShells(250, 40);
            }
            RoomData newRoom = gameMap.getCurrentRoom().getRoomFromDirection(direction);
            sendRoomPacket(direction, newRoom.getId());
            Room newRoomObj = getNewRoom(newRoom);
            gameMap.setCurrentRoom(newRoomObj);
        }

        Integer itemId = player.move(
                gameMap.getCurrentRoom().getWalls(),
                gameMap.getCurrentRoom().getDoors(),
                gameMap.getCurrentRoom().getObstacles(),
                gameMap.getCurrentRoom().getDecorations(),
                gameMap.getCurrentRoom().getItems()
        );

        if (itemId != null) {
            this.gameMap.getCurrentRoom().removeItem(itemId);
        }

        repaint();
    }

    private void sendRoomPacket(DoorDirection nextRoom, int roomID) {
        RoomPacket roomPacket = new RoomPacket();
        roomPacket.direction = nextRoom.toString();
        roomPacket.id = roomID;
        client.sendTCP(roomPacket);
    }

    private Room getNewRoom(RoomData newRoom) {
        Room newRoomObj = null;
        try {
            newRoomObj = gameMap.getCurrentRoom().clone();
        } catch (CloneNotSupportedException e) {
            logger.error("Cloning not supported " + e.getMessage());
        }
        if (newRoomObj != null) {
            newRoomObj.setRoomData(newRoom);
            newRoomObj.initDoors();
            player.detachAllObservers();
            player.setHasNotifiedObservers(false);
            newRoomObj.getRoomData().getEnemies().forEach(player::attachObserver);
            if (newRoom.getRoomType().equals(RoomType.BOSS)) {
                player.getMusic().playBossRoomMusic();
            } else if (newRoom.getRoomType().equals(RoomType.ITEM)) {
                player.getMusic().playItemRoomMusic();
            }
        } else {
            newRoomObj = new Room(newRoom);
        }
        return newRoomObj;
    }

    private void movePlayerShells(int x, int y) {
        for (AbstractShellInterface sh : shells.values()) {
            if (sh instanceof PlayerShell) {
                sh.setX(x);
                sh.setY(y);
            }
        }
    }

    public void addPlayerShell(PlayerData player) {
        PlayerShell playerShell = new PlayerShell(player.getName());
        playerShell.setX(player.getX());
        playerShell.setY(player.getY());
        shells.put(player.getId(), playerShell);
    }

    public void changeRoom(RoomPacket roomPacket) {
        String direction = roomPacket.direction;
        DoorDirection doorDirection = DoorDirection.valueOf(roomPacket.direction);
        RoomData currentRoomData = gameMap.getCurrentRoom().getRoomFromDirection(doorDirection);
        gameMap.setCurrentRoom(new Room(currentRoomData));

        switch (direction) {
            case "LEFT":
                player.setCoordinates(400, 250);
                break;
            case "TOP":
                player.setCoordinates(250, 400);
                break;
            case "RIGHT":
                player.setCoordinates(40, 250);
                break;
            case "BOTTOM":
                player.setCoordinates(250, 40);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public void updateEnemyStrategy(int id, EnemyStrategy strategy) {
        AbstractShellInterface temp = shells.get(id);
        if (temp != null) {
            temp.setInfo(strategy.getStrategy());
        }
    }

    public void updateEnemyInfo(String enemyData) {
        String[] arrOfStr = enemyData.substring(4).split(" ");
        int tempID = Integer.parseInt(arrOfStr[0]);
        String tempInfo = arrOfStr[1];
        AbstractShellInterface temp = shells.get(tempID);
        if (temp != null) {
            temp.setInfo(tempInfo);
        }
    }

    public void updateEnemies(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy != null) {
                AbstractShellInterface updatedEnemy = shells.get(enemy.getID());
                if (updatedEnemy != null) {
                    updatedEnemy.setX(enemy.getX());
                    updatedEnemy.setY(enemy.getY());
                    updatedEnemy.setHealth(enemy.getHealth());
                }
                if (shells.size() > enemies.size()) {
                    removeLeftoverShells(enemies);
                }
            }
        }
    }

    private void removeLeftoverShells(List<Enemy> enemies) {
        Iterator<AbstractShellInterface> shellIterator = shells.values().iterator();

        while (shellIterator.hasNext()) {
            AbstractShellInterface shell = shellIterator.next();
            if (shell.getShellType().equals(ShellType.ENEMY)) {
                boolean contains = enemies.stream().anyMatch(e -> e.getID() == shell.getID());
                if (!contains) {
                    shellIterator.remove();
                }
            }
        }
    }

    public void addEnemies(List<Enemy> enemies) {
        shells.values().removeIf(this::isEnemyShell);
        enemies.stream().filter(Objects::nonNull).forEach(this::addEnemy);

        for (AbstractShellInterface pl : shells.values()) {
            pl.addClothing();
        }
    }

    private void addEnemy(Enemy e) {
        if (e.getName().equals("Boss")) {
            addBoss(e);
        } else {
            addDecoratedEnemy(e);
        }
    }

    private EnemyShell getEnemyObject(String name, int id, int x, int y) {
        EnemyFlyweight type = EnemySelector.getEnemyType(name);
        EnemyShell enemy = new EnemyShell(name, id, x, y, type);
        return enemy;
    }

    private void addBoss(Enemy e) {
        shells.put(e.getID(), getEnemyObject(e.getName(), e.getID(), e.getX(), e.getY()));
    }

    private void addDecoratedEnemy(Enemy e) {
        if (random.nextInt(10000) % 10 == 0) {
            shells.put(e.getID(),
                    new MaracasEnemy(
                            new PonchosEnemy(
                                    new SombrerosEnemy(
                                            getEnemyObject(e.getName(), e.getID(), e.getX(), e.getY())
                                    )
                            )
                    ));
        } else {
            shells.put(e.getID(), getEnemyObject(e.getName(), e.getID(), e.getX(), e.getY()));
        }
    }

    public boolean isEnemyShell(AbstractShellInterface shell) {
        return shell instanceof EnemyShell || shell instanceof EnemyClothingDecorator;
    }

    public void changeShellPosition(MoveCharacter packet) {
        AbstractShellInterface temp = shells.get(packet.id);
        if (temp != null) {
            temp.setX(packet.x);
            temp.setY(packet.y);
        }
    }

    public void deletePlayerShell(int id) {
        shells.remove(id);
    }

    public void drawPlayerAttack(int id) {
        AbstractShellInterface temp = shells.get(id);
        temp.setIsAttacking(true);
    }

    // used in testing
    public Map<Integer, AbstractShellInterface> getShells() {
        return shells;
    }

    public Room getCurrentRoom() {
        return gameMap.getCurrentRoom();
    }

    public static class GameLoop extends Thread {
        long now;
        long updateTime;
        long wait;

        public GameLoop() {
            start();
        }

        public void run() {
            //Main loop to space out updates and entity checking
            while (true) {
                now = System.nanoTime();
                player.incrementTimer();
                sendPositionUpdate();

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

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }

}