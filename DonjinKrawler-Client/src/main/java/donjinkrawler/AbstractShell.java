package donjinkrawler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

abstract public class AbstractShell implements AbstractShellInterface {

    protected int dx;
    protected int dy;
    protected int x = 250;
    protected int y = 250;
    protected String name;
    protected Image image;
    protected Image attackIMG;
    protected boolean attack;
    protected double health = 100;

    abstract public void loadImage();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public int getID() { return -1; }

    public String getInfo() { return ""; }

    public void setInfo(String info){};

    public abstract void drawClothes(Graphics2D g2d, Game game);

    public abstract ArrayList<ImageIcon> addClothing();

    public double getHealth() {
        return health;
    }

    public void setHealth(double val) {
        health = val;
    }

    abstract public Image getAttackImage();

    public void isAttacing(boolean attack) {
        this.attack = attack;
    }

}
