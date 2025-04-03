package me.darkwinged.coins.libraries.timers;

import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Lottery;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class LotteryChecker extends BukkitRunnable {

    @Override
    public void run() {
        World world = Bukkit.getWorld("world");
        if (world == null) return;

        String dayOfWeek = Utils.getDay();
        if (dayOfWeek == null) return;

        List<Lottery.LotteryType> lotteryTypes = new ArrayList<>();
        for (Lottery.LotteryType type : Lottery.LotteryType.values()) {
            if (type.getDays().contains(dayOfWeek)) {
                lotteryTypes.add(type);
            }
        }

        if (lotteryTypes.isEmpty()) return;
        long worldTime = world.getTime();
        if (worldTime == 0) {
            Utils.activeLotteries.clear();
            for (Lottery.LotteryType type : lotteryTypes) {
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(Utils.chatColor("&6Lottery &8» &fA new &e" + type.getType() + " &flottery has started! Type /lottery to enter!"));
                Bukkit.broadcastMessage("");
                Utils.activeLotteries.add(new Lottery(type));
            }
        }

    }

}
