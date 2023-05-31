package de.fishi.fishiapi;

import de.fishi.fishiapi.listener.PlayerJoinListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private Map<UUID, FileConfiguration> playerConfigs;
    private static Main plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        addEventListener();
        playerConfigs = new HashMap<>();
    }

    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers()) {
            savePlayerConfig(player);
        }
    }

    public void addEventListener(){
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new PlayerJoinListener(), this);
    }

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

    private void savePlayerConfig(Player player) {
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

    public static Main getInstance() {
        return plugin;
    }

    public FileConfiguration getPlayerConfigs(Player p){
        return playerConfigs.get(p.getUniqueId());
    }
}
