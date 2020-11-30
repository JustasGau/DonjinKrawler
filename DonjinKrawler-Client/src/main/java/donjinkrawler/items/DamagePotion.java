package donjinkrawler.items;

import krawlercommon.items.DamagePotionData;

public class DamagePotion extends BaseItem {

    private double multiplier;

    public DamagePotion(DamagePotionData data) {
        this(data, 1.5);
    }

    public DamagePotion(DamagePotionData data, double multiplier) {
        this.itemData = data;
        super.loadImage("items/damage_potion_distinct.png");
        this.multiplier = multiplier;
    }

    @Override
    public DamagePotionData getData() {
        return (DamagePotionData) itemData;
    }

    @Override
    public String getDescription() {
        return "Adds some damage buff.";
    }

    @Override
    public DamagePotion deepCopy() throws CloneNotSupportedException {
        return (DamagePotion) super.clone();
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}
