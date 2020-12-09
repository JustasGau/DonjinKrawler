package donjinkrawler.items;

import donjinkrawler.items.tiers.Tier;
import donjinkrawler.visitor.ItemVisitor;

public abstract class Armory extends BaseItem {

    protected Tier tier;

    public Armory(Tier tier) {
        this.tier = tier;
    }

    protected double roundStat(double stat) {
        return (double) Math.round(stat * 100.0) / 100.0;
    }

    @Override
    public Armory clone() throws CloneNotSupportedException {
        Armory armory = (Armory) super.clone();
        armory.tier = this.tier;
        return armory;
    }

    @Override
    public abstract void accept(ItemVisitor visitor);

    @Override
    public abstract Armory deepCopy() throws CloneNotSupportedException;
}
