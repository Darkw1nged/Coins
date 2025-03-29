package me.darkwinged.coins.commands;

import me.darkwinged.coins.Coins;
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

                if (!Utils.isNumber(args[2])) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlease provide a valid number!"));
                    return true;
                }

                double multiplier = Double.parseDouble(args[2]);
                plugin.getConfig().set("players." + target.getUniqueId() + ".multiplier", multiplier);
                plugin.saveConfig();
                sender.sendMessage(Utils.chatColor("&6Coins &8» &aYou have set &e" + target.getName() + "'s &fmultiplier to &e" + multiplier + "&f!"));
                return true;
            } else if (args[0].equalsIgnoreCase("add")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlayer not found!"));
                    return true;
                }

                double amount = Utils.getNumberFromAbbreviation(args[2]);
                double multiplier = plugin.getConfig().getDouble("server-multiplier") + plugin.getConfig().getDouble("players." + target.getUniqueId() + ".multiplier");
                Manager.addCoins(target.getUniqueId(), amount * multiplier);
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully updated &e" + target.getName() + " &fbalance!"));

            } else if (args[0].equalsIgnoreCase("remove")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlayer not found!"));
                    return true;
                }

                double amount = Utils.getNumberFromAbbreviation(args[2]);
                Manager.removeCoins(target.getUniqueId(), amount);
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully updated &e" + target.getName() + " &fbalance!"));
            } else if (args[0].equalsIgnoreCase("set")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chatColor("&6Coins &8» &cPlayer not found!"));
                    return true;
                }

                double amount = Utils.getNumberFromAbbreviation(args[2]);
                Manager.setCoins(target.getUniqueId(), amount);
                sender.sendMessage(Utils.chatColor("&6Coins &8» &fYou have successfully updated &e" + target.getName() + " &fbalance!"));
            }

        }
        return true;
    }
}
