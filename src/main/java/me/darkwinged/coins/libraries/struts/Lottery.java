package me.darkwinged.coins.libraries.struts;

import me.darkwinged.coins.Coins;

import java.util.*;

public class Lottery {

    private double prizeFund;
    private final Map<Account, List<Ticket>> participants;
    private boolean isClosed;

    public Lottery() {
        this.prizeFund = 0;
        this.participants = new HashMap<>();
        this.isClosed = false;
    }

    // ---- [ Getters ] ----

    public double getPrizeFund() {
        return this.prizeFund;
    }

    public Map<Account, List<Ticket>> getParticipants() {
        return this.participants;
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    // ---- [ Setters ] ----
    public void updatePrizeFund() {
        double rollover = Coins.getInstance.getConfig().getDouble("lottery-rollover");

        int totalTickets = participants.values().stream()
                .mapToInt(List::size)
                .sum();

        this.prizeFund += totalTickets * 50;
        this.prizeFund += rollover;
    }

    public void closeLottery() {
        this.isClosed = true;
    }

    // ---- [ Participants Helper Methods ] ----
    public List<Ticket> getParticipantsTicket(Account account) {
        return participants.get(account);
    }

    public void addParticipant(Account account) {
        List<Ticket> tickets = participants.get(account);
        tickets.add(new Ticket(account));
        participants.put(account, tickets);
    }

}
