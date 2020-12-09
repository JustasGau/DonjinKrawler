package krawlercommon.composite;

import java.util.ArrayList;
import java.util.List;

public class Attribute extends BaseAttribute {
    private final List<BaseAttribute> rawBonuses;
    private final List<BaseAttribute> finalBonuses;

    private double finalValue;

    public Attribute() {
        super(0);
        rawBonuses = new ArrayList<>();
        finalBonuses = new ArrayList<>();
        finalValue = 0;
    }

    public Attribute(int startingValue) {
        super(startingValue);

        rawBonuses = new ArrayList<>();
        finalBonuses = new ArrayList<>();

        finalValue = getBaseValue();
    }

    public void addRawBonus(RawBonus bonus) {
        rawBonuses.add(bonus);
    }

    public void addFinalBonus(FinalBonus bonus) {
        finalBonuses.add(bonus);
    }

    public void removeRawBonus(RawBonus bonus) {
        rawBonuses.remove(bonus);
    }

    public void removeFinalBonus(FinalBonus bonus) {
        finalBonuses.remove(bonus);
    }

    public void clearBonuses() {
        rawBonuses.clear();
        finalBonuses.clear();
    }

    public void clearFinalBonuses() {
        finalBonuses.clear();
    }

    public void clearRawBonuses() {
        rawBonuses.clear();
    }

    public double calculateValue() {
        finalValue = getBaseValue();

        double rawBonusValue = getBonusValues(rawBonuses);
        double rawBonusMultiplier = getBonusMultipliers(rawBonuses);

        finalValue += rawBonusValue;
        finalValue *= (1 + rawBonusMultiplier);

        double finalBonusValue = getBonusValues(finalBonuses);
        double finalBonusMultiplier = getBonusMultipliers(finalBonuses);

        finalValue += finalBonusValue;
        finalValue *= (1 + finalBonusMultiplier);

        return finalValue;
    }

    private double getBonusValues(List<BaseAttribute> bonuses) {
        return bonuses.stream().reduce(0.0, (partialRes, bonus) ->
                partialRes + bonus.getBaseValue(), Double::sum);
    }

    private double getBonusMultipliers(List<BaseAttribute> bonuses) {
        return bonuses.stream().reduce(0.0, (partialRes, bonus) ->
                partialRes + bonus.getBaseMultiplier(), Double::sum);
    }

    public double getFinalValue() {
        return calculateValue();
    }
}
