package me.darkwinged.coins.listeners;

import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataProcessEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!Manager.hasAccount(player.getUniqueId())) {
            Manager.insertPlayer(player.getUniqueId());
        }

        // add interest
        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return;
        if (System.currentTimeMillis() < (account.getLastGained() + (24 * 60 * 60 * 1000))) return;

        double gained = account.addInterest();
        player.sendMessage(Utils.chatColor("&6Coins &8» &fWelcome back! Today you have gained &a" + gained + " &8(" + account.getInterest() + "%) &fcoins!"));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Account playerAccount = Manager.getAccount(player.getUniqueId());
        if (playerAccount == null) return;
        playerAccount.save();
    }

}
