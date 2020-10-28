package krawlercommon.items;

import donjinkrawler.prototype.KrawlerCloneable;

public abstract class ItemLocationData implements KrawlerCloneable {
    protected int id;
    protected int x;
    protected int y;

    public ItemLocationData() {

    }

    public ItemLocationData(int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
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

    public int getId() {
        return this.id;
    }

    @Override
    public ItemLocationData clone() throws CloneNotSupportedException {
        return (ItemLocationData) super.clone();
    }
}
