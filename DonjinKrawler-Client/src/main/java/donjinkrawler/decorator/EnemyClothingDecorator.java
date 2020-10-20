package donjinkrawler.decorator;

import donjinkrawler.EnemyShell;

import javax.swing.*;
import java.util.ArrayList;

public abstract class EnemyClothingDecorator extends EnemyShell implements Clothing {

    protected EnemyShell wrappee;
    EnemyClothingDecorator(EnemyShell source) {
        super(source.getName(), source.getID(), source.getX(), source.getY());
        this.wrappee = source;
    }

    @Override
    public ArrayList<ImageIcon> addClothing() {
        return wrappee.addClothing();
    }

}