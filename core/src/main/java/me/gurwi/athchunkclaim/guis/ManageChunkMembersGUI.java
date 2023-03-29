package me.gurwi.athchunkclaim.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.guis.guiitems.GUIItemsManager;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import me.gurwi.athchunkclaim.utils.customheads.Heads;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ManageChunkMembersGUI {

    public static HashMap<UUID, PlayerChunk> addMemberStatus = new HashMap<>();
    public static List<UUID> noBackButtonMembersGUI = new ArrayList<>();

    public static void open(Player player, PlayerChunk playerChunk) {

        PaginatedGui manageChunkGUI = Gui.paginated()
                .title(Component.text("§8§l» §b§lGESTISCI MEMBRI CHUNK"))
                .rows(5)
                .create();

        manageChunkGUI.getFiller().fillBorder(ItemBuilder.from(GUIItemsManager.bottomTopFiller()).asGuiItem());

        ChunksDatabase.getChunkMembers(playerChunk).forEach(offlinePlayer -> {
            GuiItem playerHead = ItemBuilder.from(GUIItemsManager.getPlayerHead(offlinePlayer)).asGuiItem(event -> {
                ChunkMemberRemoveConfirmGUI.open(player, offlinePlayer, playerChunk, false);
            });

            manageChunkGUI.addItem(playerHead);
        });

        GuiItem back = ItemBuilder.from(Heads.BACK_BUTTON.getItemStack("§f§l↓ §7§lINDIETRO")).asGuiItem(event -> {ManageChunkGUI.open(player, playerChunk);});

        GuiItem addMember = ItemBuilder.from(Heads.BLACK_PLUS.getItemStack("§a§l+ §7§lAGGIUNGI MEMBRO")).asGuiItem(event -> {

            if (addMemberStatus.containsKey(player.getUniqueId())) {
                player.sendMessage("§8§l» §cStai già addando un membro a un chunk!");
                return;
            }

            player.closeInventory();
            addMemberStatus.put(player.getUniqueId(), playerChunk);
            player.sendTitle("§b§lChi vuoi aggiungere?", "§f§lScrivi in chat il suo nick", 0, 9999999 * 20, 0);
            player.sendMessage("§8§l» §7Scrivi §bAnnulla §7per annullare quest' azione.");

        });

        GuiItem next = ItemBuilder.from(Heads.NEXT_PAGE.getItemStack("§7§lSUCCESSIVO §f§l→")).asGuiItem(event -> {
            manageChunkGUI.next();
        });

        GuiItem previous = ItemBuilder.from(Heads.PREVIOUS_PAGE.getItemStack("§f§l← §7§lPRECEDENTE")).asGuiItem(event -> {
            manageChunkGUI.previous();
        });

        manageChunkGUI.setItem(39, back);
        manageChunkGUI.setItem(41, addMember);
        manageChunkGUI.setItem(36, previous);
        manageChunkGUI.setItem(44, next);

        manageChunkGUI.setDefaultClickAction(event -> {event.setCancelled(true);});

        manageChunkGUI.open(player);

    }

    public static void open(Player player, PlayerChunk playerChunk, boolean manageCommand) {

        PaginatedGui manageChunkGUI = Gui.paginated()
                .title(Component.text("§8§l» §b§lGESTISCI MEMBRI CHUNK"))
                .rows(5)
                .create();

        manageChunkGUI.getFiller().fillBorder(ItemBuilder.from(GUIItemsManager.bottomTopFiller()).asGuiItem());

        ChunksDatabase.getChunkMembers(playerChunk).forEach(offlinePlayer -> {
            GuiItem playerHead = ItemBuilder.from(GUIItemsManager.getPlayerHead(offlinePlayer)).asGuiItem(event -> {
                ChunkMemberRemoveConfirmGUI.open(player, offlinePlayer, playerChunk, manageCommand);
            });

            manageChunkGUI.addItem(playerHead);
        });

        GuiItem back;
        if (manageCommand) {
            back = ItemBuilder.from(Heads.BACK_BUTTON.getItemStack("§f§l↓ §7§lINDIETRO")).asGuiItem(event -> {
                ManageChunkGUI.open(player, playerChunk, false);
            });
        } else  {
            back = ItemBuilder.from(Heads.BACK_BUTTON.getItemStack("§f§l↓ §7§lINDIETRO")).asGuiItem(event -> {
                ManageChunkGUI.open(player, playerChunk);
            });
        }
        manageChunkGUI.setItem(39, back);

        GuiItem addMember = ItemBuilder.from(Heads.BLACK_PLUS.getItemStack("§a§l+ §7§lAGGIUNGI MEMBRO")).asGuiItem(event -> {

            if (addMemberStatus.containsKey(player.getUniqueId())) {
                player.sendMessage("§8§l» §cStai già addando un membro a un chunk!");
                return;
            }

            player.closeInventory();
            addMemberStatus.put(player.getUniqueId(), playerChunk);
            noBackButtonMembersGUI.add(player.getUniqueId());
            player.sendTitle("§b§lChi vuoi aggiungere?", "§f§lScrivi in chat il suo nick", 0, 9999999 * 20, 0);
            player.sendMessage("§8§l» §7Scrivi §bAnnulla §7per annullare quest' azione.");

        });

        GuiItem next = ItemBuilder.from(Heads.NEXT_PAGE.getItemStack("§7§lSUCCESSIVO §f§l→")).asGuiItem(event -> {
            manageChunkGUI.next();
        });

        GuiItem previous = ItemBuilder.from(Heads.PREVIOUS_PAGE.getItemStack("§f§l← §7§lPRECEDENTE")).asGuiItem(event -> {
            manageChunkGUI.previous();
        });

        manageChunkGUI.setItem(41, addMember);
        manageChunkGUI.setItem(36, previous);
        manageChunkGUI.setItem(44, next);

        manageChunkGUI.setDefaultClickAction(event -> {event.setCancelled(true);});

        manageChunkGUI.open(player);

    }

}
