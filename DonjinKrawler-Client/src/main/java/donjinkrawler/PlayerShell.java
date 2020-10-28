package donjinkrawler;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PlayerShell extends AbstractShell {

    public PlayerShell(String name) {
        this.name = name;
        loadImage();
    }

    public void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        image = ii.getImage();
        ii = new ImageIcon(ClassLoader.getSystemResource("attack.png").getFile());
        attackIMG = ii.getImage();
    }

    @Override
    public void drawClothes(Graphics2D g2d, Game game) {

    }

    @Override
    public void damage(double damage) {

    }


    @Override
    public Map<String, ImageIcon> addClothing() {
        return null;
    }

    @Override
    public Image getAttackImage() {
        if (attack) {
            attack = false;
            return attackIMG;
        } else {
            return null;
        }
    }
}