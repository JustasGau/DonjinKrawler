package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;
import donjinkrawler.ShellType;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MaracasEnemy extends EnemyClothingDecorator {

    public MaracasEnemy(AbstractShellInterface source) {
        super(source);
    }

    @Override
    public Map<String, ImageIcon> addClothing() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("maracas.png").getFile());
        Image scaledImage = ii.getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
        ii = new ImageIcon(scaledImage);
        Map<String, ImageIcon> clothes = wrappee.addClothing();
        clothes.put("Maracas", ii);
        return clothes;
    }

    @Override
    public ShellType getShellType() {
        return ShellType.ENEMY;
    }
}