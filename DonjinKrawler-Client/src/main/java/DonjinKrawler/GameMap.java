package main.java.DonjinKrawler;

import javax.swing.*;
import java.awt.*;

public class GameMap {

    private int x = 0;
    private int y = 0;
    private Image image;

    public GameMap() {
        loadImage();
    }

    public Image getImage() {
        return image;
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("resources/x.jpg").getFile());
        image = ii.getImage();
    }
}
