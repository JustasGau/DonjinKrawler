package donjinkrawler.items;

import krawlercommon.items.HealthPotionData;

public class HealthPotion extends BaseItem {

    public HealthPotion(HealthPotionData data) {
        this.itemData = data;
        loadImage("items/health_potion.png");
    }

    @Override
    public HealthPotionData getData() {
        return (HealthPotionData) itemData;
    }

    @Override
    public String getDescription() {
        return "Restore some HP.";
    }
}
