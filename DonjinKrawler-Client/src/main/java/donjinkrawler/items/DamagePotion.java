package donjinkrawler.items;

import krawlercommon.items.DamagePotionData;

public class DamagePotion extends BaseItem {

    public DamagePotion(DamagePotionData data) {
        this.itemData = data;
        super.loadImage("items/damage_potion.png");
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


}
