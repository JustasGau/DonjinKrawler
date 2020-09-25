package main.java.DonjinKrawler;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Player {

    private int dx;
    private int dy;
    private int x = 40;
    private int y = 60;
    private String name;
    private Image image;
    private Boolean changedPOS = false;

    public Player(String name) {
        this.name = name;
        loadImage();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("resources/craft.png").getFile());
        image = ii.getImage();
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Boolean getChangedPOS() {
        return changedPOS;
    }

    public void setChangedPOS(Boolean val) { this.changedPOS = val; }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public void keyPressed(KeyEvent e) {
        changedPOS = true;
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        changedPOS = false;
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}