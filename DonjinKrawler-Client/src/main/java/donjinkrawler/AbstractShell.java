package donjinkrawler;

import donjinkrawler.decorator.clothes.Clothing;
import donjinkrawler.visitor.ClothingVisitor;

import java.awt.*;
import java.util.List;

public abstract class AbstractShell implements AbstractShellInterface {

    protected int x = 250;
    protected int y = 250;
    protected String name;
    protected Image image;
    protected Image attackIMG;
    protected boolean attack;
    protected double health = 100;
    private String info;

    public abstract void loadImage();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public int getID() {
        return -1;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double val) {
        health = val;
    }

    public boolean isAttacking() {
        return this.attack;
    }

    public void setIsAttacking(boolean attack) {
        this.attack = attack;
    }

}
