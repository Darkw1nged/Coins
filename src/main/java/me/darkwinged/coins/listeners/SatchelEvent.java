package me.darkwinged.coins.listeners;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SatchelEvent implements Listener {

    private final static Coins plugin = Coins.getInstance;

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand.getType().equals(Material.AIR)) return;

        ItemMeta meta = itemInHand.getItemMeta();
        if (!meta.getPersistentDataContainer().has(new NamespacedKey(plugin, "satchel"), PersistentDataType.DOUBLE)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand.getType().equals(Material.AIR)) return;

        ItemMeta meta = itemInHand.getItemMeta();
        if (!meta.getPersistentDataContainer().has(new NamespacedKey(plugin, "satchel"), PersistentDataType.DOUBLE)) return;

        double value = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "satchel"), PersistentDataType.DOUBLE);
        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return;

        account.addCoins(value);
        itemInHand.setAmount(itemInHand.getAmount() - 1);
        player.sendMessage(Utils.chatColor("&6Coins &8» &a" + value + "&f has been added to your balance."));
    }

}
