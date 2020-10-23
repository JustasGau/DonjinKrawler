package donjinkrawler.items;

import donjinkrawler.items.tiers.Tier;
import krawlercommon.items.WeaponData;

public class Weapon extends Armory {

    public Weapon(WeaponData weaponData, Tier tier) {
        super(tier);
        this.itemData = weaponData;
        loadImage("items/weapon.png");
    }

    @Override
    public WeaponData getData() {
        return (WeaponData) itemData;
    }
}
