package de.yannik.advancedFishing.data;

import de.yannik.advancedFishing.AdvancedFishing;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerStatsDAO {

    private final AdvancedFishing plugin;
    private final Database database;

    public PlayerStatsDAO(AdvancedFishing plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
        initTable();
    }

    // Create the table
    private void initTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS player_stats (
                    uuid VARCHAR(36) PRIMARY KEY,
                    fish_caught BIGINT DEFAULT 0,
                    money BIGINT DEFAULT 0
                );
                """;

        try (Statement stmt = database.getConnection().createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().severe("[AdvancedFishing] Could not create player_stats table!");
        }
    }

    // Load playerdata async
    public CompletableFuture<PlayerStats> loadPlayer(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "SELECT * FROM player_stats WHERE uuid = ?;";

            try (PreparedStatement stmt = database.getConnection().prepareStatement(sql)) {
                stmt.setString(1, uuid.toString());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    long fishCaught = rs.getLong("fish_caught");
                    long money = rs.getLong("money");
                    return new PlayerStats(uuid, fishCaught, money);
                } else {

                    PlayerStats stats = new PlayerStats(uuid, 0, 0);
                    savePlayer(stats);
                    return stats;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return new PlayerStats(uuid, 0, 0);
            }
        });
    }

    // Save playerdata async
    public void savePlayer(PlayerStats stats) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String sql = "INSERT OR REPLACE INTO player_stats(uuid, fish_caught, money) VALUES(?, ?, ?);";

            try (PreparedStatement stmt = database.getConnection().prepareStatement(sql)) {
                stmt.setString(1, stats.getUuid().toString());
                stmt.setLong(2, stats.getFishCaught());
                stmt.setLong(3, stats.getMoney());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // PlayerStats model class
    public static class PlayerStats {
        private final UUID uuid;
        private long fishCaught;
        private long money;

        public PlayerStats(UUID uuid, long fishCaught, long money) {
            this.uuid = uuid;
            this.fishCaught = fishCaught;
            this.money = money;
        }

        public UUID getUuid() { return uuid; }
        public long getFishCaught() { return fishCaught; }
        public void setFishCaught(long fishCaught) { this.fishCaught = fishCaught; }

        public long getMoney() { return money; }
        public void setMoney(long money) { this.money = money; }
    }
}