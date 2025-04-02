package me.darkwinged.coins.listeners;

import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ScavengeEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) return;

        double toGive = Math.random() * 200 + 25;

        switch (block.getType()) {
            case AZALEA:
            case BAMBOO:
            case BEETROOTS:
            case BIG_DRIPLEAF:
            case CACTUS:
            case CARROTS:
            case CAVE_VINES:
            case CHORUS_FLOWER:
            case CHORUS_PLANT:
            case COCOA:
            case DEAD_BUSH:
            case FERN:
            case DANDELION:
            case POPPY:
            case BLUE_ORCHID:
            case ALLIUM:
            case AZURE_BLUET:
            case ORANGE_TULIP:
            case PINK_TULIP:
            case RED_TULIP:
            case WHITE_TULIP:
            case OXEYE_DAISY:
            case CORNFLOWER:
            case LILY_OF_THE_VALLEY:
            case WITHER_ROSE:
            case SUNFLOWER:
            case LILAC:
            case ROSE_BUSH:
            case PEONY:
            case SHORT_GRASS:
            case TALL_GRASS:
            case HANGING_ROOTS:
            case LILY_PAD:
            case MELON:
            case MELON_STEM:
            case MOSS_BLOCK:
            case MOSS_CARPET:
            case POTATOES:
            case PUMPKIN:
            case PUMPKIN_STEM:
            case SEAGRASS:
            case SMALL_DRIPLEAF:
            case SPORE_BLOSSOM:
            case SUGAR_CANE:
            case SWEET_BERRY_BUSH:
            case WHEAT:
            case BEETROOT:
            case CARROT:
            case POTATO:
            case BAMBOO_SAPLING:
            case COCOA_BEANS:
            case SWEET_BERRIES:
            case MUSHROOM_STEM:
            case BROWN_MUSHROOM:
            case RED_MUSHROOM:
            case BROWN_MUSHROOM_BLOCK:
            case RED_MUSHROOM_BLOCK:
            case KELP:
            case KELP_PLANT:
            case SEA_PICKLE:
            case NETHER_WART:
            case CRIMSON_FUNGUS:
            case WARPED_FUNGUS:
            case GLOW_BERRIES:
                account.addCoins(toGive);
                Utils.moveUpHologram(Utils.chatColor("&a+" + toGive), block.getLocation(), 2);
        }

    }

}
