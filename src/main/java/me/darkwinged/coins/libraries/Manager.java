package me.darkwinged.coins.libraries;

import me.darkwinged.coins.Coins;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class Manager {

    private static final Coins plugin = Coins.getInstance;
    private static final List<Account> accounts = new ArrayList<>();

    public static boolean hasAccount(UUID uuid) {
        if (accounts.isEmpty()) return false;
        for (Account account : accounts) {
            if (account.getUuid().equals(uuid)) return true;
        }
        return false;
    }

    public static Account getAccount(UUID uuid) {
        if (accounts.isEmpty()) return null;
        for (Account account : accounts) {
            if (account.getUuid().equals(uuid)) return account;
        }
        return null;
    }

    public static void insertPlayer(UUID uuid) {
        Account newAccount = new Account(uuid);
        newAccount.setCoins(100);
        accounts.add(newAccount);
    }

    public static boolean loadPlayer(Player player) {
        CustomConfig dataFile = new CustomConfig(plugin, "data/" + player.getUniqueId(), "");
        if (!dataFile.getCustomConfigFile().exists()) return false;

        YamlConfiguration config = dataFile.getConfig();
        Account playerAccount = new Account(player.getUniqueId());

        playerAccount.setCoins(config.getDouble("coins"));
        playerAccount.setMultiplier(config.getDouble("multiplier"));
        playerAccount.setInterestAmount(config.getInt("interest.percent"));
        playerAccount.setLastGained(config.getLong("interest.lastGained"));
        return true;
    }

    public static void saveAllPlayers() {
        for (Account account : accounts) {
            account.save();
        }
    }

    public static void loadAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            loadPlayer(player);
        }
    }

}
