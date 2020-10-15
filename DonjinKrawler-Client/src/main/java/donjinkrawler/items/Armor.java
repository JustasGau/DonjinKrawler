package donjinkrawler.items;

import krawlercommon.items.ArmorData;

public class Armor extends BaseItem {

    public Armor(ArmorData itemData) {
        this.itemData = itemData;
        super.loadImage("items/armor.png");
    }

    @Override
    public ArmorData getData() {
        return (ArmorData) itemData;
    }
}
