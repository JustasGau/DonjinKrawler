package donjinkrawler.decorator;

import donjinkrawler.EnemyShell;

import javax.swing.*;
import java.util.ArrayList;

public class Sombreros extends EnemyClothingDecorator {

    public Sombreros(EnemyShell source) {
        super(source);
    }

    @Override
    public ArrayList<ImageIcon> addClothing() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("sombrero.png").getFile());
        ArrayList<ImageIcon> clothes = wrappee.addClothing();
        System.out.println("ad sombrero:" + clothes.size());
        clothes.add(ii);
        return clothes;
    }
}