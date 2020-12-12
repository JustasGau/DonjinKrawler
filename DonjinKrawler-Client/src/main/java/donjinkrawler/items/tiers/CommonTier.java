package donjinkrawler.items.tiers;

import java.util.Random;

public class CommonTier implements Tier {

    private final Random random = new Random();

    @Override
    public String getName() {
        return "Common";
    }

    @Override
    public double getDamageBonus() {
        return this.getRandomDoubleInRange(1, 5);
    }

    @Override
    public double getSpeedBonus() {
        return this.getRandomDoubleInRange(1, 3);
    }

    @Override
    public double getDefenseBonus() {
        return this.getRandomDoubleInRange(1, 10);
    }

    @Override
    public double getHpBonus() {
        return this.getRandomDoubleInRange(0, 20);
    }

    @Override
    public double getManaBonus() {
        return this.getRandomDoubleInRange(3, 6);
    }

    private double getRandomDoubleInRange(double min, double max) {
        double value = min + (max - min) * random.nextDouble();
        return Math.round(value * 100.0) / 100.0;
    }
}
