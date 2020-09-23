package main.java.DonjinKrawler;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.*;

public class GameMap extends JPanel implements ActionListener {

    private Timer timer;
    private Player player;
    private final int DELAY = 10;
    private int mouseX = 0;
    private int mouseY = 0;
    private double angle;
    private double rotate;
    private int stepMax = 1;
    private int currStep = 0;
    private static Set<PlayerShell> shells = new HashSet<>();

    Scanner in;
    PrintWriter out;
    JLabel label;

    public GameMap(Scanner in, PrintWriter out, JLabel label) {
        this.in = in;
        this.out = out;
        this.label = label;

        if (this.in != null) {
            new ServerReader(in, label);
        }
        addMouseMotionListener(new MyMouseAdapter());
        initMap();
    }

    private void initMap() {
        addKeyListener(new GameMap.TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        player = new Player(-1);

        timer = new Timer(DELAY, this);
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

//        g2d.rotate(Math.toRadians( rotate )*(-1), player.getX(), player.getY());
        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);

        for (PlayerShell pl : shells) {
            Graphics2D g2dd = (Graphics2D) g;

//            g2dd.rotate(Math.toRadians( rotate )*(-1), pl.getX(), pl.getY());
            g2dd.drawImage(pl.getImage(), pl.getX(), pl.getY(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameUpdate();
    }

    private void gameUpdate() {
        if (currStep == stepMax && out != null) {
            out.println("POZ " + player.getX() + " " + player.getY());
            currStep = 0;
        }
        currStep++;
        player.move();
        repaint();
    }

    private PlayerShell findPlayerInMap(int id) {
        for (PlayerShell pl : shells) {
            if (pl.getId() == id)
                return pl;
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

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX() - player.getX();
            mouseY = e.getY() - player.getY();
            angle = Math.atan2(mouseX, mouseY);
            rotate = angle * (180 / Math.PI);
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
//                    System.out.println(response);
                    if (response.startsWith("CRT")) {
                        int newPlayerID = Integer.parseInt(response.substring(4));
                        shells.add(new PlayerShell(newPlayerID));

                    } else if (response.startsWith("MSG")) {
                        label.setText(response.substring(4));
                    } else if (response.startsWith("POZ")) {
                        String data = response.substring(4);
                        String[] arrOfStr = data.split(" ", 0);
                        if (arrOfStr.length == 3) {
                            PlayerShell temp = findPlayerInMap(Integer.parseInt(arrOfStr[2]));
                            if (temp != null) {
                                temp.setX(Integer.parseInt(arrOfStr[0]));
                                temp.setY(Integer.parseInt(arrOfStr[1]));
                            }
                        }

                    } else if (response.startsWith("DLT")) {
                        int discPlayerId = Integer.parseInt(response.substring(4));
                        PlayerShell temp = findPlayerInMap(discPlayerId);
                        if (temp != null) {
                            shells.remove(temp);
                        }
                    }
                }
            }
        }
    }

}