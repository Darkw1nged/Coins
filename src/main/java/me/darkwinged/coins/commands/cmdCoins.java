package me.darkwinged.coins.commands;

import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdCoins implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("coins")) {
            if (!(sender instanceof Player player)) {
                if (args.length < 1) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlease provide a player!"));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlayer not found!"));
                    return true;
                }

                Account targetAccount = Manager.getAccount(target.getUniqueId());
                if (targetAccount == null) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
                    return true;
                }

                sender.sendMessage(Utils.chatColor("&6Coins &8» &e" + target.getName() + " &fbalance: &a" + Utils.format(targetAccount.getCoins())));
                return true;
            }

            Account playerAccount = Manager.getAccount(player.getUniqueId());
            if (playerAccount == null) {
                sender.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
                 return true;
            }

            if (args.length < 1) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &fBalance: &a" + Utils.format(playerAccount.getCoins())));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlayer not found!"));
                return true;
            }

            Account targetAccount = Manager.getAccount(target.getUniqueId());
            if (targetAccount == null) {
                sender.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
                return true;
            }

            sender.sendMessage(Utils.chatColor("&6Coins &8» &e" + target.getName() + " &fbalance: &a" + Utils.format(targetAccount.getCoins())));
            return true;
        }
        return true;
    }

}
