package donjinkrawler.decorator;

import donjinkrawler.EnemyShell;

import javax.swing.*;
import java.util.ArrayList;

public class Ponchos extends EnemyClothingDecorator {

    public Ponchos(EnemyShell source) {
        super(source);
    }

    @Override
    public ArrayList<ImageIcon> addClothing() {

        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("poncho.png").getFile());
        ArrayList<ImageIcon> clothes = wrappee.addClothing();
        clothes.add(ii);
        System.out.println("ad poncho" + clothes.size());
        return clothes;
    }
}