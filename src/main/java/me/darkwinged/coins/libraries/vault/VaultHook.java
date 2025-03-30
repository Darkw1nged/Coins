package me.darkwinged.coins.libraries.vault;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {

    private final Coins plugin = Coins.getInstance;
    private final Economy provider = Coins.coinsEconomy;

    public void hook() {
        Bukkit.getServicesManager().register(Economy.class, provider, plugin, ServicePriority.Highest);
        Bukkit.getConsoleSender().sendMessage(Utils.chatColor("&aVaultAPI hooked into &6Coins"));
    }

    public void unhook() {
        Bukkit.getServicesManager().unregisterAll(plugin);
        Bukkit.getConsoleSender().sendMessage(Utils.chatColor("&aVaultAPI unhooked from &6Coins"));
    }

}
