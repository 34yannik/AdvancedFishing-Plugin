package de.yannik.advancedFishing;

import de.yannik.advancedFishing.commands.FishstatsCMD;
import de.yannik.advancedFishing.commands.GivefishCMD;
import de.yannik.advancedFishing.data.Database;
import de.yannik.advancedFishing.data.PlayerStatsDAO;
import de.yannik.advancedFishing.handler.FishingHandler;
import org.bukkit.Bukkit;
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

        getCommand("givefish").setExecutor(new GivefishCMD());
        getCommand("givefish").setTabCompleter(new GivefishCMD());
        getCommand("fishstats").setExecutor(new FishstatsCMD());
        getCommand("fishstats").setTabCompleter(new FishstatsCMD());

        Bukkit.getPluginManager().registerEvents(new FishingHandler(), this);

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
