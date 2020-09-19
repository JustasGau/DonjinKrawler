package main.java.DonjinKrawler;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.*;

public class Map extends JPanel implements ActionListener{

    private Timer timer;
    private Player player;
    private final int DELAY = 10;
    private int mouseX = 0;
    private int mouseY = 0;
    private double angle;
    private double rotate;
    private int spammer = 0;

    Scanner in;
    PrintWriter out;
    JLabel label;


    public Map(Scanner in, PrintWriter out, JLabel label) {
        this.in = in;
        this.out = out;
        this.label = label;
        addMouseMotionListener(new MyMouseAdapter());
        initMap();
    }

    private void initMap() {

        addKeyListener(new Map.TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        player = new Player();

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

        g2d.rotate(Math.toRadians( rotate )*(-1), player.getX(), player.getY());
        g2d.drawImage(player.getImage(), player.getX(),
                player.getY(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameUpdate();
    }

    private void gameUpdate() {
        System.out.println("update");
        if( spammer == 100){
            System.out.println("wtf");
            out.println(player.getX());
            spammer = 0;
        }
        spammer++;
        if(in != null) {
            System.out.println("update 2");
            String response = "";
            if (in.hasNextLine()) {
                System.out.println("update 3");
                response = in.nextLine();
                System.out.println(response);
                label.setText(response);
            }
            System.out.println("update2.2");
        }
        System.out.println("update 4");
        player.move();
        repaint();
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

}