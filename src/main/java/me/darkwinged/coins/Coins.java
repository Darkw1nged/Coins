package me.darkwinged.coins;

import me.darkwinged.coins.commands.cmdBalance;
import me.darkwinged.coins.commands.cmdEconomy;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.listeners.DataProcessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Coins extends JavaPlugin {

    public static Coins getInstance;

    public void onEnable() {
        getInstance = this;

        getCommand("balance").setExecutor(new cmdBalance());
        getCommand("economy").setExecutor(new cmdEconomy());

        getServer().getPluginManager().registerEvents(new DataProcessEvent(), this);
        Manager.loadAllPlayers();

        getServer().getConsoleSender().sendMessage(Utils.chatColor("&6Coins &8» &aPlugin has been enabled!"));
    }

    public void onDisable() {
        Manager.saveAllPlayers();
        getServer().getConsoleSender().sendMessage(Utils.chatColor("&6Coins &8» &cPlugin has been disabled!"));
    }
}
