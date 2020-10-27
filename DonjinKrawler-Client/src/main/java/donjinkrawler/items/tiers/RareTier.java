package donjinkrawler.items.tiers;

public class RareTier implements Tier {

    private static final double DAMAGE_MULTIPLIER = 2;
    private static final double SPEED_MULTIPLIER = 1.5;
    private static final double DEFENSE_MULTIPLIER = 2;
    private static final double HP_MULTIPLIER = 2.5;
    private static final double MANA_MULTIPLIER = 1.2;

    @Override
    public String getName() {
        return "Rare";
    }

    @Override
    public double getDamageBonus() {
        return (getLastDigitOfTime() + 1) * DAMAGE_MULTIPLIER;
    }

    @Override
    public double getSpeedBonus() {
        return (getLastDigitOfTime() + 1) * SPEED_MULTIPLIER;
    }

    @Override
    public double getDefenseBonus() {
        return (getLastDigitOfTime() + 1) * DEFENSE_MULTIPLIER;
    }

    @Override
    public double getHpBonus() {
        return (getLastDigitOfTime() + 1) * HP_MULTIPLIER;
    }

    @Override
    public double getManaBonus() {
        return (getLastDigitOfTime() + 1) * MANA_MULTIPLIER;
    }

    public int getLastDigitOfTime() {
        return (int) ((System.currentTimeMillis() / 1000L) % 10);
    }
}
