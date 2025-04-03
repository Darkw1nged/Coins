package me.darkwinged.coins.libraries.struts;

import java.util.*;

public class Lottery {

    private final LotteryType type;
    private final double prizeFund;
    private final Map<Account, List<Ticket>> participants;
    private boolean isClosed;

    public Lottery(LotteryType type) {
        this.type = type;
        this.prizeFund = 0;
        this.participants = new HashMap<>();
        this.isClosed = false;
    }

    // ---- [ Getters ] ----
    public LotteryType getType() {
        return this.type;
    }

    public double getPrizeFund() {
        return this.prizeFund;
    }

    public Map<Account, List<Ticket>> getParticipants() {
        return this.participants;
    }

    public boolean isClosed() {
        return this.isClosed;
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

    public void closeLottery() {
        this.isClosed = true;
    }

    public enum LotteryType {

        LOTTO("Lotto", Arrays.asList("Wednesday", "Saturday")),
        EURO_MILLIONS("Euro Millions", Arrays.asList("Tuesday", "Friday")),
        SET_FOR_LIFE("Set for Life", Arrays.asList("Monday", "Thursday")),
        THUNDERBALL("Thunderball", Arrays.asList("Tuesday", "Wednesday", "Friday", "Saturday"));

        private final String type;
        private final List<String> days;

        LotteryType(String type, List<String> days) {
            this.type = type;
            this.days = days;
        }

        public String getType() {
            return this.type;
        }

        public List<String> getDays() {
            return this.days;
        }

    }

}
