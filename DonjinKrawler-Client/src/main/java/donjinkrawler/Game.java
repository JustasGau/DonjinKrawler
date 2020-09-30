package donjinkrawler;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.*;

public class Game extends JPanel implements ActionListener {

    enum DoorDirection {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }

    private final Timer timer;
    private final Player player;
    private GameMap gameMap;
    private final int delay = 10;
    private int mouseX = 0;
    private int mouseY = 0;
    private double angle;
    private double rotate;
    private int counter = 0;
    private static final Set<PlayerShell> shells = new HashSet<>();
    private int[][] mapGrid;
    //saves states if the map cell is cleared(1) or not (0)
    private int[][] mapState;

    static class CurrentCell {
        public static int x = 0;
        public static int y = 0;
    }

    private final Scanner in;
    private final PrintWriter out;
    private final JLabel label;


    public Game(Scanner in, PrintWriter out, JLabel label, Player player, int[][] mapGrid) {
        this.in = in;
        this.out = out;
        this.label = label;
        this.player = player;
        this.mapGrid = mapGrid;
        this.mapState = new int[mapGrid.length][mapGrid.length];
        this.mapState[0][0] = 1;
        this.gameMap = new GameMap(mapGrid[CurrentCell.x][CurrentCell.y], doorLocations());

        if (this.in != null) {
            new ServerReader(in, label);
        }

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
        g2dd.setColor(Color.BLUE);
        for (PlayerShell pl : shells) {
            g2dd.drawImage(pl.getImage(), pl.getX(), pl.getY(), this);
            g2dd.drawString(pl.getName(), pl.getX(), pl.getY() + 30);
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
            out.println("ROM " + CurrentCell.x + " " + CurrentCell.y + " " + nextRoom);
            gameMap = new GameMap(mapGrid[CurrentCell.x][CurrentCell.y], doorLocations());
        }
        if (player.hasChangedPosition()) {
            out.println("POZ " + player.getX() + " " + player.getY());
        }
        if (counter == 100) {
            out.println("POZ " + player.getX() + " " + player.getY());
            counter = 0;
        }
        counter++;

        player.move();
        repaint();
    }

    private PlayerShell findPlayerInMap(String name) {
        for (PlayerShell pl : shells) {
            if (pl.getName().equals(name)) {
                return pl;
            }
        }
        return null;
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

    public class ServerReader extends Thread {

        Scanner in;
        JLabel label;

        public ServerReader(Scanner in, JLabel label) {
            this.in = in;
            this.label = label;
            start();
        }

        public void run() {
            while (true) {
                if (in.hasNextLine()) {
                    String response = in.nextLine();
                    if (response.startsWith("CRT")) {
                        String newPlayerName = response.substring(4);
                        shells.add(new PlayerShell(newPlayerName));
                    } else if (response.startsWith("MSG")) {
                        label.setText(response.substring(4));
                    } else if (response.startsWith("POZ")) {
                        String data = response.substring(4);
                        String[] arrOfStr = data.split(" ", 0);
                        if (arrOfStr.length == 3) {
                            PlayerShell temp = findPlayerInMap(arrOfStr[2]);
                            temp.setX(Integer.parseInt(arrOfStr[0]));
                            temp.setY(Integer.parseInt(arrOfStr[1]));
                        }
                    } else if (response.startsWith("DLT")) {
                        String discPlayerName = response.substring(4);
                        PlayerShell temp = findPlayerInMap(discPlayerName);
                        shells.remove(temp);
                    } else if (response.startsWith("ROM")) {
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
                }
            }
        }
    }

}