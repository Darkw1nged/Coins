package me.darkwinged.coins.commands;

import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class cmdWithdraw implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("withdraw")) {
            if (args.length < 1) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &fUsage: &e/withdraw <amount> <player>"));
                    return true;
                }
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fUsage: &e/withdraw <amount>"));
                return true;
            }

            if (!Utils.isNumber(args[2])) {
                sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlease provide a valid number!"));
                return true;
            }
            double amount = Utils.getNumberFromAbbreviation(args[0]);

            if (!(sender instanceof Player)) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlayer not found!"));
                    return true;
                }

                ItemStack satchel = CustomItems.satchel("server", amount);
                if (target.getInventory().firstEmpty() == -1) {
                    target.getWorld().dropItem(target.getLocation(), satchel);
                    target.sendMessage(Utils.chatColor("&6Coins &8» &fYou were given a satchel but never had enough space, so it has been dropped on the ground!"));
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully given a satchel to &e" + target.getName()));
                    return true;
                }
                target.getInventory().addItem(satchel);
                target.sendMessage(Utils.chatColor("&6Coins &8» &fYou were given a satchel"));
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully given a satchel to &e" + target.getName()));
                return true;
            }
            Player player = (Player)sender;
            Account account = Manager.getAccount(player.getUniqueId());
            if (account == null) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
                return true;
            }

            if (!account.hasEnoughCoins(amount)) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cYou do not have enough coins for this!"));
                return true;
            }

            ItemStack satchel = CustomItems.satchel(player.getName(), amount);
            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItem(player.getLocation(), satchel);
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully withdrawn &a" + amount));
                return true;
            }
            player.getInventory().addItem(satchel);
            sender.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully withdrawn &a" + amount));
            return true;

        }
        return true;
    }

}
