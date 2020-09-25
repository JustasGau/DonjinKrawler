package main.java.DonjinKrawler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerShell {

    private int dx;
    private int dy;
    private int x = 40;
    private int y = 60;
    private String name;
    private Image image;

    public PlayerShell(String name) {
        this.name = name;
        loadImage();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("resources/craft.png").getFile());
        image = ii.getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }
}