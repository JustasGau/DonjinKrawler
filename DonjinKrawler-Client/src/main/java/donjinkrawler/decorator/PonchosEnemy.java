package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;

import javax.swing.*;
import java.util.ArrayList;

public class PonchosEnemy extends EnemyClothingDecorator {

    public PonchosEnemy(AbstractShellInterface source) {
        super(source);
    }

    @Override
    public ArrayList<ImageIcon> addClothing() {

        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("poncho.png").getFile());
        ArrayList<ImageIcon> clothes = wrappee.addClothing();
        clothes.add(ii);
        return clothes;
    }

}