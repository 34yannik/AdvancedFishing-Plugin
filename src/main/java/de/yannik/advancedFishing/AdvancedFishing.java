package de.yannik.advancedFishing;

import de.yannik.advancedFishing.data.Database;
import de.yannik.advancedFishing.data.PlayerStatsDAO;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedFishing extends JavaPlugin {

    private static AdvancedFishing instance;
    private static Database database;
    private static PlayerStatsDAO playerStatsDAO;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        // Database
        database = new Database();
        database.Connect();
        playerStatsDAO = new PlayerStatsDAO(instance, database);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(database != null) database.Close();
    }

    public static AdvancedFishing getInstance() {
        return instance;
    }

    public static Database getDatabase() {
        return database;
    }

    public PlayerStatsDAO getPlayerStatsDAO() {
        return playerStatsDAO;
    }
}
