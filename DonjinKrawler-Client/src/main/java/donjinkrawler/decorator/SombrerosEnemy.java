package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;
import donjinkrawler.ShellType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SombrerosEnemy extends EnemyClothingDecorator {

    public SombrerosEnemy(AbstractShellInterface source) {
        super(source);
    }

    @Override
    public Map<String, ImageIcon> addClothing() {
        try {
            InputStream stream = getClass().getResourceAsStream("/sombrero.png");
            ImageIcon ii = new ImageIcon(ImageIO.read(stream));
            Image scaledImage = ii.getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
            ii = new ImageIcon(scaledImage);
            Map<String, ImageIcon> clothes = wrappee.addClothing();
            clothes.put("Sombrero", ii);
            return clothes;
        } catch (Exception ignored) {

        }
        return new HashMap<>();
    }

    @Override
    public ShellType getShellType() {
        return ShellType.ENEMY;
    }
}