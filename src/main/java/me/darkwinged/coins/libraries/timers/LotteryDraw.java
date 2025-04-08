package me.darkwinged.coins.libraries.timers;
import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.Ticket;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import me.darkwinged.coins.libraries.struts.Lottery;

import java.util.*;

@SuppressWarnings("all")
public class LotteryDraw extends BukkitRunnable {

    @Override
    public void run() {
        World world = Bukkit.getWorld("world");
        if (world == null) return;

        String worldTime = Utils.getCurrentTimeOfDay();
        if (worldTime == null || worldTime.isEmpty()) return;
        String hour = worldTime.split(":")[0];

        switch (hour) {
            case "19" -> closeActiveLotteries();
            case "20" -> drawAndClearLotteries();
        }
    }

    private void closeActiveLotteries() {
        for (Lottery lottery : Utils.activeLotteries) {
            if (lottery.isClosed()) continue;

            lottery.closeLottery();
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(Utils.chatColor("&6Lottery &8» &fThe lottery entries have now closed! No further entries will be accepted."));
            Bukkit.broadcastMessage("");
        }
    }

    private void drawAndClearLotteries() {
        // Create a new list to avoid ConcurrentModificationException
        List<Lottery> toRemove = new ArrayList<>();

        for (Lottery lottery : Utils.activeLotteries) {
            if (!lottery.isClosed()) continue;

            int winners = drawWinners(lottery);
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(Utils.chatColor("&6Lottery &8» &fThe lottery winners have been drawn!"));
            Bukkit.broadcastMessage(Utils.chatColor("&6Lottery &8» &fThere were &a" + winners + " &fwinners!"));
            Bukkit.broadcastMessage("");

            toRemove.add(lottery);
        }

        Utils.activeLotteries.removeAll(toRemove);
    }

    private int drawWinners(Lottery lottery) {
        Map<Account, List<Ticket>> participants = lottery.getParticipants();
        if (participants.isEmpty()) return 0;

        // Generate 6 unique random numbers
        List<Integer> lotteryNumbers = new ArrayList<>();
        Random random = new Random();
        while (lotteryNumbers.size() < 6) {
            int number = random.nextInt(59);
            if (!lotteryNumbers.contains(number)) {
                lotteryNumbers.add(number);
            }
        }

        double prizeFund = lottery.getPrizeFund();
        boolean jackpotWon = false;
        Map<Account, Double> rewards = new HashMap<>();

        // Calculate matches and determine winners
        for (Map.Entry<Account, List<Ticket>> entry : participants.entrySet()) {
            Account account = entry.getKey();
            List<Ticket> tickets = entry.getValue();

            for (Ticket ticket : tickets) {
                int matched = 0;
                for (int number : ticket.getNumbers()) {
                    if (lotteryNumbers.contains(number)) matched++;
                }

                // If there's a jackpot winner, mark it and break
                if (matched == 5) {
                    jackpotWon = true;
                    rewards.put(account, prizeFund); // Full prize fund to jackpot winner(s)
                } else if (!jackpotWon) {
                    // Only allow lower-tier rewards if no jackpot winner
                    switch (matched) {
                        case 4 -> rewards.merge(account, prizeFund * 0.25, Double::sum);
                        case 3 -> rewards.merge(account, prizeFund * 0.1, Double::sum);
                    }
                }
            }
        }

        // Distribute rewards
        double totalPayout = 0;
        for (Map.Entry<Account, Double> rewardEntry : rewards.entrySet()) {
            Account account = rewardEntry.getKey();
            double reward = rewardEntry.getValue();
            if (reward > 0) {
                account.addCoins(reward);
                totalPayout += reward;
            }
        }

        // Update prize fund or rollover
        if (!jackpotWon) {
        } else {
            double newFund = prizeFund - totalPayout;
            Coins.getInstance.getConfig().set("lottery-rollover", Math.max(newFund, 0));
        }

        return rewards.size();
    }

}
