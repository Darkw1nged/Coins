package me.darkwinged.coins.libraries;

import me.darkwinged.coins.Coins;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class Utils {

    private static final Coins plugin = Coins.getInstance;

    // ---- [ Lists and Maps ] ----
    public static final HashMap<UUID, Integer> TeleportDelay = new HashMap<>();
    public static final HashMap<UUID, Integer> Cooldown = new HashMap<>();

    // ---- [ Managing chat color within the plugin ] ----
    public static String chatColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    // ---- [ Managing chat color within the plugin | Supports Amount ] ----
    public static String chatColor(String s, Double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        String converted = nf.format(amount);
        return ChatColor.translateAlternateColorCodes('&', s)
                .replaceAll("%amount%", converted);
    }

    // ---- [ Format numbers ] ----
    public static String format(Double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

    public static String format(Integer amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return nf.format(amount);
    }

    // ---- [ Check if a string is a number ] ----
    public static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // ---- [ Get number from abbreviation ] ----
    public static double getNumberFromAbbreviation(String s) {
        if (s == null || s.isEmpty()) return -1;
        if (!isNumber(s.substring(0, s.length() - 1))) return -1;
        if (s.length() == 1) return Double.parseDouble(s);
        char lastChar = s.charAt(s.length() - 1);
        double number = Double.parseDouble(s.substring(0, s.length() - 1));
        return switch (lastChar) {
            case 'k' -> number * 1000;
            case 'm' -> number * 1000000;
            case 'b' -> number * 1000000000;
            case 't' -> number * 1000000000000.0;
            case 'q' -> number * 1000000000000000.0;
            case 'Q' -> number * 1000000000000000000.0;
            case 's' -> number * 1000000000000000000000.0;
            case 'S' -> number * 1000000000000000000000000.0;
            case 'o' -> number * 1000000000000000000000000000.0;
            case 'n' -> number * 1000000000000000000000000000000.0;
            default -> Double.parseDouble(s);
        };
    }

    // ---- [ Check for combat tag via combat_tag ] ----
    public static boolean isCombatTagged(UUID uuid) {
        Plugin combatTag = plugin.getServer().getPluginManager().getPlugin("combat_tag");
        if (combatTag == null) return false;
        return combatTag.isEnabled() && combatTag.getConfig().getBoolean("combat-tag." + uuid + ".tagged");
    }

}
