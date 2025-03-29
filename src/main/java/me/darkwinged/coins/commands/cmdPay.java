package me.darkwinged.coins.commands;

import me.darkwinged.coins.libraries.Account;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdPay implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pay")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Utils.chatColor("&6Coins &8» &cYou are not allowed to use this!"));
                return true;
            }
            if (args.length < 2) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &fUsage: /pay <player> <coins>"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cPlayer not found!"));
                return true;
            }

            Account playerAccount = Manager.getAccount(player.getUniqueId());
            Account targetAccount = Manager.getAccount(target.getUniqueId());

            if (playerAccount == null) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
                return true;
            }
            if (targetAccount == null) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
                return true;
            }

            double amount = Utils.getNumberFromAbbreviation(args[0]);
            if (amount == -1) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cThat is not a valid amount!"));
                return true;
            }

            if (playerAccount.hasEnoughCoins(amount)) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cYou do not have enough coins!"));
                return true;
            }

            playerAccount.removeCoins(amount);
            player.sendMessage(Utils.chatColor("&6Coins &8» &fYou have sent &e" + amount + "&f coins to &a" + target.getName() + "&f!"));

            targetAccount.addCoins(amount);
            target.sendMessage(Utils.chatColor("&6Coins &8» &fYou have received &e" + amount + "&f coins from &a" + player.getName() + "&f!"));
        }
        return true;
    }

}
