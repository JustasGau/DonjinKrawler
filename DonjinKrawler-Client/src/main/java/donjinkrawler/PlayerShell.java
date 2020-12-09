package donjinkrawler;

import donjinkrawler.decorator.clothes.Clothing;
import donjinkrawler.logging.LoggerSingleton;
import donjinkrawler.visitor.ClothingVisitor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.List;

public class PlayerShell extends AbstractShell {
    private LoggerSingleton logger = LoggerSingleton.getInstance();

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
        } catch (Exception e) {
            logger.error("Failed loading image");
            logger.error(e);
        }
    }

    @Override
    public void drawClothes(ClothingVisitor visitor) {
        // Todo: implement
    }

    @Override
    public void damage(double damage) {
        this.health = this.health - damage;
    }

    @Override
    public List<Clothing> addClothing() {
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