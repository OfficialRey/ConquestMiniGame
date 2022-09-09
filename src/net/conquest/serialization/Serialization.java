package net.conquest.serialization;

import net.conquest.buildings.captureable.Captureable;
import net.conquest.entities.PlayerStatistic;
import net.conquest.other.ConquestTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Serialization {

    private static final File saveFile = new File("plugins//conquest//save.dat");
    private static final YamlConfiguration saveCfg = YamlConfiguration.loadConfiguration(saveFile);

    public static void savePlayerStatistic(PlayerStatistic playerStatistic) {
        String path = "player." + playerStatistic.getOwner().getUniqueId();
        saveCfg.set(path + ".gold", playerStatistic.getGold());
        saveCfg.set(path + ".gems", playerStatistic.getGems());
        saveCfg.set(path + ".kills", playerStatistic.getKills());
        saveCfg.set(path + ".deaths", playerStatistic.getDeaths());
        saveCfg.set(path + ".playtime", playerStatistic.getPlaytime()); //int in seconds
        try {
            saveCfg.save(saveFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PlayerStatistic loadPlayerStatistic(Player player) {
        String path = "player." + player.getUniqueId();
        if (saveCfg.contains(path)) {
            int gold = saveCfg.getInt(path + ".gold");
            int gems = saveCfg.getInt(path + ".gems");
            int kills = saveCfg.getInt(path + ".kills");
            int deaths = saveCfg.getInt(path + ".deaths");
            int playtime = saveCfg.getInt(path + ".playtime");
            return new PlayerStatistic(player, gold, gems, kills, deaths, playtime);
        }
        return new PlayerStatistic(player);
    }

    public static void saveLobbyLocation(Location location) {
        try {
            saveLocation(location, "lobby");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Location loadLobbyLocation() {
        return loadLocation("lobby");
    }

    public static void saveCaptureable(Captureable captureable) {
        String path = "zone.team" + captureable.getCurrentTeam().getId() + "." + captureable.getConfigName();
        try {
            saveLocation(captureable.getLocation(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Captureable loadCaptureable(ConquestTeam conquestTeam, String name) {
        String path = "zone.team" + conquestTeam.getId() + "." + name;
        Location location = loadLocation(path);

        return Captureable.fromName(name, location, conquestTeam);
    }

    public static void saveLocation(Location location, String path) throws IOException {
        saveCfg.set(path + ".world", Objects.requireNonNull(location.getWorld()).getName());
        saveCfg.set(path + ".x", location.getX());
        saveCfg.set(path + ".y", location.getY());
        saveCfg.set(path + ".z", location.getZ());
        saveCfg.set(path + ".yaw", location.getYaw());
        saveCfg.set(path + ".pitch", location.getPitch());
        saveCfg.save(saveFile);
    }

    public static Location loadLocation(String path) {
        if (saveCfg.contains(path + ".world")) {
            World w = Bukkit.getWorld(Objects.requireNonNull(saveCfg.getString(path + ".world")));
            double x = saveCfg.getDouble(path + ".x");
            double y = saveCfg.getDouble(path + ".y");
            double z = saveCfg.getDouble(path + ".z");
            float yaw = (float) saveCfg.getDouble(path + ".yaw");
            float pitch = (float) saveCfg.getDouble(path + ".pitch");
            return new Location(w, x, y, z, yaw, pitch);
        }
        return null;
    }
}
