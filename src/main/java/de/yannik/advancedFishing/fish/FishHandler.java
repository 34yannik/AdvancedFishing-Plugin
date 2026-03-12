package de.yannik.advancedFishing.fish;

import de.yannik.advancedFishing.AdvancedFishing;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FishHandler {

    private static final Random random = new Random();

    private static final List<Material> fishMaterials = List.of(
            Material.COD,
            Material.SALMON,
            Material.TROPICAL_FISH,
            Material.PUFFERFISH
    );

    private static final NamespacedKey FISH_KEY =
            new NamespacedKey(AdvancedFishing.getInstance(), "fish");

    private static final NamespacedKey TRAIT_KEY =
            new NamespacedKey(AdvancedFishing.getInstance(), "trait");

    private static final NamespacedKey SIZE_KEY =
            new NamespacedKey(AdvancedFishing.getInstance(), "size");



    public static ItemStack CreateFish(Fish fish, Trait trait, Size size) {

        int randomIndex = random.nextInt(0, fishMaterials.size());
        Material fishMaterial = fishMaterials.get(randomIndex);

        double weight = Math.round(random.nextDouble(fish.getMinWeight(), fish.getMaxWeight()) * 100.0) / 100.0;

        ItemStack fishItem = new ItemStack(fishMaterial, 1);
        ItemMeta fishMeta = fishItem.getItemMeta();

        fishMeta.setDisplayName(fish.getRarity().getColoredName() + " " +
                size.getName() + " " +
                trait.getColoredName() + " " +
                fish.getRarity().getColor() + fish.getName() +
                " §8[" + weight + "kg]");

        fishMeta.setLore(List.of(
                "",
                "Rarity: " + fish.getRarity().getColoredName(),
                "Trait: " + trait.getColoredName(),
                "Size: §b" + size.getName(),
                "Weight: §9" + weight + "kg"
        ));

        fishItem.setItemMeta(fishMeta);

        return AddMetadata(fishItem, fish, trait, size);

    }

    private static ItemStack AddMetadata(ItemStack fishItem, Fish fish, Trait trait, Size size) {

        ItemMeta meta = fishItem.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(FISH_KEY, PersistentDataType.STRING, fish.getKeyName());
        if(trait != Trait.NORMAL)
            container.set(TRAIT_KEY, PersistentDataType.STRING, trait.name());
        if(size != Size.NORMAL)
            container.set(SIZE_KEY, PersistentDataType.STRING, size.name());

        fishItem.setItemMeta(meta);

        return fishItem;
    }

    public static Trait getTrait(ItemStack item) {

        if (item == null || !item.hasItemMeta()) return Trait.NORMAL;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        String traitName = container.get(TRAIT_KEY, PersistentDataType.STRING);

        if (traitName == null) return Trait.NORMAL;

        return Trait.valueOf(traitName);
    }

    public static Size getSize(ItemStack item) {

        if (item == null || !item.hasItemMeta()) return Size.NORMAL;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        String sizeName = container.get(SIZE_KEY, PersistentDataType.STRING);

        if (sizeName == null) return Size.NORMAL;

        return Size.valueOf(sizeName);
    }

    public static Fish getFish(ItemStack item) {

        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        String fishKey = container.get(FISH_KEY, PersistentDataType.STRING);

        if (fishKey == null) return null;

        return Fish.fromKeyName(fishKey);
    }

    public static boolean isFish(ItemStack item) {

        if (item == null || !item.hasItemMeta())
            return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        return container.has(FISH_KEY, PersistentDataType.STRING);
    }

}
