package me.darkwinged.coins.libraries.timers;

import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Lottery;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("all")
public class LotteryChecker extends BukkitRunnable {

    @Override
    public void run() {
        World world = Bukkit.getWorld("world");
        if (world == null) return;

        if (Utils.activeLotteries.size() >= 1) return;
        Utils.activeLotteries.clear();

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(Utils.chatColor("&6Lottery &8» &fA new lottery has started! Type &e/lottery &fto enter!"));
        Bukkit.broadcastMessage("");
        Utils.activeLotteries.add(new Lottery());
    }

}
