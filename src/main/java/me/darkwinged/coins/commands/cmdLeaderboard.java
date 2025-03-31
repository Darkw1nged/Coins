package me.darkwinged.coins.commands;

import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class cmdLeaderboard implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("leaderboard")) {
            sender.sendMessage(Utils.chatColor("&e--------{ &fTop 10 Coin &e}--------"));

            List<Account> top10 = Manager.top10Players();
            int pos=1;
            for (Account account : top10) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(account.getUuid());
                sender.sendMessage(Utils.chatColor("&e#" + pos + " &f" + player.getName() + ": &a" + account.getCoins()));
            }

            sender.sendMessage(Utils.chatColor("&e----------------------------"));

        }
        return true;
    }

}
