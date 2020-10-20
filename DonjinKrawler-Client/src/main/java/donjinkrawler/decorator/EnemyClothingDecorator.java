package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;
import donjinkrawler.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class EnemyClothingDecorator implements AbstractShellInterface {

    protected AbstractShellInterface wrappee;
    EnemyClothingDecorator(AbstractShellInterface source) {
        this.wrappee = source;
    }

    @Override
    public ArrayList<ImageIcon> addClothing() {
        return wrappee.addClothing();
    }

    @Override
    public int getX() {
        return wrappee.getX();
    }

    @Override
    public int getY() {
        return wrappee.getY();
    }

    @Override
    public String getName() {
        return wrappee.getName();
    }

    @Override
    public void setX(int x) {
        wrappee.setX(x);
    }

    @Override
    public void setY(int y) {
        wrappee.setY(y);
    }

    @Override
    public Image getImage() {
        return wrappee.getImage();
    }

    @Override
    public int getID() {
        return wrappee.getID();
    }

    @Override
    public String getInfo() {
        return wrappee.getInfo();
    }

    @Override
    public void setInfo(String info) {
        wrappee.setInfo(info);
    }

    @Override
    public void drawClothes(Graphics2D g2d, Game game) {
        wrappee.drawClothes(g2d, game);
    }

}