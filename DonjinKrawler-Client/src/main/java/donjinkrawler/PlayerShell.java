package donjinkrawler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerShell extends AbstractShell {

    public PlayerShell(String name) {
        this.name = name;
        loadImage();
    }

    public void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        image = ii.getImage();
    }

    @Override
    public void drawClothes(Graphics2D g2d, Game game) {

    }

    @Override
    public ArrayList<ImageIcon> addClothing() {
        return null;
    }
}