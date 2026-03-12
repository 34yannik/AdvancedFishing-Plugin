package de.yannik.advancedFishing.fish;

public enum Size {
    TINY("Tiny", 0.2, 1.5),
    NORMAL("", 1, 1.0),
    HUGE("Huge", 3, 2.2);

    private final String name;
    private final double weightMultiplier;
    private final double xpMultiplier;

    Size(String name, double weightMultiplier, double xpMultiplier) {
        this.name = name;
        this.weightMultiplier = weightMultiplier;
        this.xpMultiplier = xpMultiplier;
    }

    public String getName() {
        return name;
    }

    public double getWeightMultiplier() {
        return weightMultiplier;
    }

    public double getXpMultiplier() {
        return xpMultiplier;
    }
}
