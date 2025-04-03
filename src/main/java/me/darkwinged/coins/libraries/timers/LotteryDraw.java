package me.darkwinged.coins.libraries.timers;
import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.Ticket;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import me.darkwinged.coins.libraries.struts.Lottery;
import me.darkwinged.coins.libraries.struts.Lottery.LotteryType;

import java.util.*;

public class LotteryDraw extends BukkitRunnable {

    private final Coins plugin = Coins.getInstance;

    @Override
    public void run() {
        World world = Bukkit.getWorld("world");
        if (world == null) return;

        String worldTime = Utils.getCurrentTimeOfDay();
        if (worldTime.equalsIgnoreCase("")) return;

        if (worldTime.equalsIgnoreCase("19:00")) {
            for (Lottery lottery : Utils.activeLotteries) {
                if (lottery.isClosed()) continue;

                lottery.closeLottery();
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(Utils.chatColor("&6Lottery &8» &fThe lottery entries for &e" + lottery.getType().getType() + "&f have now closed! No further entries will be accepted."));
                Bukkit.broadcastMessage("");
            }
        } else if (worldTime.equalsIgnoreCase("20:00")) {
            for (Lottery lottery : Utils.activeLotteries) {
                if (!lottery.isClosed()) continue;
                drawWinners(lottery);
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(Utils.chatColor("&6Lottery &8» &fThe lottery winners for &e" + lottery.getType().getType() + "&f have been drawn!"));
                Bukkit.broadcastMessage("");
            }
        }
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
