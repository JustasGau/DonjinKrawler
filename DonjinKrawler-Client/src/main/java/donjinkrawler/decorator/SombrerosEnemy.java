package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class SombrerosEnemy extends EnemyClothingDecorator {

    public SombrerosEnemy(AbstractShellInterface source) {
        super(source);
    }

    @Override
    public Map<String, ImageIcon> addClothing() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("sombrero.png").getFile());
        Image scaledImage = ii.getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
        ii = new ImageIcon(scaledImage);
        Map<String, ImageIcon> clothes = wrappee.addClothing();
        clothes.put("Sombrero", ii);
        return clothes;
    }
}