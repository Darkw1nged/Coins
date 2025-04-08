package me.darkwinged.coins.menus;

import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.CustomItems;
import me.darkwinged.coins.libraries.struts.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Calendar;

public class menuInsurance extends Menu {

    public menuInsurance(Player player) {
        super(player);
    }

    public String getMenuName() {
        return Utils.chatColor("&8Insurance");
    }

    public int getSlots() {
        return 27;
    }

    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) {
            player.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
            player.closeInventory();
            return;
        }

        int slot = event.getSlot();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(account.getInsurance());

        switch (slot) {
            case 14 -> {
                if (!account.hasEnoughCoins(1000)) {
                    player.sendMessage(Utils.chatColor("&6Coins &8» &cYou do not have enough coins!"));
                    return;
                }
                account.removeCoins(1000);
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                account.setInsurance(calendar.getTime());
                player.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully purchased 1 day insurance for &a1000 &fcoins"));
            }
            case 15 -> {
                if (!account.hasEnoughCoins(3000)) {
                    player.sendMessage(Utils.chatColor("&6Coins &8» &cYou do not have enough coins!"));
                    return;
                }
                account.removeCoins(3000);
                calendar.add(Calendar.DAY_OF_YEAR, 3);
                account.setInsurance(calendar.getTime());
                player.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully purchased 3 days insurance for &a3000 &fcoins"));
            }
            case 16 -> {
                if (!account.hasEnoughCoins(7000)) {
                    player.sendMessage(Utils.chatColor("&6Coins &8» &cYou do not have enough coins!"));
                    return;
                }
                account.removeCoins(7000);
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                account.setInsurance(calendar.getTime());
                player.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully purchased 7 days insurance for &a7000 &fcoins"));
            }

        }
    }

    public void setMenuItems() {
        setFillerGlass();

        inventory.setItem(3, makeItem(Material.ORANGE_STAINED_GLASS_PANE, " "));
        inventory.setItem(12, makeItem(Material.ORANGE_STAINED_GLASS_PANE, " "));
        inventory.setItem(21, makeItem(Material.ORANGE_STAINED_GLASS_PANE, " "));

        inventory.setItem(14, makeItem(Material.EMERALD, Utils.chatColor("&a1 Day Insurance"), Utils.chatColor("&a&l | &fCost: &a1,000 Coins"), "", Utils.chatColor("&eClick to purchase")));
        inventory.setItem(15, makeItem(Material.EMERALD, Utils.chatColor("&a3 Day Insurance"), Utils.chatColor("&a&l | &fCost: &a3,000 Coins"), "", Utils.chatColor("&eClick to purchase")));
        inventory.setItem(16, makeItem(Material.EMERALD, Utils.chatColor("&a7 Day Insurance"), Utils.chatColor("&a&l | &fCost: &a7,000 Coins"), "", Utils.chatColor("&eClick to purchase")));

        inventory.setItem(10, CustomItems.insuranceStatus(player));
    }
}
