package de.yannik.advancedFishing.fish;

import net.md_5.bungee.api.ChatColor;

public enum Trait {
    NORMAL("", ChatColor.GRAY, 1.0, 1.0, 1.0),
    SHINY("Shiny", ChatColor.GOLD, 2.0, 1.5, 1.2),
    STRIPED("Striped", ChatColor.AQUA, 1.2, 1.0, 1.0),
    DARK("Dark", ChatColor.DARK_GRAY, 1.5, 1.0, 1.1),
    ERROR("ERROR", ChatColor.LIGHT_PURPLE, 3.0, 1.2, 2.0),
    TRANSPARENT("Transparent", ChatColor.WHITE, 1.8, 2.0, 1.0),
    RARE_COLORFUL("Colorful", ChatColor.RED, 2.2, 1.0, 1.3);

    private final String name;
    private final ChatColor color;
    private final double sellMultiplier;
    private final double weightMultiplier;
    private final double xpMultiplier;

    Trait(String name, ChatColor color, double sellMultiplier, double weightMultiplier, double xpMultiplier) {
        this.name = name;
        this.color = color;
        this.sellMultiplier = sellMultiplier;
        this.weightMultiplier = weightMultiplier;
        this.xpMultiplier = xpMultiplier;
    }

    public String getName() {
        return name;
    }

    public String getColoredName() {
        return color + name;
    }

    public ChatColor getColor() {
        return color;
    }

    public double getSellMultiplier() {
        return sellMultiplier;
    }

    public double getWeightMultiplier() {
        return weightMultiplier;
    }

    public double getXpMultiplier() {
        return xpMultiplier;
    }
}