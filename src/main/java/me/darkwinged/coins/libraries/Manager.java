package me.darkwinged.coins.libraries;

import me.darkwinged.coins.Coins;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Manager {

    private static final Coins plugin = Coins.getInstance;
    private static final Map<UUID, Double> coins = new HashMap<>();

    public static boolean hasAccount(UUID uuid) {
        return coins.containsKey(uuid);
    }

    public static Double getPlayerCoins(UUID uuid) {
        return coins.get(uuid);
    }

    public static void insertPlayer(UUID uuid) {
        coins.put(uuid, 100.0);
    }

    public static boolean hasEnoughCoins(UUID uuid, double amount) {
        return coins.containsKey(uuid) && coins.get(uuid) >= amount;
    }

    public static boolean setCoins(UUID uuid, double amount) {
        if (!coins.containsKey(uuid)) return false;
        coins.put(uuid, amount);
        return true;
    }

    public static boolean addCoins(UUID uuid, double amount) {
        if (!coins.containsKey(uuid)) return false;
        coins.put(uuid, coins.get(uuid) + amount);
        return true;
    }

    public static boolean removeCoins(UUID uuid, double amount) {
        if (!coins.containsKey(uuid)) return false;
        coins.put(uuid, coins.get(uuid) - amount);
        return true;
    }

    public static void savePlayer(Player player) {
        if (!hasAccount(player.getUniqueId())) return;
        plugin.getConfig().set("players." + player.getUniqueId() + ".coins", coins.get(player.getUniqueId()));
        plugin.saveConfig();
    }

    public static boolean loadPlayer(Player player) {
        if (!plugin.getConfig().contains("players." + player.getUniqueId())) return false;
        coins.put(player.getUniqueId(), plugin.getConfig().getDouble("players." + player.getUniqueId() + ".coins"));
        return true;
    }

    public static void saveAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            savePlayer(player);
        }
    }

    public static void loadAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            loadPlayer(player);
        }
    }

}
