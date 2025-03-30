package me.darkwinged.coins.libraries.struts;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.text.SimpleDateFormat;
import java.util.Arrays;

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

}
