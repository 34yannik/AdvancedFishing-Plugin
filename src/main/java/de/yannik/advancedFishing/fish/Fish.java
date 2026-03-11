package de.yannik.advancedFishing.fish;

import org.bukkit.block.Biome;

public enum Fish {

    SALMON("Salmon", Rarity.COMMON, Biome.RIVER, 2.0, 6.0),
    GOLDEN_SALMON("Golden Salmon", Rarity.RARE, Biome.RIVER, 3.0, 8.0),
    TROUT("Trout", Rarity.UNCOMMON, Biome.RIVER, 1.5, 5.0),
    PIKE("Pike", Rarity.UNCOMMON, Biome.RIVER, 4.0, 10.0),
    CATFISH("Catfish", Rarity.RARE, Biome.RIVER, 5.0, 12.0),
    CARP("Carp", Rarity.COMMON, Biome.RIVER, 2.0, 7.0),
    BARBEL("Barbel", Rarity.UNCOMMON, Biome.RIVER, 1.0, 4.0),
    PERCH("Perch", Rarity.COMMON, Biome.RIVER, 1.5, 4.5),
    BULLHEAD("Bullhead", Rarity.UNCOMMON, Biome.RIVER, 2.0, 5.0),
    RIVER_SHARK("River Shark", Rarity.LEGENDARY, Biome.RIVER, 30.0, 50.0),


    FOREST_SALMON("Forest Salmon", Rarity.COMMON, Biome.FOREST, 2.0, 5.5),
    WOODCARP("Wood Carp", Rarity.UNCOMMON, Biome.FOREST, 1.5, 6.0),
    TADPOLE("Tadpole", Rarity.COMMON, Biome.FOREST, 0.5, 1.5),
    CRIMSON_BASS("Crimson Bass", Rarity.RARE, Biome.FOREST, 3.0, 8.0),
    FOREST_CATFISH("Forest Catfish", Rarity.UNCOMMON, Biome.FOREST, 2.0, 7.0),
    POND_PERCH("Pond Perch", Rarity.COMMON, Biome.FOREST, 1.0, 3.5),
    REDFIN("Redfin", Rarity.RARE, Biome.FOREST, 2.5, 6.5),
    SWIFTFISH("Swiftfish", Rarity.UNCOMMON, Biome.FOREST, 1.5, 4.0),
    FOREST_STURGEON("Forest Sturgeon", Rarity.EPIC, Biome.FOREST, 8.0, 18.0),
    SHIMMERFISH("Shimmerfish", Rarity.LEGENDARY, Biome.FOREST, 10.0, 25.0),


    PLAINS_CARP("Plains Carp", Rarity.COMMON, Biome.PLAINS, 1.5, 4.0),
    PLAINS_BASS("Plains Bass", Rarity.UNCOMMON, Biome.PLAINS, 2.0, 5.5),
    POND_SPRITE("Pond Sprite", Rarity.RARE, Biome.PLAINS, 0.8, 2.5),
    GRASSFIN("Grassfin", Rarity.COMMON, Biome.PLAINS, 1.0, 3.0),
    PLAINS_CATFISH("Plains Catfish", Rarity.UNCOMMON, Biome.PLAINS, 2.5, 6.5),
    MEADOW_SALMON("Meadow Salmon", Rarity.RARE, Biome.PLAINS, 3.0, 7.0),
    SUNFISH("Sunfish", Rarity.COMMON, Biome.PLAINS, 1.0, 2.5),
    GOLDEN_MINNOW("Golden Minnow", Rarity.UNCOMMON, Biome.PLAINS, 0.5, 1.5),
    PLAINS_STURGEON("Plains Sturgeon", Rarity.EPIC, Biome.PLAINS, 8.0, 15.0),
    RAINBOWFIN("Rainbowfin", Rarity.LEGENDARY, Biome.PLAINS, 12.0, 28.0),


    SWAMP_EEL("Swamp Eel", Rarity.COMMON, Biome.SWAMP, 2.0, 6.0),
    MUDFISH("Mudfish", Rarity.UNCOMMON, Biome.SWAMP, 1.5, 5.0),
    TOADFISH("Toadfish", Rarity.RARE, Biome.SWAMP, 2.5, 7.0),
    LILYPAD_BASS("Lilypad Bass", Rarity.COMMON, Biome.SWAMP, 1.5, 4.0),
    SWAMP_CATFISH("Swamp Catfish", Rarity.UNCOMMON, Biome.SWAMP, 2.0, 6.0),
    PANTHERFIN("Pantherfin", Rarity.RARE, Biome.SWAMP, 3.0, 8.0),
    MARSH_GUARD("Marsh Guard", Rarity.EPIC, Biome.SWAMP, 10.0, 20.0),
    SHADOWFIN("Shadowfin", Rarity.LEGENDARY, Biome.SWAMP, 15.0, 30.0),
    SWAMP_SPRITE("Swamp Sprite", Rarity.RARE, Biome.SWAMP, 1.0, 3.0),
    MIRE_FISH("Mire Fish", Rarity.UNCOMMON, Biome.SWAMP, 1.5, 4.5),


    COD("Cod", Rarity.COMMON, Biome.OCEAN, 2.0, 5.0),
    TUNA("Tuna", Rarity.UNCOMMON, Biome.OCEAN, 5.0, 15.0),
    GREAT_WHITE("Great White Shark", Rarity.LEGENDARY, Biome.OCEAN, 50.0, 100.0),
    MANTA("Manta Ray", Rarity.EPIC, Biome.OCEAN, 30.0, 60.0),
    ANGELFISH("Angelfish", Rarity.RARE, Biome.OCEAN, 1.0, 3.0),
    CLOWNFISH("Clownfish", Rarity.COMMON, Biome.OCEAN, 0.5, 1.5),
    BLUEFIN_TUNA("Bluefin Tuna", Rarity.EPIC, Biome.OCEAN, 20.0, 40.0),
    DOLPHINFISH("Dolphinfish", Rarity.RARE, Biome.OCEAN, 4.0, 10.0),
    GIANT_OCEAN_CAT("Giant Ocean Catfish", Rarity.LEGENDARY, Biome.OCEAN, 25.0, 50.0),
    SEA_SERPENT("Sea Serpent", Rarity.LEGENDARY, Biome.OCEAN, 60.0, 120.0);

    private final String name;
    private final Rarity rarity;
    private final Biome biome;
    private final double minWeight;
    private final double maxWeight;

    Fish(String name, Rarity rarity, Biome biome, double minWeight, double maxWeight) {
        this.name = name;
        this.rarity = rarity;
        this.biome = biome;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

    public String getName() { return name; }
    public Rarity getRarity() { return rarity; }
    public Biome getBiome() { return biome; }
    public double getMinWeight() { return minWeight; }
    public double getMaxWeight() { return maxWeight; }

    // random weight
    public double getRandomWeight() {
        return minWeight + Math.random() * (maxWeight - minWeight);
    }
}