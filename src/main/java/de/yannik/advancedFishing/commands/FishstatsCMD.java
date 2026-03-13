package de.yannik.advancedFishing.commands;

import de.yannik.advancedFishing.AdvancedFishing;
import de.yannik.advancedFishing.handler.LevelHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FishstatsCMD implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if(args.length == 0) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            UUID uuid = player.getUniqueId();

            AdvancedFishing.getInstance().getPlayerStatsDAO().loadPlayer(uuid).thenAccept( playerStats -> {

                player.sendMessage("§5§lFishing Stats");
                player.sendMessage("§7Level: §b" + playerStats.getLevel());
                player.sendMessage("§7XP: §9" + playerStats.getCurrentXp() + " §7/ §9" + LevelHandler.getXPForLevel(playerStats.getLevel()));
                player.sendMessage("§7Caught Fish: §9" + playerStats.getFishCaught() + " Fish");
                player.sendMessage("§7Balance: §e" + playerStats.getMoney() + "$");

            });

        } else {

            Player target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                sender.sendMessage("The player wasn't found.");
                return false;
            }

            AdvancedFishing.getInstance().getPlayerStatsDAO().loadPlayer(target.getUniqueId()).thenAccept( playerStats -> {

                sender.sendMessage("§5§l" + target.getName() + "'s Fishing Stats");
                sender.sendMessage("§7Level: §b" + playerStats.getLevel());
                sender.sendMessage("§7XP: §9" + playerStats.getCurrentXp() + " §7/ §9" + LevelHandler.getXPForLevel(playerStats.getLevel()));
                sender.sendMessage("§7Caught Fish: §9" + playerStats.getFishCaught() + " Fish");
                sender.sendMessage("§7Balance: §e" + playerStats.getMoney() + "$");

            });

        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        ArrayList<String> suggestions = new ArrayList<>();

        if(args.length == 1) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }
        }

        return suggestions;
    }
}
