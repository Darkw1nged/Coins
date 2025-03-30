package me.darkwinged.coins.libraries;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.Coinflip;
import me.darkwinged.coins.libraries.struts.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Manager {

    // ---- [ Declarations ] ----
    private static final Coins plugin = Coins.getInstance;
    private static final List<Account> accounts = new ArrayList<>();
    private static final List<Coinflip> coinflips = new ArrayList<>();

    // ---- [ Economy ] ----
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

    // ---- [ Coinflips ] ----
    public static void insertNewCoinflip(Coinflip cf) {
        coinflips.add(cf);
    }

    public static List<Coinflip> getCoinflips() {
        return coinflips;
    }

    public static Coinflip getCoinflip(UUID gameID) {
        for (Coinflip cf : coinflips) {
            if (cf.getGameID().equals(gameID)) return cf;
        }
        return null;
    }

    public static void removeCoinflip(UUID gameID) {
        for (Coinflip cf : coinflips) {
            if (!cf.getGameID().equals(gameID)) continue;
            coinflips.remove(cf);
        }
    }

    public static void saveCoinflips() {
        CustomConfig dataFile = new CustomConfig(plugin, "coinflips", "");
        YamlConfiguration config = dataFile.getConfig();
        for (Coinflip cf : coinflips) {
            config.set("games." + cf.getGameID() + ".Owner", cf.getOwner());
            config.set("games." + cf.getGameID() + ".Worth", cf.getWorth());
            config.set("games." + cf.getGameID() + ".Choice", cf.getChoice());
        }
        dataFile.saveConfig();
    }

    public static void loadCoinflips() {
        CustomConfig dataFile = new CustomConfig(plugin, "coinflips", "");
        YamlConfiguration config = dataFile.getConfig();

        for (String key : config.getConfigurationSection("games").getKeys(false)) {
            UUID owner = UUID.fromString(config.getString("games." + key + ".Owner"));
            double worth = config.getDouble("games." + key + ".Worth");
            String choice = config.getString("games." + key + ".Choice");
            Coinflip cf = new Coinflip(UUID.fromString(key), owner, worth, choice);
            coinflips.add(cf);
        }
    }

}
