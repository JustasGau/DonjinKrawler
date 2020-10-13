package donjinkrawler;

import krawlercommon.PlayerData;
import krawlercommon.enemies.Enemy;
import krawlercommon.packets.MoveCharacter;
import krawlercommon.packets.RoomPacket;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.*;

public class Game extends JPanel implements ActionListener {

    enum DoorDirection {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }

    com.esotericsoftware.kryonet.Client client;
    private final Timer timer;
    private final Player player;
    private GameMap gameMap;
    private final int delay = 10;
    private static final Map<Integer, AbstractShell> shells = new ConcurrentHashMap<>();
    private final int[][] mapGrid;
    //saves states if the map cell is cleared(1) or not (0)
    private final int[][] mapState;

    static class CurrentCell {
        public static int x = 0;
        public static int y = 0;
    }

    private final JLabel label;


    public Game(com.esotericsoftware.kryonet.Client client, JLabel label, Player player, int[][] mapGrid) {
        this.label = label;
        this.player = player;
        this.mapGrid = mapGrid;
        this.mapState = new int[mapGrid.length][mapGrid.length];
        this.mapState[0][0] = 1;
        this.gameMap = new GameMap(mapGrid[CurrentCell.x][CurrentCell.y], doorLocations());
        this.client = client;

        addKeyListener(new Game.TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        timer = new Timer(delay, this);
        timer.start();
    }

    //returns left, top, right, bottom door
    private int[] doorLocations() {
        int[] doors = {0, 0, 0, 0};
        if (CurrentCell.y - 1 >= 0 && mapGrid[CurrentCell.x][CurrentCell.y - 1] != 0) {
            doors[0] = 1;
        }
        if (CurrentCell.x - 1 >= 0 && mapGrid[CurrentCell.x - 1][CurrentCell.y] != 0) {
            doors[1] = 1;
        }
        if (CurrentCell.y + 1 < mapGrid.length && mapGrid[CurrentCell.x][CurrentCell.y + 1] != 0) {
            doors[2] = 1;
        }
        if (CurrentCell.x + 1 < mapGrid.length && mapGrid[CurrentCell.x + 1][CurrentCell.y] != 0) {
            doors[3] = 1;
        }
        return doors;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2dd = (Graphics2D) g;
        gameMap.draw(g);
        drawUnit(g);
        for (AbstractShell pl : shells.values()) {
            g2dd.drawImage(pl.getImage(), pl.getX(), pl.getY(), this);
            g2dd.setColor(Color.BLUE);
            g2dd.drawString(pl.getName() + " " + pl.getID(), pl.getX(), pl.getY() + 30);
            g2dd.setColor(Color.YELLOW);
            g2dd.drawString(pl.getInfo(), pl.getX(), pl.getY() + 50);
        }
    }

    private void drawUnit(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
        g2d.drawString(player.getName(), player.getX(), player.getY() + 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameUpdate();
    }

    private void gameUpdate() {
        DoorDirection nextRoom = gameMap.update(player);
        if (nextRoom != null) {
            mapState[CurrentCell.x][CurrentCell.y] = 1;
            if (nextRoom == DoorDirection.LEFT) {
                CurrentCell.y -= 1;
                player.setCoordinates(400, 250);
                movePlayerShellsToRoom(400, 250);
            } else if (nextRoom == DoorDirection.TOP) {
                CurrentCell.x -= 1;
                player.setCoordinates(250, 400);
                movePlayerShellsToRoom(250, 400);
            } else if (nextRoom == DoorDirection.RIGHT) {
                CurrentCell.y += 1;
                player.setCoordinates(40, 250);
                movePlayerShellsToRoom(40, 250);
            } else if (nextRoom == DoorDirection.BOTTOM) {
                CurrentCell.x += 1;
                player.setCoordinates(250, 40);
                movePlayerShellsToRoom(250, 40);
            }
            sendRoomPacket(nextRoom);
            gameMap = new GameMap(mapGrid[CurrentCell.x][CurrentCell.y], doorLocations());
        }
        if (player.hasChangedPosition()) {
            sendPositionUpdate();
        }

        player.move();
        repaint();
    }

    private void sendRoomPacket(DoorDirection nextRoom) {
        RoomPacket roomPacket = new RoomPacket();
        roomPacket.x = CurrentCell.x;
        roomPacket.y = CurrentCell.y;
        roomPacket.direction = nextRoom.toString();
        client.sendTCP(roomPacket);
    }

    private void sendPositionUpdate() {
        MoveCharacter msg = new MoveCharacter();
        msg.id = player.getId();
        msg.x = player.getX();
        msg.y = player.getY();
        client.sendTCP(msg);
    }

    private void movePlayerShellsToRoom(int x, int y) {
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
        int mapX = roomPacket.x;
        int mapY = roomPacket.y;

        String direction = roomPacket.direction;
        CurrentCell.x = mapX;
        CurrentCell.y = mapY;
        gameMap = new GameMap(mapGrid[mapX][mapY], doorLocations());

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