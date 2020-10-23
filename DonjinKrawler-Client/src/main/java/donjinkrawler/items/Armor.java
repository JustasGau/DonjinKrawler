package donjinkrawler.items;

import donjinkrawler.items.tiers.Tier;
import krawlercommon.items.ArmorData;

public class Armor extends Armory {

    public Armor(ArmorData itemData, Tier tier) {
        super(tier);
        this.itemData = itemData;
        super.loadImage("items/armor.png");
    }

    @Override
    public ArmorData getData() {
        return (ArmorData) itemData;
    }
}
