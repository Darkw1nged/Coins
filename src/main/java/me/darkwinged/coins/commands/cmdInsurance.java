package me.darkwinged.coins.commands;

import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.menus.menuInsurance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdInsurance implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("insurance")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chatColor("&6Coins &8» &cYou are not allowed to use this!"));
                return true;
            }
            Player player = (Player)sender;
            new menuInsurance(player).open();
        }
        return true;
    }

}