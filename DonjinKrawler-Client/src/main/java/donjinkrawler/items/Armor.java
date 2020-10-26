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

        this.defense = this.roundStat(defense + this.tier.getDefenseBonus());
        this.hp      = this.roundStat(hp + this.tier.getHpBonus());
        this.mana    = this.roundStat(mana + this.tier.getManaBonus());
    }

    @Override
    public ArmorData getData() {
        return (ArmorData) itemData;
    }

    @Override
    public String getDescription() {
        return "Tier: " + this.tier.getName() + "\n" +
                "Defense: " + this.getDefense() + "\n" +
                "HP: " + this.getHp() + "\n" +
                "Mana: " + this.getMana();
    }

    public double getDefense() {
        return this.defense;
    }

    public double getHp() {
        return this.hp ;
    }

    public double getMana() {
        return this.mana;
    }
}
