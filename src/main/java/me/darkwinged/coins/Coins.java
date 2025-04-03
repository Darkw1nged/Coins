package me.darkwinged.coins;

import me.darkwinged.coins.commands.*;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.timers.LotteryChecker;
import me.darkwinged.coins.libraries.timers.LotteryDraw;
import me.darkwinged.coins.libraries.vault.CoinEconomy;
import me.darkwinged.coins.libraries.vault.VaultHook;
import me.darkwinged.coins.listeners.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("all")
public final class Coins extends JavaPlugin {

    public static Coins getInstance;
    private VaultHook vaultHook;
    public static Economy coinsEconomy;

    public void onEnable() {
        getInstance = this;
        coinsEconomy = new CoinEconomy();

        vaultHook = new VaultHook();
        vaultHook.hook();
        registerCommands();
        registerEvents();

        new LotteryChecker().runTaskTimerAsynchronously(this, 20 * 5, 20 * 5);
        new LotteryDraw().runTaskTimerAsynchronously(this, 20 * 5, 20 * 5);

        Manager.loadAllAccounts();
        Manager.loadCoinflips();

        getServer().getConsoleSender().sendMessage(Utils.chatColor("&6Coins &8» &aPlugin has been enabled!"));
    }

    public void onDisable() {
        Manager.saveAllAccounts();
        Manager.saveCoinflips();
        vaultHook.unhook();
        getServer().getConsoleSender().sendMessage(Utils.chatColor("&6Coins &8» &cPlugin has been disabled!"));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new MenuEvent(), this);
        getServer().getPluginManager().registerEvents(new DataProcessEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new SatchelEvent(), this);
        getServer().getPluginManager().registerEvents(new InsuranceEvent(), this);
        getServer().getPluginManager().registerEvents(new ScavengeEvent(), this);
    }

    public void registerCommands() {
        getCommand("economy").setExecutor(new cmdEconomy());
        getCommand("balance").setExecutor(new cmdCoins());
        getCommand("pay").setExecutor(new cmdPay());
        getCommand("withdraw").setExecutor(new cmdWithdraw());
        getCommand("coinflip").setExecutor(new cmdCoinflip());
        getCommand("leaderboard").setExecutor(new cmdLeaderboard());
        getCommand("insurance").setExecutor(new cmdInsurance());
    }

}
