package de.yannik.advancedFishing;

import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedFishing extends JavaPlugin {

    private static AdvancedFishing instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AdvancedFishing getInstance() {
        return instance;
    }


}
