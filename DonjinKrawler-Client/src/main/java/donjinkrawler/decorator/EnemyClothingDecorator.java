package donjinkrawler.decorator;

import donjinkrawler.EnemyShell;

import javax.swing.*;

public abstract class EnemyClothingDecorator extends EnemyShell implements Clothing {

    private EnemyShell wrappee;

    EnemyClothingDecorator(EnemyShell source) {
        super();
        this.wrappee = source;
    }

    @Override
    protected void loadImage() {

    }

    @Override
    public void addClothing(ImageIcon item) {
        wrappee.addClothing(item);
    }

}