package me.darkwinged.coins.listeners;

import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class InsuranceEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return;
        if (account.getInsurance().getTime() < System.currentTimeMillis()) return;

        event.setDroppedExp(0);

        event.setKeepInventory(true);
        event.setKeepLevel(true);
        event.getDrops().clear();
        player.sendMessage(Utils.chatColor("&6Coins &8» &fYour items have been saved due to your insurance!"));
    }

}
