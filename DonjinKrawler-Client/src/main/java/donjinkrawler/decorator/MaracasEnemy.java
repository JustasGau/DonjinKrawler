package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MaracasEnemy extends EnemyClothingDecorator {

    public MaracasEnemy(AbstractShellInterface source) {
        super(source);
    }

    @Override
    public ArrayList<ImageIcon> addClothing() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("maracas.png").getFile());
        ArrayList<ImageIcon> clothes = wrappee.addClothing();
        clothes.add(ii);
        return clothes;
    }
}