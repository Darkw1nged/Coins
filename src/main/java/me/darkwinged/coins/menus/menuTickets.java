package me.darkwinged.coins.menus;

import me.darkwinged.coins.libraries.Manager;
import me.darkwinged.coins.libraries.Utils;
import me.darkwinged.coins.libraries.struts.Account;
import me.darkwinged.coins.libraries.struts.Lottery;
import me.darkwinged.coins.libraries.struts.Menu;
import me.darkwinged.coins.libraries.struts.Ticket;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class menuTickets extends Menu {

    private final Player player;

    public menuTickets(Player player) {
        super(player);
        this.player = player;
    }

    public String getMenuName() {
        return Utils.chatColor("&8Lottery Tickets");
    }

    public int getSlots() {
        return 27;
    }

    public void handleMenu(InventoryClickEvent event) {
        int slot = event.getSlot();

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) {
            player.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
            player.closeInventory();
            return;
        }

        Lottery lottery = Utils.activeLotteries.getFirst();
        if (lottery == null) {
            player.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
            player.closeInventory();
            return;
        }

        switch (slot) {
            case 14 -> {
                Ticket ticket = new Ticket(account);

                if (!account.hasEnoughCoins(50)) {
                    player.sendMessage(Utils.chatColor("&6Coins &8» &cYou do not have enough coins!"));
                    return;
                }

                account.removeCoins(50);
                List<Ticket> playerTickets;
                if (lottery.getParticipants().containsKey(account)) {
                    playerTickets = lottery.getParticipants().get(account);
                } else {
                    playerTickets = new ArrayList<>();
                }
                playerTickets.add(ticket);
                lottery.getParticipants().put(account, playerTickets);
            }
            case 15 -> {
                List<Ticket> tickets = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    tickets.add(new Ticket(account));
                }

                if (!account.hasEnoughCoins(250)) {
                    player.sendMessage(Utils.chatColor("&6Coins &8» &cYou do not have enough coins!"));
                    return;
                }

                account.removeCoins(250);
                List<Ticket> playerTickets;
                if (lottery.getParticipants().containsKey(account)) {
                    playerTickets = lottery.getParticipants().get(account);
                } else {
                    playerTickets = new ArrayList<>();
                }
                playerTickets.addAll(tickets);
                lottery.getParticipants().put(account, playerTickets);
            }
            case 16 -> {
                List<Ticket> tickets = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    tickets.add(new Ticket(account));
                }

                if (!account.hasEnoughCoins(500)) {
                    player.sendMessage(Utils.chatColor("&6Coins &8» &cYou do not have enough coins!"));
                    return;
                }

                List<Ticket> playerTickets;
                if (lottery.getParticipants().containsKey(account)) {
                    playerTickets = lottery.getParticipants().get(account);
                } else {
                    playerTickets = new ArrayList<>();
                }
                playerTickets.addAll(tickets);
                lottery.getParticipants().put(account, playerTickets);
            }
        }

        List<Ticket> playerTickets = Utils.activeLotteries.getFirst().getParticipantsTicket(account);
        inventory.setItem(10, makeItem(Material.PAPER, Utils.chatColor("&eYour Tickets"), Utils.chatColor("&e&l | &fAmount: &a" + playerTickets.size())));
    }

    public void setMenuItems() {
        setFillerGlass();

        Account account = Manager.getAccount(player.getUniqueId());
        if (account == null) {
            player.sendMessage(Utils.chatColor("&6Coins &8» &cAn internal error occurred, please contact your system administrator for assistance."));
            player.closeInventory();
            return;
        }

        List<Ticket> playerTickets = Utils.activeLotteries.getFirst().getParticipantsTicket(account);
        if (playerTickets == null) {
            playerTickets = new ArrayList<>();
        }
        inventory.setItem(0, makeItem(Material.CLOCK, Utils.chatColor("&eCurrent Time"), Utils.chatColor("&e&l | &fWorld Time: &a" + Utils.getCurrentTimeOfDay())));
        inventory.setItem(10, makeItem(Material.PAPER, Utils.chatColor("&eYour Tickets"), Utils.chatColor("&e&l | &fAmount: &a" + playerTickets.size())));

        inventory.setItem(3, makeItem(Material.ORANGE_STAINED_GLASS_PANE, " "));
        inventory.setItem(12, makeItem(Material.ORANGE_STAINED_GLASS_PANE, " "));
        inventory.setItem(21, makeItem(Material.ORANGE_STAINED_GLASS_PANE, " "));

        inventory.setItem(14, makeItem(Material.NAME_TAG, Utils.chatColor("&a1 Ticket"), Utils.chatColor("&a&l | &fCost: &a50 Coins"), "", Utils.chatColor("&eClick to purchase")));
        inventory.setItem(15, makeItem(Material.NAME_TAG, Utils.chatColor("&a5 Ticket"), Utils.chatColor("&a&l | &fCost: &a250 Coins"), "", Utils.chatColor("&eClick to purchase")));
        inventory.setItem(16, makeItem(Material.NAME_TAG, Utils.chatColor("&a10 Ticket"), Utils.chatColor("&a&l | &fCost: &a500 Coins"), "", Utils.chatColor("&eClick to purchase")));


    }
}
