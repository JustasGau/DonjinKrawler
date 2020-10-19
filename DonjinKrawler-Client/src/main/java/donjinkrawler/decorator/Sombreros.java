package donjinkrawler.decorator;

import donjinkrawler.EnemyShell;

import javax.swing.*;

public class Sombreros extends EnemyClothingDecorator {

    public Sombreros(EnemyShell source) {
        super(source);
    }

    @Override
    public void addClothing(ImageIcon item) {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("sombrero.png").getFile());
        super.addClothing(ii);
    }
}