package de.yannik.advancedFishing.handler;

import de.yannik.advancedFishing.AdvancedFishing;
import de.yannik.advancedFishing.data.PlayerStatsDAO;
import de.yannik.advancedFishing.fish.Fish;
import de.yannik.advancedFishing.fish.FishHandler;
import de.yannik.advancedFishing.fish.Size;
import de.yannik.advancedFishing.fish.Trait;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class FishingHandler implements Listener {

    public static HashMap<UUID, Minigame> activeGames = new HashMap<>();
    private final Random random = new Random();

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();

        if (activeGames.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            event.setCancelled(true);
            event.getHook().remove();
            startMinigame(player);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Minigame game = activeGames.get(player.getUniqueId());
        if (game == null) return;

        Location from = event.getFrom();
        Location to = event.getTo();

        double deltaX = to.getX() - from.getX();
        double deltaZ = to.getZ() - from.getZ();

        if(Math.abs(deltaX) > 0.1){
            game.movePlayerBar(deltaX > 0 ? 1 : -1);
        }
        if(Math.abs(deltaZ) > 0.1){
            game.movePlayerBar(deltaZ > 0 ? 1 : -1);
        }

        event.setCancelled(true);
    }

    private void startMinigame(Player player) {
        Minigame game = new Minigame(player);
        activeGames.put(player.getUniqueId(), game);
        game.start();
    }

    private class Minigame {
        private final Player player;
        private final int totalBarLength = 20;
        private final int playerBarSize = 9;

        private int playerBarPos = 5;
        private double fishPos = 0.5;
        private double fishSpeed = 0.03;
        private boolean fishMovingRight = true;
        private double progress = 0.0;
        private final double maxProgress = 10.0;

        private final double directionChangeChance = 0.2; // 20% Chance pro Tick
        private final Random random = new Random();

        private BukkitRunnable task;

        private Minigame(Player player) {
            this.player = player;
        }

        public void start() {
            task = new BukkitRunnable() {
                @Override
                public void run() {

                    // zufällig Richtung ändern
                    if (random.nextDouble() < directionChangeChance) {
                        fishMovingRight = !fishMovingRight;
                    }

                    // Fisch bewegen
                    if(fishMovingRight){
                        fishPos += fishSpeed;
                        if(fishPos >= 1.0) fishMovingRight = false;
                    } else {
                        fishPos -= fishSpeed;
                        if(fishPos <= 0.0) fishMovingRight = true;
                    }

                    // progress check
                    double barStart = playerBarPos / (double) totalBarLength;
                    double barEnd = (playerBarPos + playerBarSize) / (double) totalBarLength;

                    if (fishPos >= barStart && fishPos <= barEnd) {
                        progress += 0.2;
                    } else {
                        progress -= 0.1;
                        progress = Math.max(0, progress);
                    }

                    updateDisplay();

                    if (progress >= maxProgress) complete();
                }
            };
            task.runTaskTimer(AdvancedFishing.getInstance(), 0, 2);
        }

        public void movePlayerBar(int direction) {
            playerBarPos = Math.max(0, Math.min(totalBarLength - playerBarSize, playerBarPos + direction));
        }

        private void updateDisplay() {
            StringBuilder bar = new StringBuilder(ChatColor.GRAY + "[");
            for (int i = 0; i < totalBarLength; i++) {
                boolean isFish = (int) (fishPos * totalBarLength) == i;
                boolean isPlayer = i >= playerBarPos && i < playerBarPos + playerBarSize;

                if (isFish && isPlayer) {
                    bar.append(ChatColor.GREEN + "|");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 0.5F, 0.8F);
                }
                else if (isFish) bar.append(ChatColor.AQUA + "|");
                else if (isPlayer) bar.append(ChatColor.WHITE + "|");
                else bar.append(ChatColor.GRAY + "-");
            }
            bar.append(ChatColor.GRAY + "]");
            player.sendTitle(bar.toString(), "Progress: " + (int)(progress*10) + "%", 0, 40, 0);
        }

        private void complete() {
            player.sendTitle(ChatColor.GOLD + "Fish caught!", "", 0, 40, 0);

            giveReward();

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            cancel();
        }

        private void giveReward() {

            Biome biome = player.getLocation().getBlock().getBiome();

            List<Fish> fishable = new ArrayList<>();

            for (Fish fish : Fish.values()) {
                if (fish.getBiome() == biome) {
                    fishable.add(fish);
                }
            }

            if(fishable.isEmpty()) {
                for(Fish fish : Fish.values()) {
                    if(fish.getBiome() == Biome.RIVER)
                        fishable.add(fish);
                }
            }

            double totalWeight = 0;

            for (Fish fish : fishable) {
                totalWeight += fish.getRarity().getChanceWeight();
            }


            double random = Math.random() * totalWeight;

            Fish selected = null;
            double current = 0;

            for (Fish fish : fishable) {
                current += fish.getRarity().getChanceWeight();

                if (random < current) {
                    selected = fish;
                    break;
                }
            }

            if (selected != null) {

                Random rnd = new Random();

                Trait trait;
                Size size;

                if (rnd.nextDouble() > 0.90) {

                    List<Trait> possibleTraits = new ArrayList<>();

                    for (Trait t : Trait.values()) {
                        if (t != Trait.NORMAL) {
                            possibleTraits.add(t);
                        }
                    }

                    trait = possibleTraits.get(rnd.nextInt(possibleTraits.size()));

                } else {
                    trait = Trait.NORMAL;
                }

                if (rnd.nextDouble() > 0.80) {

                    List<Size> possibleSizes = new ArrayList<>();

                    for (Size t : Size.values()) {
                        if (t != Size.NORMAL) {
                            possibleSizes.add(t);
                        }
                    }

                    size = possibleSizes.get(rnd.nextInt(possibleSizes.size()));

                } else {
                    size = Size.NORMAL;
                }

                ItemStack fishItem = FishHandler.CreateFish(selected, trait, size);

                player.sendMessage("§aYou caught: " + fishItem.getItemMeta().getDisplayName() + "§a!");

                boolean invFree = false;

                for (ItemStack item : player.getInventory().getContents()) {
                    if (item == null || item.getType() == Material.AIR) {
                        invFree = true;
                        break;
                    }
                }

                if (invFree) {
                    player.getInventory().addItem(fishItem);
                } else {
                    player.getWorld().dropItemNaturally(player.getLocation(), fishItem);
                    player.sendMessage("§cYou'r inventory was full, the fish has been dropped.");
                }

                Fish finalSelected = selected;
                AdvancedFishing.getInstance()
                        .getPlayerStatsDAO()
                        .loadPlayer(player.getUniqueId())
                        .thenAccept(stats -> {

                            stats.setFishCaught(stats.getFishCaught()+1);

                            AdvancedFishing.getInstance().getPlayerStatsDAO().savePlayer(stats);

                            double xpMulti = trait.getXpMultiplier() + size.getXpMultiplier();

                            int baseXP = finalSelected.getRarity().getBaseXP();
                            int minXP = baseXP - 5;
                            int maxXP = baseXP + 5;

                            int randomXP = rnd.nextInt(maxXP - minXP + 1) + minXP;
                            int xpToAdd = (int) Math.round(randomXP * xpMulti);

                            giveXPReward(stats, xpToAdd);

                        });

            }
            else {
                player.sendMessage("§cYou didn't catch anything.");
            }
        }

        public void giveXPReward(PlayerStatsDAO.PlayerStats stats, int xp) {

            List<Integer> level = LevelHandler.addXP(stats, xp);

            player.sendMessage("§7+ §9" + xp + "XP");

            if(level.get(0) < level.get(1)) {
                player.sendMessage("§7========================");
                player.sendMessage("§5Fishing Levelup!");
                player.sendMessage("");
                player.sendMessage("§9Level " + level.get(0) + " -> Level " + level.get(1));
                player.sendMessage("");
                player.sendMessage("§7========================");

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7F, 1.0F);
            }

        }

        public void cancel() {
            if (task != null) task.cancel();
            activeGames.remove(player.getUniqueId());
        }
    }
}