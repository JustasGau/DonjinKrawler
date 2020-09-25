package main.java.DonjinKrawler;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.*;

public class Map extends JPanel implements ActionListener{

    private Timer timer;
    private Player player;
    private final int DELAY = 10;
    private int mouseX = 0;
    private int mouseY = 0;
    private double angle;
    private double rotate;
    private int counter = 0;
    private static Set<PlayerShell> shells = new HashSet<>();

    private Scanner in;
    private PrintWriter out;
    private JLabel label;


    public Map(Scanner in, PrintWriter out, JLabel label, Player player) {
        this.in = in;
        this.out = out;
        this.label = label;
        this.player = player;

        if (this.in != null) {
            new ServerReader(in, label);
        }
        addMouseMotionListener(new MyMouseAdapter());

        addKeyListener(new Map.TAdapter());
        setBackground(Color.black);
        setFocusable(true);

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
        drawUnit(g);
        Graphics2D g2dd = (Graphics2D) g;
        g2dd.setColor(Color.BLUE);
        for (PlayerShell pl : shells) {

//            g2dd.rotate(Math.toRadians( rotate )*(-1), pl.getX(), pl.getY());
            g2dd.drawImage(pl.getImage(), pl.getX(), pl.getY(), this);
            g2dd.drawString(pl.getName(), pl.getX(), pl.getY() + 30);
        }
    }

    private void drawUnit(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        //g2d.rotate(Math.toRadians( rotate )*(-1), player.getX(), player.getY());
        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
        g2d.drawString(player.getName(), player.getX(), player.getY() + 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameUpdate();
    }

    private void gameUpdate() {
        if (player.getChangedPOS() == true) {
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
            if(pl.getName().equals(name))
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
            angle = Math.atan2( mouseX , mouseY );
            rotate = angle * ( 180 / Math.PI );
        }
    }

    public class ServerReader extends Thread {

        Scanner in;
        JLabel label;
        public ServerReader(Scanner in, JLabel label){
            this.in = in;
            this.label = label;
            start();
        }

        public void run() {
            while(true){
                if (in.hasNextLine()) {
                    String response = in.nextLine();
                    System.out.println(response);
                    if (response.startsWith("CRT")) {
                        System.out.println(response);
                        String newPlayerName = response.substring(4);
                        shells.add(new PlayerShell(newPlayerName));

                    } else if (response.startsWith("MSG")) {
                        label.setText(response.substring(4));
                    } else if (response.startsWith("POZ")) {
                        String data = response.substring(4);
                        String[] arrOfStr = data.split(" ", 0);
                        if(arrOfStr.length == 3) {
                            PlayerShell temp = findPlayerInMap(arrOfStr[2]);
                            temp.setX(Integer.parseInt(arrOfStr[0]));
                            temp.setY(Integer.parseInt(arrOfStr[1]));
                        }
                    } else if (response.startsWith("DLT")) {
                        System.out.println(response);
                        String discPlayerName = response.substring(4);
                        PlayerShell temp = findPlayerInMap(discPlayerName);
                        shells.remove(temp);
                    }
                }
            }
        }
    }

}