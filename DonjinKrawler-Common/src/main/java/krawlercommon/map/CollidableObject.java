package krawlercommon.map;

import krawlercommon.KrawlerCloneable;

public class CollidableObject implements KrawlerCloneable {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public CollidableObject() {

    }

    public CollidableObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTopX() {
        return x;
    }

    public int getTopY() {
        return y;
    }

    public int getBotX() {
        return x + width;
    }

    public int getBotY() {
        return y + height;
    }

    @Override
    public CollidableObject clone() throws CloneNotSupportedException {
        return (CollidableObject) super.clone();
    }

    @Override
    public CollidableObject deepCopy() throws CloneNotSupportedException {
        return this.clone();
    }
}
