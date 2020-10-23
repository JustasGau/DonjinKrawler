package donjinkrawler.items;

import donjinkrawler.items.tiers.Tier;

public abstract class Armory extends BaseItem {

    protected Tier tier;

    public Armory(Tier tier) {
        this.tier = tier;
    }
}
