package donjinkrawler.items;

import donjinkrawler.visitor.ItemVisitor;
import krawlercommon.items.HealthPotionData;

public class HealthPotion extends BaseItem {

    private double value;

    public HealthPotion(HealthPotionData data) {
        this.itemData = data;
        this.value = 20;
        loadImage("items/health_potion.png");
    }

    @Override
    public HealthPotionData getData() {
        return (HealthPotionData) itemData;
    }

    @Override
    public String getDescription() {
        return "Restore some HP.";
    }

    @Override
    public void accept(ItemVisitor v) {
        v.visit(this);
    }

    @Override
    public HealthPotion deepCopy() throws CloneNotSupportedException {
        return (HealthPotion) super.clone();
    }

    public double getValue() {
        return value;
    }
}
