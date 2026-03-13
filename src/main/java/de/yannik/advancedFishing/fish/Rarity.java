package de.yannik.advancedFishing.fish;

import net.md_5.bungee.api.ChatColor;

public enum Rarity {
    COMMON("Common", ChatColor.GRAY, 5, 50),
    UNCOMMON("Uncoommon", ChatColor.GREEN, 8, 35),
    RARE("Rare", ChatColor.BLUE, 15, 25),
    UNUSUAL("Unusual", ChatColor.AQUA, 22, 15),
    EPIC("Epic", ChatColor.DARK_PURPLE, 30, 5),
    LEGENDARY("Legendary", ChatColor.GOLD, 50, 2),
    MYTHIC("Mythic", ChatColor.LIGHT_PURPLE, 80, 0.5);

    private final String name;
    private final ChatColor color;
    private final int baseXP;
    private final double chanceWeight;

    Rarity(String name, ChatColor color, int baseXP, double chanceWeight) {
        this.name = name;
        this.color = color;
        this.baseXP = baseXP;
        this.chanceWeight = chanceWeight;
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

    public int getBaseXP() {
        return baseXP;
    }

    public double getChanceWeight() {
        return chanceWeight;
    }
}
