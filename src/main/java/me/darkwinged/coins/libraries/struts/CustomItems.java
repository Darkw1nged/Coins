package me.darkwinged.coins.libraries.struts;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Manager;
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

    public static ItemStack coinflip(Coinflip cf) {
        ItemStack item;
        String color;
        switch (cf.getChoice()) {
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
        Player player = Bukkit.getPlayer(cf.getOwner());

        meta.setDisplayName(Utils.chatColor("&f" + player.getName() + " Coinflip"));
        meta.setLore(Arrays.asList(
                Utils.chatColor("&9&l | &fBet: &a" + cf.getWorth()),
                Utils.chatColor("&9&l | &fColor: " + color),
                "",
                Utils.chatColor("&eClick to challenge")
        ));
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "coinflip"), PersistentDataType.STRING, cf.getGameID().toString());

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack coinflipStats(Player player) {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Utils.chatColor("&eCoinflip Stats"));

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return item;

        meta.setLore(Arrays.asList(
                Utils.chatColor("&e&l | &fWins/Losses: &a" + account.getCoinflipWins() + "&f/&c" + account.getCoinflipLosses()),
                Utils.chatColor("&e&l | &fGained/Lost: &a" + account.getCoinflipTotalWon() + "&f/&c" + account.getCoinflipTotalLost())
        ));

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack insuranceStatus(Player player) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Utils.chatColor("&eInsurance Status"));

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return item;

        long diff = account.getInsurance().getTime() - System.currentTimeMillis();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = diff / (1000 * 60 * 60) % 24;
        long minutes = diff / (1000 * 60) % 60;
        long seconds = diff / 1000 % 60;

        if (diff < 0) {
            meta.setLore(Arrays.asList(
                    Utils.chatColor("&e&l | &fInsurance: &cExpired"),
                    Utils.chatColor("&e&l | &fTime left: &c0s")
            ));
        } else {
            meta.setLore(Arrays.asList(
                    Utils.chatColor("&e&l | &fInsurance: &aActive"),
                    Utils.chatColor("&e&l | &fTime left: &a" + days + "d " + hours + "h " + minutes + "m " + seconds + "s")
            ));
        }

        item.setItemMeta(meta);
        return item;
    }

}
