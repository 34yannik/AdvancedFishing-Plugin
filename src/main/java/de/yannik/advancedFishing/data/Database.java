package de.yannik.advancedFishing.data;

import org.bukkit.Bukkit;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class Database {

    private Connection connection;

    public void Connect() {
        try {
            File folder = new File("plugins/AdvancedFishing");
            if(!folder.exists()) folder.mkdirs();

            String url = "jdbc:sqlite:" + folder.getPath() + "/advancedfishingdb.db";
            connection = DriverManager.getConnection(url);

            Bukkit.getLogger().log(Level.INFO, "[AdvancedFishing] SQLite connected!");
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.SEVERE, "[AdvancedFishing] SQLite could not connect!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void Close() {
        try {
            if(connection != null) connection.close();
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.SEVERE, "[AdvancedFishing] SQLite connection could not close!");
            e.printStackTrace();
        }
    }


}
