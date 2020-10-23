package donjinkrawler.items;

import donjinkrawler.items.tiers.Tier;
import krawlercommon.items.ArmorData;

public class Armor extends Armory {

    protected double defense;
    protected double hp;
    protected double mana;

    public Armor(ArmorData itemData, Tier tier, double defense, double hp, double mana) {
        super(tier);
        this.itemData = itemData;
        super.loadImage("items/armor.png");

        this.defense = defense;
        this.hp      = hp;
        this.mana    = mana;
    }

    @Override
    public ArmorData getData() {
        return (ArmorData) itemData;
    }

    public double getDefense() {
        return defense + this.tier.getDefenseBonus();
    }

    public double getHp() {
        return hp + this.tier.getHpBonus();
    }

    public double getMana() {
        return mana + this.tier.getManaBonus();
    }
}
