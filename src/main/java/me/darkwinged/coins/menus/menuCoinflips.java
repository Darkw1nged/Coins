package me.darkwinged.coins.menus;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.Coinflip;
import me.darkwinged.coins.libraries.struts.CustomItems;
import me.darkwinged.coins.libraries.struts.Menu;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class menuCoinflips extends Menu {

    private final static Coins plugin = Coins.getInstance;

    public menuCoinflips(Player player) {
        super(player);
    }

    public String getMenuName() {
        return Utils.chatColor("&8Coinflips");
    }

    public int getSlots() {
        return 36;
    }

    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (slot >= 27) return;

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        ItemMeta meta = item.getItemMeta();
        UUID id = UUID.fromString(meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "coinflip"), PersistentDataType.STRING));

        Coinflip cf = Manager.getCoinflip(id);
        if (cf == null) return;

        if (cf.getOwner() == player.getUniqueId()) {
            player.sendMessage(Utils.chatColor("&6Coins &8» &cYou cannot accept your own coinflip!"));
            return;
        }

        if (cf.getOpponent() != null) {
            player.sendMessage(Utils.chatColor("&6Coins &8» &cThis coinflip has already been accepted!"));
            return;
        }

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) {
            player.sendMessage(Utils.chatColor("&6Coins &8» &cAn error occurred while trying to accept this coinflip!"));
            return;
        }

        if (!account.hasEnoughCoins(cf.getWorth())) {
            player.sendMessage(Utils.chatColor("&6Coins &8» &cYou do not have enough coins to accept this coinflip!"));
            return;
        }

        account.removeCoins(cf.getWorth());
        cf.setOpponent(player.getUniqueId());
        new menuCoinflipRoll(player, cf).open();

        Player target = Bukkit.getPlayer(cf.getOwner());
        if (target != null) {
            new menuCoinflipRoll(target, cf).open();
        }
    }

    public void setMenuItems() {
        for (int i=27; i<35; i++) {
            if (i== 31) {
                inventory.setItem(i, CustomItems.coinflipStats(player));
                continue;
            }
            inventory.setItem(i, this.FILLER_GLASS);
        }

        int i = 0;
        for (Coinflip cf : Manager.getCoinflips()) {
            inventory.setItem(i, CustomItems.coinflip(cf));
            i++;
        }
    }
}
