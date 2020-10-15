package donjinkrawler.items;

import krawlercommon.items.WeaponData;

public class Weapon extends BaseItem {

    public Weapon(WeaponData weaponData) {
        this.itemData = weaponData;
        loadImage("items/weapon.png");
    }

    @Override
    public WeaponData getData() {
        return (WeaponData) itemData;
    }
}
