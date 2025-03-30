package me.darkwinged.coins;

import me.darkwinged.coins.commands.cmdCoins;
import me.darkwinged.coins.commands.cmdEconomy;
import me.darkwinged.coins.commands.cmdPay;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.vault.CoinEconomy;
import me.darkwinged.coins.libraries.vault.VaultHook;
import me.darkwinged.coins.listeners.DataProcessEvent;
import me.darkwinged.coins.listeners.DeathEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Coins extends JavaPlugin {

    public static Coins getInstance;
    private VaultHook vaultHook;
    public static Economy coinsEconomy;

    public void onEnable() {
        getInstance = this;
        coinsEconomy = new CoinEconomy();

        vaultHook = new VaultHook();
        vaultHook.hook();

        getCommand("economy").setExecutor(new cmdEconomy());
        getCommand("balance").setExecutor(new cmdCoins());
        getCommand("pay").setExecutor(new cmdPay());

        getServer().getPluginManager().registerEvents(new DataProcessEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        Manager.loadAllPlayers();

        getServer().getConsoleSender().sendMessage(Utils.chatColor("&6Coins &8» &aPlugin has been enabled!"));
    }

    public void onDisable() {
        Manager.saveAllPlayers();
        vaultHook.unhook();
        getServer().getConsoleSender().sendMessage(Utils.chatColor("&6Coins &8» &cPlugin has been disabled!"));
    }

}
