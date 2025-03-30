package me.darkwinged.coins.libraries.struts;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;

public class CustomItems {

    private final static Coins plugin = Coins.getInstance;

    public static ItemStack satchel(String createdBy, double amount) {
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Utils.chatColor("&eMoney Satchel"));
        meta.setLore(Arrays.asList(
                Utils.chatColor("&8Use this satchel to gain money!"),
                "",
                "&5&lInformation:",
                "&5&l | &fValue: &a" + amount,
                "&5&l | &fCreated by: &e" + createdBy + "&f, on &e" + new SimpleDateFormat("dd/MM/yyyy"),
                "",
                "&7Right-Click to claim"
        ));
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "satchel"), PersistentDataType.DOUBLE, amount);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack coinflip(Coinflip.Choice choice, UUID playerID, double worth) {
        ItemStack item;
        String color;
        switch (choice) {
            case Coinflip.Choice.BLUE -> {
                item = new ItemStack(Material.BLUE_WOOL);
                color = "&9Blue";
            }
            case Coinflip.Choice.GREEN -> {
                item = new ItemStack(Material.GREEN_WOOL);
                color = "&aGreen";
            }
            case Coinflip.Choice.RED -> {
                item = new ItemStack(Material.RED_WOOL);
                color = "&cRed";
            }
            case Coinflip.Choice.WHITE -> {
                item = new ItemStack(Material.WHITE_WOOL);
                color = "&fWhite";
            }
            default -> {
                item = new ItemStack(Material.BLACK_WOOL);
                color = "&0Black";
            }
        }

        ItemMeta meta = item.getItemMeta();
        Player player = Bukkit.getPlayer(playerID);

        meta.setDisplayName(Utils.chatColor("&f" + player.getName() + " Coinflip"));
        meta.setLore(Arrays.asList(
                Utils.chatColor("&9&l | &fBet: &a" + worth),
                Utils.chatColor("&9&l | &fColor: " + color),
                "",
                Utils.chatColor("&eClick to challenge")
        ));
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cf_bet"), PersistentDataType.DOUBLE, worth);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "cf_uuid"), PersistentDataType.STRING, playerID.toString());

        item.setItemMeta(meta);
        return item;
    }

}
