package me.darkwinged.coins.listeners;

import me.darkwinged.coins.libraries.Account;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return;

        double toTake = account.applyPenalty();
        if (toTake <= 0) return;
        player.sendMessage(Utils.chatColor("&6Coins &8» &cOh no! You lost &2" + toTake + " &8(" + account.getPenalty() + "% ) &f coins!"));
    }

}
