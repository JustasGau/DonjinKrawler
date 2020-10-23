package donjinkrawler.items;

import donjinkrawler.items.tiers.Tier;
import krawlercommon.items.WeaponData;

public class Weapon extends Armory {

    protected double damage;
    protected double speed;

    public Weapon(WeaponData weaponData, Tier tier, double damage, double speed) {
        super(tier);
        this.itemData = weaponData;
        loadImage("items/weapon.png");

        this.damage = damage;
        this.speed  = speed;
    }

    @Override
    public WeaponData getData() {
        return (WeaponData) itemData;
    }

    public double getDamage() {
        return damage + this.tier.getDamageBonus();
    }

    public double getSpeed() {
        return speed + this.tier.getSpeedBonus();
    }
}
