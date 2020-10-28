package krawlercommon.items;

public class DamagePotionData extends ItemLocationData {

    public DamagePotionData() {
    }

    public DamagePotionData(int id, int x, int y) {
        super(id, x, y);
    }

    @Override
    public DamagePotionData deepCopy() throws CloneNotSupportedException {
        return (DamagePotionData) super.clone();
    }
}
