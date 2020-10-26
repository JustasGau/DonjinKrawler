package donjinkrawler.items;

import donjinkrawler.items.tiers.Tier;

public abstract class Armory extends BaseItem {

    protected Tier tier;

    public Armory(Tier tier) {
        this.tier = tier;
    }

    protected double roundStat(double stat) {
        return (double) Math.round(stat * 100.0) / 100.0;
    }
}
