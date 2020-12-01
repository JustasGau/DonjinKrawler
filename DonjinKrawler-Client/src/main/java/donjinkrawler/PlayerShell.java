package donjinkrawler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Map;

public class PlayerShell extends AbstractShell {

    public PlayerShell(String name) {
        this.name = name;
        loadImage();
    }

    public void loadImage() {
        try {
            InputStream stream = getClass().getResourceAsStream("/craft.png");
            ImageIcon ii = new ImageIcon(ImageIO.read(stream));
            image = ii.getImage();
            ii = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/attack.png")));
            attackIMG = ii.getImage();
        } catch (Exception ignored) {

        }
    }

    @Override
    public void drawClothes(Graphics2D g2d, Game game) {
        // Todo: implement
    }

    @Override
    public void damage(double damage) {
        this.health = this.health - damage;
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

    @Override
    public ShellType getShellType() {
        return ShellType.PLAYER;
    }
}