package me.darkwinged.coins.libraries.vault;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.struts.Account;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class CoinEconomy implements Economy {

    private final static Coins plugin = Coins.getInstance;

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "Coins";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return -1;
    }

    @Override
    public String format(double v) {
        return "null";
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) return false;
        return Manager.hasAccount(player.getUniqueId());
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return Manager.hasAccount(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean hasAccount(String playerName, String world) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) return false;
        return Manager.hasAccount(player.getUniqueId());
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String world) {
        return Manager.hasAccount(offlinePlayer.getUniqueId());
    }

    @Override
    public double getBalance(String s) {
        Player player = Bukkit.getPlayer(s);
        if (player == null) return 0;

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return 0;
        return account.getCoins();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        Account account = Manager.getAccount(offlinePlayer.getUniqueId());
        if (account == null) return 0;
        return account.getCoins();
    }

    @Override
    public double getBalance(String s, String s1) {
        Player player = Bukkit.getPlayer(s);
        if (player == null) return 0;

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return 0;
        return account.getCoins();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        Account account = Manager.getAccount(offlinePlayer.getUniqueId());
        if (account == null) return 0;
        return account.getCoins();
    }

    @Override
    public boolean has(String s, double v) {
        Player player = Bukkit.getPlayer(s);
        if (player == null) return false;

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return false;
        return account.hasEnoughCoins(v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        Account account = Manager.getAccount(offlinePlayer.getUniqueId());
        if (account == null) return false;
        return account.hasEnoughCoins(v);
    }

    @Override
    public boolean has(String s, String s1, double v) {
        Player player = Bukkit.getPlayer(s);
        if (player == null) return false;

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return false;
        return account.hasEnoughCoins(v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        Account account = Manager.getAccount(offlinePlayer.getUniqueId());
        if (account == null) return false;
        return account.hasEnoughCoins(v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double amount) {
        Player player = Bukkit.getPlayer(s);
        if (player == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No player found.");

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No account found.");

        if (amount < 0)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds.");
        if (!has(player, amount))
            return new EconomyResponse(0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");

        account.removeCoins(amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No account found.");

        if (amount < 0)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds.");
        if (!has(player, amount))
            return new EconomyResponse(0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");

        account.removeCoins(amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double amount) {
        Player player = Bukkit.getPlayer(s);
        if (player == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No player found.");

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No account found.");

        if (amount < 0)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds.");
        if (!has(player, amount))
            return new EconomyResponse(0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");

        account.removeCoins(amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String s, double amount) {
        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No account found.");

        if (amount < 0)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds.");
        if (!has(player, amount))
            return new EconomyResponse(0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");

        account.removeCoins(amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double amount) {
        Player player = Bukkit.getPlayer(s);
        if (player == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No player found.");

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No account found.");

        if (amount < 0)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds.");

        account.addCoins(amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No account found.");

        if (amount < 0)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds.");

        account.addCoins(amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double amount) {
        Player player = Bukkit.getPlayer(s);
        if (player == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No player found.");

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No account found.");

        if (amount < 0)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds.");

        account.addCoins(amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String s, double amount) {
        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "No account found.");

        if (amount < 0)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds.");

        account.addCoins(amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return List.of();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
