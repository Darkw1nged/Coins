package me.darkwinged.coins.libraries;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.Coinflip;
import me.darkwinged.coins.libraries.struts.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
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

    public static boolean loadAccount(UUID uuid) {
        CustomConfig dataFile = new CustomConfig(plugin, "data/" + uuid, "");
        if (!dataFile.getCustomConfigFile().exists()) return false;

        YamlConfiguration config = dataFile.getConfig();
        Account playerAccount = new Account(uuid);

        playerAccount.setCoins(config.getDouble("coins"));
        playerAccount.setMultiplier(config.getDouble("multiplier"));
        playerAccount.setPenalty(config.getInt("penalty"));
        playerAccount.setInterestAmount(config.getInt("interest.percent"));
        playerAccount.setLastGained(config.getLong("interest.lastGained"));
        playerAccount.setCoinflipWins(config.getInt("coinflips.wins"));
        playerAccount.setCoinflipLosses(config.getInt("coinflips.losses"));
        playerAccount.setCoinflipTotalWon(config.getDouble("coinflips.totalWon"));
        playerAccount.setCoinflipTotalLost(config.getDouble("coinflips.totalLost"));
        return true;
    }

    public static void saveAllAccounts() {
        for (Account account : accounts) {
            account.save();
        }
    }

    public static void loadAllAccounts() {
        File folder = new File(plugin.getDataFolder() + "/data");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) return;

        for (File file : listOfFiles) {
            String name = file.getName();
            loadAccount(UUID.fromString(name.split(".yml")[0]));
        }
    }

    public static List<Account> top10Players() {
        List<Account> result = new ArrayList<>();

        int positionToFind = 1;
        while (positionToFind < Integer.min(accounts.size(), 10)) {
            double maximum = Double.MIN_VALUE;
            Account nextAccount = null;

            for (Account account : accounts) {
                if (result.contains(account)) continue;
                if (account.getCoins() > maximum) {
                    maximum = account.getCoins();
                    nextAccount = account;
                }
            }
            result.add(nextAccount);
            positionToFind++;
        }

        return result;
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
