package de.yannik.advancedFishing.handler;

import de.yannik.advancedFishing.AdvancedFishing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;
import java.util.Random;

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
            player.sendTitle(bar.toString(), "Fortschritt: " + (int)(progress*10) + "%", 0, 40, 0);
        }

        private void complete() {
            player.sendTitle(ChatColor.GOLD + "Fisch gefangen!", "", 0, 40, 0);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 1.0F);
            cancel();
        }

        public void cancel() {
            if (task != null) task.cancel();
            activeGames.remove(player.getUniqueId());
        }
    }
}