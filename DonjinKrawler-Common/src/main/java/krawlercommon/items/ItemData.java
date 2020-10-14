package krawlercommon.items;

public abstract class ItemData {
    protected int id;
    protected int x;
    protected int y;

    public ItemData() {

    }

    public ItemData(int id, int x, int y) {
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
}
