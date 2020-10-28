package krawlercommon.items;

public class ArmorData extends ItemLocationData {

    public ArmorData() {
    }

    public ArmorData(int id, int x, int y) {
        super(id, x, y);
    }

    @Override
    public ArmorData deepCopy() throws CloneNotSupportedException {
        return (ArmorData) super.clone();
    }
}
