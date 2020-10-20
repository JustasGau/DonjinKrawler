package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;

import javax.swing.*;
import java.util.ArrayList;

public class SombrerosEnemy extends EnemyClothingDecorator {

    public SombrerosEnemy(AbstractShellInterface source) {
        super(source);
    }

    @Override
    public ArrayList<ImageIcon> addClothing() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("sombrero.png").getFile());
        ArrayList<ImageIcon> clothes = wrappee.addClothing();
        clothes.add(ii);
        return clothes;
    }
}