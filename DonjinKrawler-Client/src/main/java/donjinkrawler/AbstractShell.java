package donjinkrawler;

import javax.swing.*;
import java.awt.*;

abstract public class AbstractShell {

    private int dx;
    private int dy;
    private int x = 250;
    private int y = 250;
    private String name;
    private Image image;

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

}
