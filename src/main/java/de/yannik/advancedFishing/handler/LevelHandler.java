package de.yannik.advancedFishing.handler;

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
        long xpCurrent = getXPForLevel(currentLevel);
        long xpNext = getXPForLevel(currentLevel + 1);
        return xpNext - xpCurrent;
    }

    // XP hinzufügen und ggf. Level-Up zurückgeben
    public static int addXP(long currentXP, long amount) {
        long newXP = currentXP + amount;
        return getLevelForXP(newXP);
    }

}
