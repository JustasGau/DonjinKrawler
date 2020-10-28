package krawlercommon.items;

public class SpeedPotionData extends ItemLocationData {

    public SpeedPotionData() {
    }

    public SpeedPotionData(int id, int x, int y) {
        super(id, x, y);
    }

    @Override
    public SpeedPotionData deepCopy() throws CloneNotSupportedException {
        return (SpeedPotionData) super.clone();
    }
}
