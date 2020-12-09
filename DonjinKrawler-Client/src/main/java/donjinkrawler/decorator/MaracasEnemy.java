package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;
import donjinkrawler.ShellType;
import donjinkrawler.logging.LoggerSingleton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MaracasEnemy extends EnemyClothingDecorator {
    private final LoggerSingleton logger = LoggerSingleton.getInstance();

    public MaracasEnemy(AbstractShellInterface source) {
        super(source);
    }

    @Override
    public Map<String, ImageIcon> addClothing() {
        try {
            InputStream stream = getClass().getResourceAsStream("/maracas.png");
            ImageIcon ii = new ImageIcon(ImageIO.read(stream));
            Image scaledImage = ii.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
            ii = new ImageIcon(scaledImage);
            Map<String, ImageIcon> clothes = wrappee.addClothing();
            clothes.put("Maracas", ii);
            return clothes;
        } catch (Exception e) {
            logger.error("Failed loading image");
            logger.error(e);
        }
        return new HashMap<>();
    }

    @Override
    public ShellType getShellType() {
        return ShellType.ENEMY;
    }
}