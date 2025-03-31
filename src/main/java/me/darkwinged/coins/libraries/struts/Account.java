package me.darkwinged.coins.libraries.struts;

import me.darkwinged.coins.Coins;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Account {

    private final Coins plugin = Coins.getInstance;

    private final UUID uuid;
    private final CustomConfig dataFile;
    private double coins;
    private double multiplier;
    private int penalty;
    private Date insurance;
    private int interest;
    private long lastGained;

    // --- [ Coinflip Stats ] ---
    private int coinflipWins;
    private int coinflipLosses;
    private double coinflipTotalWon; // Total amount of coins won from coinflips
    private double coinflipTotalLost; // Total amount of coins lost from coinflips

    public Account(UUID uuid) {
        this.uuid = uuid;
        this.dataFile = new CustomConfig(plugin, "data/" + uuid, "");
        this.coins = 0;
        this.multiplier = 1;
        this.penalty = 50;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        this.insurance = calendar.getTime();

        this.interest = 1;
        this.lastGained = System.currentTimeMillis();

        // Coinflip stats
        this.coinflipWins = 0;
        this.coinflipLosses = 0;
        this.coinflipTotalWon = 0;
        this.coinflipTotalLost = 0;
    }

    // ---- [ Getters ] ----
    public UUID getUuid() {
        return this.uuid;
    }

    public double getCoins() {
        return this.coins;
    }

    public double getMultiplier() {
        return this.multiplier;
    }

    public int getPenalty() {
        return this.penalty;
    }

    public Date getInsurance() {
        return this.insurance;
    }

    public int getInterest() {
        return this.interest;
    }

    public long getLastGained() {
        return this.lastGained;
    }

    public int getCoinflipWins() {
        return this.coinflipWins;
    }

    public int getCoinflipLosses() {
        return this.coinflipLosses;
    }

    public double getCoinflipTotalWon() {
        return this.coinflipTotalWon;
    }

    public double getCoinflipTotalLost() {
        return this.coinflipTotalLost;
    }

    // ---- [ Setters ] ----
    public void setCoins(double coins) {
        this.coins = coins;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public void setInsurance(Date insurance) {
        this.insurance = insurance;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public void setLastGained(long lastGained) {
        this.lastGained = lastGained;
    }

    public void setCoinflipWins(int coinflipWins) {
        this.coinflipWins = coinflipWins;
    }

    public void setCoinflipLosses(int coinflipLosses) {
        this.coinflipLosses = coinflipLosses;
    }

    public void setCoinflipTotalWon(double coinflipTotalWon) {
        this.coinflipTotalWon = coinflipTotalWon;
    }

    public void setCoinflipTotalLost(double coinflipTotalLost) {
        this.coinflipTotalLost = coinflipTotalLost;
    }

    // ---- [ Helper Methods ] ----
    public boolean hasEnoughCoins(double coins) {
        return this.coins >= coins;
    }

    public void addCoins(double coins) {
        this.coins += coins;
    }

    public void removeCoins(double coins) {
        this.coins -= coins;
    }

    public double addInterest() {
        double toAdd = (this.coins / 100) * this.interest;
        this.coins += toAdd;
        this.lastGained = System.currentTimeMillis();
        return toAdd;
    }

    public double applyPenalty() {
        double toTake = (this.coins / 100) * this.penalty;
        this.coins -= toTake;
        return toTake;
    }

    public void addCoinflipWin() {
        this.coinflipWins++;
    }

    public void addCoinflipLoss() {
        this.coinflipLosses++;
    }

    public void addCoinflipTotalWon(double coins) {
        this.coinflipTotalWon += coins;
    }

    public void addCoinflipTotalLost(double coins) {
        this.coinflipTotalLost += coins;
    }

    // ---- [ Save method ] ----
    public void save() {
        YamlConfiguration config = dataFile.getConfig();
        config.set("uuid", this.uuid.toString());
        config.set("coins", this.coins);
        config.set("multiplier", this.multiplier);
        config.set("penalty", this.penalty);
        config.set("insurance", this.insurance);
        config.set("interest.percent", this.interest);
        config.set("interest.lastGained", this.lastGained);
        config.set("coinflip.wins", this.coinflipWins);
        config.set("coinflip.losses", this.coinflipLosses);
        config.set("coinflip.totalWon", this.coinflipTotalWon);
        config.set("coinflip.totalLost", this.coinflipTotalLost);
        dataFile.saveConfig();
    }

}
