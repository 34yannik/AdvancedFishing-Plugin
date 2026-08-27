package de.yannik.advancedFishing.handler;

import de.yannik.advancedFishing.AdvancedFishing;
import de.yannik.advancedFishing.data.PlayerStatsDAO;

import java.util.ArrayList;
import java.util.List;

public class LevelHandler {

    private static long[] levelCache = new long[200];

    public static int MAX_LEVEL = 200;

    public static long getXPForLevel(int level) {
        if(level == 1) return 100;

        if(levelCache[level - 1] != 0) return levelCache[level - 1];

        long xp = (long) (100 * Math.pow(1.1, level - 1));
        levelCache[level - 1] = xp;
        return xp;
    }

    public static int getLevelForXP(long xp) {
        int level = 1;
        while(level < MAX_LEVEL && xp >= getXPForLevel(level)) {
            xp -= getXPForLevel(level);
            level++;
        }
        return level;
    }

    // XP bis zum nächsten Level
    public static long getXPToNextLevel(int currentLevel) {

        if (currentLevel >= MAX_LEVEL) {
            return 0;
        }

        long xpCurrent = getXPForLevel(currentLevel);
        long xpNext = getXPForLevel(currentLevel + 1);
        return xpNext - xpCurrent;
    }

    public static List<Integer> addXP(PlayerStatsDAO.PlayerStats stats, long amount) {

        stats.setCurrentXp(stats.getCurrentXp() + amount);
        stats.setAlltimeXp(stats.getAlltimeXp() + amount);

        List<Integer> leveledUp = new ArrayList<>();

        leveledUp.add(stats.getLevel());

        while (stats.getLevel() < MAX_LEVEL
                && stats.getCurrentXp() >= getXPForLevel(stats.getLevel())) {
            stats.setCurrentXp(stats.getCurrentXp() - getXPForLevel(stats.getLevel()));
            stats.setLevel(stats.getLevel() + 1);
        }

        leveledUp.add(stats.getLevel());

        AdvancedFishing.getInstance().getPlayerStatsDAO().savePlayer(stats);

        return leveledUp;
    }

}
