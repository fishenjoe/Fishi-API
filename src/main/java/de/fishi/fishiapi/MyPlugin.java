package de.fishi.fishiapi;

import de.fishi.fishiapi.API.fishiAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyPlugin extends JavaPlugin implements fishiAPI, Listener {

    private Map<UUID, FileConfiguration> playerConfigs;
    private static MyPlugin plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        playerConfigs = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void loadPlayerConfig(Player player) {
        UUID playerUUID = player.getUniqueId();
        File dir = new File(getDataFolder().getPath() + "player-Saves");

        if(!dir.exists()){
            dir.mkdirs();
        }

        File configFile = new File(dir, playerUUID + ".yml");

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        // YAML-Konfigurationsdatei laden
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        playerConfigs.put(playerUUID, config);
    }

    @Override
    public void savePlayerConfig(Player player) {
        UUID playeruuid = player.getUniqueId();
        File configFile = new File(getDataFolder(), playeruuid + ".yml");

        if (!configFile.exists()) {
            return;
        }

        FileConfiguration config = playerConfigs.get(playeruuid);

        // Speichere die Konfiguration in die Datei
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MyPlugin getInstance() {
        return plugin;
    }
    @Override
    public FileConfiguration getPlayerConfigs(Player p){
        return playerConfigs.get(p.getUniqueId());
    }
}
