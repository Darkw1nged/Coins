package me.darkwinged.coins.libraries;
import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.Ticket;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import me.darkwinged.coins.libraries.struts.Lottery;
import me.darkwinged.coins.libraries.struts.Lottery.LotteryType;

import java.util.*;

public class LotteryTimer extends BukkitRunnable {

    private final Coins plugin = Coins.getInstance;

    @Override
    public void run() {
        World world = Bukkit.getWorld("world");
        if (world == null) return;

        long totalTimeActive = world.getFullTime();
        long timeOfDay = totalTimeActive % 24000;

        long hourOfDay = (timeOfDay / 1000 + 6) % 24;
        long minutes = (timeOfDay % 1000) * 60 / 1000;

        // Check if it's 7 PM (19:00) or 8 PM (20:00)
        if (hourOfDay == 19 && minutes == 0) {
            closeLottery();
        } else if (hourOfDay == 20 && minutes == 0) {
            drawLottery();
        }
    }

    // Method to close the lottery at 7 PM
    private void closeLottery() {
        for (LotteryType type : LotteryType.values()) {
            Lottery lottery = getLotteryByType(type);
            lottery.closeLottery();
            Bukkit.broadcastMessage("The lottery entries have now closed! No further entries will be accepted.");
        }
    }

    // Method to trigger the lottery draw at 8 PM
    private void drawLottery() {
    }

    private Lottery getLotteryByType(LotteryType type) {
        return new Lottery(type);
    }

    private void drawWinners(Lottery lottery) {
        // TODO get 5 random numbers and check against all tickets
        // TODO tickets with 2 numbers get another chance, 3 numbers 50 coins, 4 numbers 200 coins, 5 numbers 1000 coins
        Map<Account, List<Ticket>> participants = lottery.getParticipants();
        if (participants.isEmpty()) return;

        List<Integer> lotteryNumbers = new ArrayList<>();
        Random random = new Random();
        int generatedNumbers=0;
        while (generatedNumbers < 6) {
            int number = random.nextInt(59);
            if (lotteryNumbers.contains(number)) continue;
            lotteryNumbers.add(number);
            generatedNumbers++;
        }

        Map<Account, Double> rewards = new HashMap<>();
        for (Account account : participants.keySet()) {
            List<Ticket> tickets = participants.get(account);
            for (Ticket ticket : tickets) {
                int matched = 0;
                for (int number : ticket.getNumbers()) {
                    if (lotteryNumbers.contains(number)) {
                        matched++;
                    }
                }

                switch (matched) {
                    case 2 -> {
                        Ticket secondChance = new Ticket(account);
                    }
                    case 3 -> {
                        double rewardTotal = 0;
                        if (rewards.containsKey(account)) {
                            rewardTotal += rewards.get(account);
                        }
                        rewardTotal += 50;
                        rewards.put(account, rewardTotal);
                    }
                    case 4 -> {
                        double rewardTotal = 0;
                        if (rewards.containsKey(account)) {
                            rewardTotal += rewards.get(account);
                        }
                        rewardTotal += 200;
                        rewards.put(account, rewardTotal);
                    }
                    case 5 -> {
                        double rewardTotal = 0;
                        if (rewards.containsKey(account)) {
                            rewardTotal += rewards.get(account);
                        }
                        rewardTotal += 1000;
                        rewards.put(account, rewardTotal);
                    }
                }
            }
        }

    }
}
