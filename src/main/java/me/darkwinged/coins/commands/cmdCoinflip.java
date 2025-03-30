package me.darkwinged.coins.commands;

import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.Coinflip;
import me.darkwinged.coins.menus.menuCoinflips;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdCoinflip implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("coinflip")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor("&6Coins &8» &cYou are not allowed to use this!"));
                return true;
            }
            Player player = (Player)sender;
            if (args.length < 1) {
                new menuCoinflips(player).open();
                return true;
            }

            if (!args[0].equalsIgnoreCase("create")) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &fUsage: /coinflip create <amount> <choice>"));
                return true;
            }

            Account account = Manager.getAccount(player.getUniqueId());
            if (account == null) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
                return true;
            }

            if (!Utils.isNumber(args[1])) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cPlease provide a valid number!"));
                return true;
            }

            double amount = Utils.getNumberFromAbbreviation(args[1]);
            if (!account.hasEnoughCoins(amount)) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cYou do not have enough coins!"));
                return true;
            }

            String choice = args[2];
            if (choice.equalsIgnoreCase("red") || choice.equalsIgnoreCase("blue") ||
                    choice.equalsIgnoreCase("green") || choice.equalsIgnoreCase("white")) {

                Coinflip cf = new Coinflip(player.getUniqueId(), amount, choice);
                Manager.insertNewCoinflip(cf);
                account.removeCoins(amount);
                player.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successully created a new coinflip for &a" + amount));
            } else {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cPlease provide a valid choice. &cRed &9Blue &aGreen &fWhite"));
                return true;
            }
        }
        return true;
    }

}
