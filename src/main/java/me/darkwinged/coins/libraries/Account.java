package me.darkwinged.coins.libraries;

import me.darkwinged.coins.Coins;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.UUID;

public class Account {

    private final Coins plugin = Coins.getInstance;

    private final UUID uuid;
    private final CustomConfig dataFile;
    private double coins;
    private double multiplier;
    private int penalty;
    private int interest;
    private long lastGained;

    public Account(UUID uuid) {
        this.uuid = uuid;
        this.dataFile = new CustomConfig(plugin, "data/" + uuid, "");
        this.coins = 0;
        this.multiplier = 1;
        this.penalty = 5;
        this.interest = 1;
        this.lastGained = System.currentTimeMillis();
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

    public int getInterest() {
        return this.interest;
    }

    public long getLastGained() {
        return this.lastGained;
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

    public void setInterestAmount(int interest) {
        this.interest = interest;
    }

    public void setLastGained(long lastGained) {
        this.lastGained = lastGained;
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

    // ---- [ Save method ] ----
    public void save() {
        YamlConfiguration config = dataFile.getConfig();
        config.set("uuid", this.uuid);
        config.set("coins", this.coins);
        config.set("multiplier", this.multiplier);
        config.set("penalty", this.penalty);
        config.set("interest.percent", this.interest);
        config.set("interest.lastGained", this.lastGained);
        dataFile.saveConfig();
    }

}
