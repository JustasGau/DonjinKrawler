package krawlercommon.composite;

public class BaseAttribute {

    private double baseValue;
    private double baseMultiplier;

    public BaseAttribute(double baseVal) {
        this.baseValue = baseVal;
        this.baseMultiplier = 0;
    }

    public BaseAttribute(double baseVal, double baseMultiplier) {
        this.baseValue = baseVal;
        this.baseMultiplier = baseMultiplier;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public double getBaseMultiplier() {
        return baseMultiplier;
    }
}
