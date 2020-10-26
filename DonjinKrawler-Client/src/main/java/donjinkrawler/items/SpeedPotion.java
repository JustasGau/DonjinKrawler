package donjinkrawler.items;

import krawlercommon.items.SpeedPotionData;

public class SpeedPotion extends BaseItem {

    public SpeedPotion(SpeedPotionData data) {
        this.itemData = data;
        super.loadImage("items/speed_potion.png");
    }

    @Override
    public SpeedPotionData getData() {
        return (SpeedPotionData) itemData;
    }

    @Override
    public String getDescription() {
        return "Adds some speed buff.";
    }
}
