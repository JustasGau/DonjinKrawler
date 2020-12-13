package donjinkrawler.items.tiers;

import java.util.Random;

public class LegendaryTier implements Tier {

    private Random random = new Random();

    @Override
    public String getName() {
        return "Legendary";
    }

    @Override
    public double getDamageBonus() {
        return (getLastDigitOfTime() + 1) * getRandomDoubleInRange(3, 4);
    }

    @Override
    public double getSpeedBonus() {
        return (getLastDigitOfTime() + 1) * getRandomDoubleInRange(2, 3);
    }

    @Override
    public double getDefenseBonus() {
        return (getLastDigitOfTime() + 1) * getRandomDoubleInRange(3, 4);
    }

    @Override
    public double getHpBonus() {
        return (getLastDigitOfTime() + 1) * getRandomDoubleInRange(2, 3);
    }

    @Override
    public double getManaBonus() {
        return (getLastDigitOfTime() + 1) * getRandomDoubleInRange(2, 3);
    }

    private double getRandomDoubleInRange(double min, double max) {
        double value = min + (max - min) * random.nextDouble();
        return Math.round(value * 100.0) / 100.0;
    }

    public int getLastDigitOfTime() {
        return (int) ((System.currentTimeMillis() / 1000L) % 10);
    }
}
