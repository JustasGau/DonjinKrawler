package donjinkrawler;

import donjinkrawler.decorator.Clothing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

abstract public class AbstractShell {

    protected int dx;
    protected int dy;
    protected int x = 250;
    protected int y = 250;
    protected String name;
    protected Image image;

    abstract protected void loadImage();

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


}
