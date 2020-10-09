package donjinkrawler;

import krawlercommon.enemies.Enemy;
import krawlercommon.packets.MessagePacket;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
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
    private int mouseX = 0;
    private int mouseY = 0;
    private double angle;
    private double rotate;
    private int counter = 0;
    private String lastPos = "";
    private static final Set<AbstractShell> shells = new CopyOnWriteArraySet<>();
    private int[][] mapGrid;
    //saves states if the map cell is cleared(1) or not (0)
    private int[][] mapState;

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
        for (AbstractShell pl : shells) {
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
            } else if (nextRoom == DoorDirection.TOP) {
                CurrentCell.x -= 1;
                player.setCoordinates(250, 400);
            } else if (nextRoom == DoorDirection.RIGHT) {
                CurrentCell.y += 1;
                player.setCoordinates(40, 250);
            } else if (nextRoom == DoorDirection.BOTTOM) {
                CurrentCell.x += 1;
                player.setCoordinates(250, 40);
            }
            MessagePacket messagePacket = new MessagePacket();
            messagePacket.message = "ROM " + CurrentCell.x + " " + CurrentCell.y + " " + nextRoom;
            client.sendTCP(messagePacket);
            gameMap = new GameMap(mapGrid[CurrentCell.x][CurrentCell.y], doorLocations());
        }
        if (player.hasChangedPosition()) {
            MessagePacket messagePacket = new MessagePacket();
            messagePacket.message = "POZ " + player.getX() + " " + player.getY();
            client.sendTCP(messagePacket);
        }
//        if (counter == 100 /*&& !lastPos.equals(player.getX() + " " + player.getY())*/) {
//            MessagePacket messagePacket = new MessagePacket();
//            messagePacket.message = "POZ " + player.getX() + " " + player.getY();
//            client.sendTCP(messagePacket);
//            counter = 0;
//            lastPos = player.getX() + " " + player.getY();
//        }
        counter++;

        player.move();
        repaint();
    }

    private AbstractShell findPlayerInMap(String name) {
        for (AbstractShell pl : shells) {
            if (pl.getName().equals(name)) {
                return pl;
            }
        }
        return null;
    }

    private AbstractShell findEnemyInMap(int id) {
        for (AbstractShell enemy : shells) {
            if (enemy.getID() == id) {
                return enemy;
            }
        }
        return null;
    }

    public void addPlayerShell(String name) {
        shells.add(new PlayerShell(name));
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

    public void changeRoom(String response) {
        String data = response.substring(4);
        String[] arrOfStr = data.split(" ", 0);

        int mapX = Integer.parseInt(arrOfStr[0]);
        int mapY = Integer.parseInt(arrOfStr[1]);

        String direction = arrOfStr[2];
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

    public void updateEnemyInfo(String response) {
        String data = response.substring(4);
        String[] arrOfStr = data.split(" ", 0);
        int tempID = Integer.parseInt(arrOfStr[0]);
        String tempInfo = arrOfStr[1];
        if (tempInfo == null) {
            tempInfo = "no info";
        }
        AbstractShell temp = findEnemyInMap(tempID);
        if (temp != null) {
            temp.setInfo(tempInfo);
        }
    }

    public void addEnemies(List<Enemy> enemies) {
        shells.addAll(
                enemies.stream()
                        .map(e -> new EnemyShell(e.getName(), e.getID(), e.getX(), e.getY()))
                        .collect(Collectors.toList())
        );
    }

    public void changeShellPosition(String response) {
        String data = response.substring(4);
        String[] arrOfStr = data.split(" ", 0);
        if (arrOfStr.length == 3) {
            AbstractShell temp = findPlayerInMap(arrOfStr[2]);
            if (temp != null) {
                temp.setX(Integer.parseInt(arrOfStr[0]));
                temp.setY(Integer.parseInt(arrOfStr[1]));
            }
        }
    }

    public void deletePlayerShell(String response) {
        AbstractShell temp = findPlayerInMap(response);
        shells.remove(temp);
    }

}