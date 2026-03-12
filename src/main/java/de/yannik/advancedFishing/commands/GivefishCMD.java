package de.yannik.advancedFishing.commands;

import de.yannik.advancedFishing.fish.Fish;
import de.yannik.advancedFishing.fish.FishHandler;
import de.yannik.advancedFishing.fish.Size;
import de.yannik.advancedFishing.fish.Trait;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GivefishCMD implements CommandExecutor, TabCompleter {

    // advancedfishing.commands.givefish.self
    // advancedfishing.commands.givefish.others

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§cUsage: /givefish [player] <fish> [trait] [size]");
            return true;
        }

        Player target = null;
        String fishName;
        String traitName = null;
        String sizeName = null;

        int index = 0;

        // check if argument is a online player
        Player possiblePlayer = Bukkit.getPlayer(args[0]);

        if (possiblePlayer != null) {

            if (!sender.hasPermission("advancedfishing.commands.givefish.others")) {
                sender.sendMessage("§cYou don't have permission to give fish to others.");
                return true;
            }

            target = possiblePlayer;
            index = 1;

        } else {

            if (!(sender instanceof Player)) {
                sender.sendMessage("§cConsole must specify a player.");
                return true;
            }

            if (!sender.hasPermission("advancedfishing.commands.givefish.self")) {
                sender.sendMessage("§cYou don't have permission.");
                return true;
            }

            target = (Player) sender;
        }

        // check fishname
        if (args.length <= index) {
            sender.sendMessage("§cYou must specify a fish.");
            return true;
        }

        fishName = args[index];
        index++;

        // Trait optional
        if (args.length > index) {
            traitName = args[index];
            index++;
        }

        // Size optional
        if (args.length > index) {
            sizeName = args[index];
            index++;
        }

        /*
        HIER DEINE FISCH LOGIK

        Fish fish = Fish.valueOf(fishName.toUpperCase());
        Trait t = trait != null ? Trait.valueOf(trait.toUpperCase()) : null;

        ItemStack fishItem = FishFactory.createFish(fish, t, size);
        target.getInventory().addItem(fishItem);
        */

        Fish fish = Fish.fromKeyName(fishName);

        Trait trait = null;
        Size size = null;

        if (traitName != null) {
            try {
                trait = Trait.valueOf(traitName.toUpperCase());
            } catch (IllegalArgumentException e) {
                sender.sendMessage("§cUnknown trait.");
                return true;
            }
        }

        if (sizeName != null) {
            try {
                size = Size.valueOf(sizeName.toUpperCase());
            } catch (IllegalArgumentException e) {
                sender.sendMessage("§cUnknown size.");
                return true;
            }
        }

        ItemStack fishItem = FishHandler.CreateFish(fish, trait, size);

        target.getInventory().addItem(fishItem);

        sender.sendMessage("§aFish given to " + target.getName());
        return true;
    }

    private List<String> filter(List<String> list, String input) {
        return list.stream()
                .filter(s -> s.toLowerCase().startsWith(input.toLowerCase()))
                .toList();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String alias,
                                                @NotNull String[] args) {

        List<String> suggestions = new ArrayList<>();

        // /givefish ...
        if (args.length == 1) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }

            for (Fish fish : Fish.values()) {
                suggestions.add(fish.getKeyName());
            }

            return filter(suggestions, args[0]);
        }

        // /givefish player ...
        if (args.length == 2) {

            Player possiblePlayer = Bukkit.getPlayer(args[0]);

            if (possiblePlayer != null) {

                for (Fish fish : Fish.values()) {
                    suggestions.add(fish.getKeyName());
                }
            } else {

                for (Trait trait : Trait.values()) {
                    suggestions.add(trait.name().toLowerCase());
                }
            }

            return filter(suggestions, args[1]);
        }

        // /givefish player fish ...
        if (args.length == 3) {

            Player possiblePlayer = Bukkit.getPlayer(args[0]);

            if (possiblePlayer != null) {
                // Traits suggestions
                for (Trait trait : Trait.values()) {
                    suggestions.add(trait.name().toLowerCase());
                }

            } else {
                // Size suggestions
                for(Size size : Size.values()) {
                    suggestions.add(size.name().toLowerCase());
                }
            }

            return filter(suggestions, args[2]);
        }

        // /givefish player fish trait ...
        if (args.length == 4) {

            for(Size size : Size.values()) {
                suggestions.add(size.name().toLowerCase());
            }

            return filter(suggestions, args[3]);
        }

        return Collections.emptyList();
    }
}