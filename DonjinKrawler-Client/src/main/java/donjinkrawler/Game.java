package donjinkrawler;

import krawlercommon.PlayerData;
import krawlercommon.enemies.Enemy;
import krawlercommon.map.DoorDirection;
import krawlercommon.map.RoomData;
import krawlercommon.map.RoomType;
import krawlercommon.packets.MoveCharacter;
import krawlercommon.packets.RoomPacket;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.*;

public class Game extends JPanel implements ActionListener {

    com.esotericsoftware.kryonet.Client client;
    private final Timer timer;
    private final Player player;
    private GameMap gameMap;
    private final int delay = 10;
    private static final Map<Integer, AbstractShell> shells = new ConcurrentHashMap<>();

    private final JLabel label;

    public Game(com.esotericsoftware.kryonet.Client client,
                JLabel label,
                Player player,
                Map<Integer, RoomData> rooms,
                int currentRoom) {
        this.label = label;
        this.player = player;
        this.gameMap = new GameMap(new Room(rooms.get(currentRoom)));
        this.client = client;

        addKeyListener(new Game.TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        gameMap.currentRoom.draw(g);
        drawCurrentPlayer(g);
        for (AbstractShell pl : shells.values()) {
            if (gameMap.currentRoom.roomData.getRoomType() != RoomType.ITEM) {
                drawShell(g2d, pl);
            } else if (!(pl instanceof EnemyShell)) {
                drawShell(g2d, pl);
            }
        }
    }

    private void drawCurrentPlayer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
        SwingUtils.drawHealthBar(g2d, player.getX(), player.getY(), 20, 5, player.getHealth());
        g2d.setColor(Color.GREEN);
        g2d.drawString(player.getName(), player.getX(), player.getY() + 30);
    }

    private void drawShell(Graphics2D g2d, AbstractShell pl) {
        g2d.drawImage(pl.getImage(), pl.getX(), pl.getY(), this);
        g2d.setColor(Color.BLUE);
        g2d.drawString(pl.getName() + " " + pl.getID(), pl.getX(), pl.getY() + 30);
        g2d.setColor(Color.YELLOW);
        g2d.drawString(pl.getInfo(), pl.getX(), pl.getY() + 50);
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
            RoomData newRoom = gameMap.currentRoom.getRoomFromDirection(direction);
            sendRoomPacket(direction, newRoom.getId());
            gameMap.currentRoom = new Room(newRoom);
        }
        if (player.hasChangedPosition()) {
            sendPositionUpdate();
        }

        player.move(gameMap.currentRoom.getWalls(),
                gameMap.currentRoom.getDoors(),
                gameMap.currentRoom.getObstacles(),
                gameMap.currentRoom.getDecorations());
        repaint();
    }

    private void sendRoomPacket(DoorDirection nextRoom, int roomID) {
        RoomPacket roomPacket = new RoomPacket();
        roomPacket.direction = nextRoom.toString();
        roomPacket.id = roomID;
        client.sendTCP(roomPacket);
    }

    private void sendPositionUpdate() {
        MoveCharacter msg = new MoveCharacter();
        msg.id = player.getId();
        msg.x = player.getX();
        msg.y = player.getY();
        client.sendTCP(msg);
    }

    private void movePlayerShells(int x, int y) {
        for (AbstractShell sh : shells.values()) {
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

    public void changeRoom(RoomPacket roomPacket) {
        String direction = roomPacket.direction;
        DoorDirection doorDirection = DoorDirection.valueOf(roomPacket.direction);
        RoomData currentRoomData = gameMap.currentRoom.getRoomFromDirection(doorDirection);
        gameMap.currentRoom = new Room(currentRoomData);

        if (direction.equals("LEFT")) {
            player.setCoordinates(400, 250);
        } else if (direction.equals("TOP")) {
            player.setCoordinates(250, 400);
        } else if (direction.equals("RIGHT")) {
            player.setCoordinates(40, 250);
        } else if (direction.equals("BOTTOM")) {
            player.setCoordinates(250, 40);
        }
    }

    public void updateEnemyInfo(String enemyData) {
        String[] arrOfStr = enemyData.substring(4).split(" ");
        int tempID = Integer.parseInt(arrOfStr[0]);
        String tempInfo = arrOfStr[1];
        AbstractShell temp = shells.get(tempID);
        if (temp != null) {
            temp.setInfo(tempInfo);
        }
    }

    public void addEnemies(List<Enemy> enemies) {
        enemies.forEach(e -> shells.put(e.getID(), new EnemyShell(e.getName(), e.getID(), e.getX(), e.getY())));
    }

    public void changeShellPosition(MoveCharacter packet) {
        AbstractShell temp = shells.get(packet.id);
        if (temp != null) {
            temp.setX(packet.x);
            temp.setY(packet.y);
        }
    }

    public void deletePlayerShell(int id) {
        shells.remove(id);
    }

}