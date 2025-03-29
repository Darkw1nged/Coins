package me.darkwinged.coins.listeners;

import me.darkwinged.coins.libraries.Manager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataProcessEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Manager.hasAccount(player.getUniqueId())) return;

        if (!Manager.loadPlayer(player)) {
            Manager.insertPlayer(player.getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!Manager.hasAccount(player.getUniqueId())) return;
        Manager.savePlayer(player);
    }

}
