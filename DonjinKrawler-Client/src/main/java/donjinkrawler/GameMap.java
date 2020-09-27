package donjinkrawler;

import javax.swing.*;
import java.awt.*;

public class GameMap {

    private final int x = 0;
    private final int y = 0;
    private Image image;

    public GameMap() {
        loadImage();
    }

    public Image getImage() {
        return image;
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("x.jpg").getFile());
        image = ii.getImage();
    }
}
