package de.yannik.advancedFishing.fish;

import net.md_5.bungee.api.ChatColor;

public enum Rarity {
    COMMON("Common", ChatColor.GRAY, 5),
    UNCOMMON("Uncoommon", ChatColor.GREEN, 8),
    RARE("Rare", ChatColor.BLUE, 15),
    UNUSUAL("Unusual", ChatColor.AQUA, 22),
    EPIC("Epic", ChatColor.DARK_PURPLE, 30),
    LEGENDARY("Legendary", ChatColor.GOLD, 50),
    MYTHIC("Mythic", ChatColor.LIGHT_PURPLE, 80);

    private final String name;
    private final ChatColor color;
    private final int baseXP;

    Rarity(String name, ChatColor color, int baseXP) {
        this.name = name;
        this.color = color;
        this.baseXP = baseXP;
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
}
