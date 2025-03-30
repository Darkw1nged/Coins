package me.darkwinged.coins.libraries.struts;

import java.util.UUID;

public class Coinflip {

    private final UUID gameID;
    private final UUID owner;
    private UUID opponent;
    private final double worth;
    private final Choice choice;

    public Coinflip(UUID owner, double amount, String choice) {
        this.gameID = UUID.randomUUID();
        this.owner = owner;
        this.opponent = null;
        this.worth = amount;
        this.choice = Choice.valueOf(choice);
    }

    public Coinflip(UUID gameID, UUID owner, double amount, String choice) {
        this.gameID = gameID;
        this.owner = owner;
        this.opponent = null;
        this.worth = amount;
        this.choice = Choice.valueOf(choice);
    }

    // ---- [ Getters ] ----
    public UUID getGameID() {
        return this.gameID;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public UUID getOpponent() {
        return this.opponent;
    }

    public double getWorth() {
        return this.worth;
    }

    public Choice getChoice() {
        return this.choice;
    }

    // ---- [ Setters ] ----
    public void setOpponent(UUID opponent) {
        this.opponent = opponent;
    }

    public enum Choice {

        RED("Red"),
        GREEN("Green"),
        BLUE("Blue"),
        WHITE("White");

        private final String color;

        private Choice(String color) {
            this.color = color;
        }

        public String getColor() {
            return this.color;
        }
    }

}
