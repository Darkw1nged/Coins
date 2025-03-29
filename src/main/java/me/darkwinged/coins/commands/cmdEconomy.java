package me.darkwinged.coins.commands;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Account;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdEconomy implements CommandExecutor {

    private final Coins plugin = Coins.getInstance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("economy")) {
            if (sender instanceof Player player) {
                player.sendMessage(Utils.chatColor("&6Coins &8» &cYou are not allowed to use this!"));
                return true;
            }

            if (args.length < 3) {
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fUsage: &e/economy <add|remove|set|> <player> <amount>"));
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fUsage: &e/economy multiplier <player|server> <multiplier>"));
                return true;
            }

            if (args[0].equalsIgnoreCase("multiplier")) {
                if (args[1].equalsIgnoreCase("server")) {
                    if (!Utils.isNumber(args[2])) {
                        sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlease provide a valid number!"));
                        return true;
                    }

                    double multiplier = Double.parseDouble(args[2]);
                    plugin.getConfig().set("server-multiplier", multiplier);
                    plugin.saveConfig();

                    sender.sendMessage(Utils.chatColor("&6Coins &8» &aYou have set the server multiplier to &e" + multiplier + "&f!"));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlayer not found!"));
                    return true;
                }

                Account account = Manager.getAccount(target.getUniqueId());
                if (account == null) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
                    return true;
                }

                if (!Utils.isNumber(args[2])) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlease provide a valid number!"));
                    return true;
                }

                double multiplier = Double.parseDouble(args[2]);
                account.setMultiplier(multiplier);
                sender.sendMessage(Utils.chatColor("&6Coins &8» &aYou have set &e" + target.getName() + "'s &fmultiplier to &e" + multiplier + "&f!"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlayer not found!"));
                return true;
            }

            Account account = Manager.getAccount(target.getUniqueId());
            if (account == null) {
                sender.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
                return true;
            }

            double amount = Utils.getNumberFromAbbreviation(args[2]);

            if (args[0].equalsIgnoreCase("add")) {
                double multiplier = plugin.getConfig().getDouble("server-multiplier") + account.getMultiplier();
                account.addCoins(amount * multiplier);
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully updated &e" + target.getName() + " &fbalance!"));

            } else if (args[0].equalsIgnoreCase("remove")) {
                account.removeCoins(amount);
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully updated &e" + target.getName() + " &fbalance!"));
            } else if (args[0].equalsIgnoreCase("set")) {
                account.setCoins(amount);
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully updated &e" + target.getName() + " &fbalance!"));
            }

        }
        return true;
    }
}
