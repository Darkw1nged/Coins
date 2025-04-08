package me.darkwinged.coins.libraries;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.struts.Lottery;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.*;

public class Utils {

    private static final Coins plugin = Coins.getInstance;

    // ---- [ Lists and Maps ] ----
    public static Map<ArmorStand, Long> activeHolograms = new HashMap<>();
    public static List<Lottery> activeLotteries = new ArrayList<>();

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

    // ---- [ Managing holograms for small amount of features ] ----
    public static void moveUpHologram(String name, Location loc, int length) {
        ArmorStand holo = loc.getWorld().spawn(loc, ArmorStand.class);

        // ---- [ Settings flags for entity ] ----
        holo.setCustomName(chatColor(name));
        holo.setCustomNameVisible(true);
        holo.setGravity(false);
        holo.setInvisible(true);
        holo.setInvulnerable(false);
        holo.setSmall(true);
        holo.setArms(false);
        holo.setBasePlate(false);
        holo.setMetadata("hologram", new FixedMetadataValue(plugin, UUID.randomUUID().toString()));

        activeHolograms.put(holo, System.currentTimeMillis());
        new BukkitRunnable() {
            public void run() {
                if (!activeHolograms.isEmpty() && activeHolograms.containsKey(holo)) {
                    long timeLeft = ((activeHolograms.get(holo) / 1000) + length) - (System.currentTimeMillis() / 1000);
                    if (timeLeft <= 0) {
                        activeHolograms.remove(holo);
                        holo.remove();
                        cancel();
                    } else {
                        holo.teleport(new Location(holo.getWorld(), holo.getLocation().getX(), holo.getLocation().getY() + .01, holo.getLocation().getZ()));
                    }
                }
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    // ---- [ Check for combat tag via combat_tag ] ----
    public static boolean isCombatTagged(UUID uuid) {
        Plugin combatTag = plugin.getServer().getPluginManager().getPlugin("combat_tag");
        if (combatTag == null) return false;
        return combatTag.isEnabled() && combatTag.getConfig().getBoolean("combat-tag." + uuid + ".tagged");
    }

    public static String getCurrentTimeOfDay() {
        World world = Bukkit.getWorld("world");
        if (world == null) return "00:00"; // Fallback in case the world is not found
        long totalTimeActive = world.getFullTime();

        // Get the time of day (this will be the time modulo 24000 to get it within a single day cycle)
        long timeOfDay = totalTimeActive % 24000;

        // Calculate the hour of the day in Minecraft. Minecraft starts at 6:00 AM (0 ticks) as "morning".
        long hours = (timeOfDay / 1000 + 6) % 24; // Add 6 to make it 6:00 AM at tick 0
        long minutes = (timeOfDay % 1000) * 60 / 1000; // Get the minutes from the remainder

        // Format the time in 24-hour format
        return String.format("%02d:%02d", hours, minutes);
    }

}
