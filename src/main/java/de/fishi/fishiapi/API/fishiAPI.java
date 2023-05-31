package de.fishi.fishiapi.API;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public interface fishiAPI {
    void loadPlayerConfig(Player p, String path);
    void savePlayerConfig(Player p);
    FileConfiguration getPlayerConfigs(Player p);
}
