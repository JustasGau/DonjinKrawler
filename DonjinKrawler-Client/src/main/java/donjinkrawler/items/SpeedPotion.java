package donjinkrawler.items;

import donjinkrawler.visitor.ItemVisitor;
import krawlercommon.items.SpeedPotionData;

public class SpeedPotion extends BaseItem {

    private double multiplier;

    public SpeedPotion(SpeedPotionData data) {
        this(data, 1.5);
    }

    public SpeedPotion(SpeedPotionData data, double multiplier) {
        this.itemData = data;
        super.loadImage("items/speed_potion.png");
        this.multiplier = multiplier;
    }

    @Override
    public SpeedPotionData getData() {
        return (SpeedPotionData) itemData;
    }

    @Override
    public String getDescription() {
        return "Adds some speed buff.";
    }

    @Override
    public void accept(ItemVisitor v) {
        v.visit(this);
    }

    @Override
    public SpeedPotion deepCopy() throws CloneNotSupportedException {
        return (SpeedPotion) super.clone();
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}
