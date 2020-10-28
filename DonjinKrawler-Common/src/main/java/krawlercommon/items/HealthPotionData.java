package krawlercommon.items;

public class HealthPotionData extends ItemLocationData {

    public HealthPotionData() {
    }

    public HealthPotionData(int id, int x, int y) {
        super(id, x, y);
    }

    @Override
    public HealthPotionData deepCopy() throws CloneNotSupportedException {
        return (HealthPotionData) super.clone();
    }
}
