package de.fishi.fishiapi.listener;

import de.fishi.fishiapi.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Main.getInstance().loadPlayerConfig(p);
    }
}
