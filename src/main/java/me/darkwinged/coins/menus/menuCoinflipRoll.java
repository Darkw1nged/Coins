package me.darkwinged.coins.menus;

import me.darkwinged.coins.Coins;
import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.Coinflip;
import me.darkwinged.coins.libraries.struts.CustomItems;
import me.darkwinged.coins.libraries.struts.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class menuCoinflipRoll extends Menu {

    private final static Coins plugin = Coins.getInstance;
    private final Coinflip coinflip;
    private final Player owner;
    private final Player opponent;

    public menuCoinflipRoll(Player player, Coinflip coinflip) {
        super(player);
        this.coinflip = coinflip;
        this.owner = Bukkit.getPlayer(coinflip.getOwner());
        this.opponent = Bukkit.getPlayer(coinflip.getOpponent());
    }

    public String getMenuName() {
        return Utils.chatColor("&e" + owner.getName() + "&f Coinflip");
    }

    public int getSlots() {
        return 27;
    }

    public void handleMenu(InventoryClickEvent event) {
    }

    public void setMenuItems() {
        setFillerGlass();

        ItemStack ownerItem;
        switch (coinflip.getChoice()) {
            case BLUE -> ownerItem = makeItem(Material.BLUE_WOOL, owner.getName());
            case GREEN -> ownerItem = makeItem(Material.GREEN_WOOL, owner.getName());
            case RED -> ownerItem = makeItem(Material.RED_WOOL, owner.getName());
            case WHITE -> ownerItem = makeItem(Material.WHITE_WOOL, owner.getName());
            default -> ownerItem = makeItem(Material.BLACK_WOOL, owner.getName());
        }

        ItemStack opponentItem = makeItem(Material.MAGENTA_WOOL, opponent.getName());

        // Set player skulls
        inventory.setItem(10, playerSkull(owner));
        inventory.setItem(16, playerSkull(opponent));

        new BukkitRunnable() {
            int counter = 0;
            final int duration = 5 * 20; // 5 seconds (20 ticks per second)
            final Random random = new Random();

            @Override
            public void run() {
                if (counter >= duration) {
                    cancel();

                    // Determine final result
                    boolean ownerWins = random.nextBoolean();
                    Player winner = ownerWins ? owner : opponent;
                    Player loser = ownerWins ? opponent : owner;
                    ItemStack resultItem = ownerWins ? ownerItem : opponentItem;

                    inventory.setItem(13, resultItem);
                    Bukkit.broadcastMessage(Utils.chatColor("&6Coinflip &8» &e" + winner.getName() + "&f won &a" + (coinflip.getWorth() * 2) + " &fthe coinflip against &e" + loser.getName() + "&f!"));

                    Account winnerAccount = Manager.getAccount(winner.getUniqueId());
                    if (winnerAccount == null) return;
                    winnerAccount.addCoins(coinflip.getWorth() * 2);
                    winnerAccount.addCoinflipWin();
                    winnerAccount.addCoinflipTotalWon(coinflip.getWorth());

                    Account loserAccount = Manager.getAccount(loser.getUniqueId());
                    if (loserAccount == null) return;
                    loserAccount.addCoinflipLoss();
                    loserAccount.addCoinflipTotalLost(coinflip.getWorth());
                    return;
                }

                // Alternate between owner and opponent items
                inventory.setItem(13, (counter / 10) % 2 == 0 ? ownerItem : opponentItem);
                counter += 5; // Update every 5 ticks (0.25 seconds)
            }
        }.runTaskTimer(plugin, 0, 5);
    }

}
