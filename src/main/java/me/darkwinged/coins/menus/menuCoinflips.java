package me.darkwinged.coins.menus;

import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Coinflip;
import me.darkwinged.coins.libraries.struts.CustomItems;
import me.darkwinged.coins.libraries.struts.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class menuCoinflips extends Menu {

    public menuCoinflips(Player player) {
        super(player);
    }

    public String getMenuName() {
        return Utils.chatColor("&7Coinflips");
    }

    public int getSlots() {
        return 45;
    }

    public void handleMenu(InventoryClickEvent event) {

    }

    public void setMenuItems() {
        for (int i=35; i<44; i++) {
            inventory.setItem(i, this.FILLER_GLASS);
        }

        int i = 0;
        for (Coinflip cf : Manager.getCoinflips()) {
            inventory.setItem(i, CustomItems.coinflip(cf.getChoice(), cf.getOwner(), cf.getWorth()));
            i++;
        }

    }
}
