package me.gurwi.athchunkclaim.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.guis.guiitems.GUIItemsManager;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import me.gurwi.athchunkclaim.utils.APIManager;
import me.gurwi.athchunkclaim.config.ConfigHandler;
import me.gurwi.athchunkclaim.utils.customheads.Heads;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ManageChunkGUI {

    public static HashMap<UUID, PlayerChunk> sellChunkStatus = new HashMap<>();
    public static List<UUID> noBackButton = new ArrayList<>();

    public static void open(Player player, PlayerChunk playerChunk) {

        Gui manageChunkGUI = Gui.gui()
                .title(Component.text("§8§l» §b§lGESTISCI CHUNK"))
                .rows(5)
                .create();

        manageChunkGUI.getFiller().fillBorder(ItemBuilder.from(GUIItemsManager.bottomTopFiller()).asGuiItem());

        GuiItem chunckMembers = ItemBuilder.from(Heads.STEVE.getItemStack("§8§l✕ §fMembri")).asGuiItem(event -> {ManageChunkMembersGUI.open(player, playerChunk);});
        GuiItem chunckOwners = ItemBuilder.from(Heads.ALEX.getItemStack("§8§l✕ §cProprietari")).asGuiItem(event -> {ManageChunkOwnersGUI.open(player, playerChunk);});

        GuiItem unSellChunk = ItemBuilder.from(Heads.GOLD_DOLLAR.getItemStack("§8§l✕ §eAnnulla vendità chunk")).asGuiItem();
        GuiItem sellChunk = ItemBuilder.from(Heads.MONEY.getItemStack("§8§l✕ §aVendi Chunk")).asGuiItem(event -> {

            if (!APIManager.setupEconomy()) {
                player.sendMessage("§8§l» §cFunziona attualmente non disponibile a causa della mancanza di Vault!");
                return;
            }

            if (!ConfigHandler.VAULT_SUPPORT.getBoolean()) {
                player.sendMessage("§8§l» §cFunziona attualmente non disponibile.");
                return;
            }


            if (sellChunkStatus.containsKey(player.getUniqueId())) {
                player.sendMessage("§8§l» §cStai già tentando di vendere un chunk!");
                return;
            }

            player.closeInventory();
            sellChunkStatus.put(player.getUniqueId(), playerChunk);
            player.sendTitle("§b§lA quanto lo vuoi vendere?", "§f§lScrivi in chat il prezzo a cui vuoi venderei il chunk", 0, 9999999 * 20, 0);
            player.sendMessage("§8§l» §7Scrivi §bAnnulla §7per annullare quest' azione.");
        });

        GuiItem unClaimChunk = ItemBuilder.from(Heads.FARMED_CUBE_RED.getItemStack("§8§l✕ §4UnClaim Chunk")).asGuiItem(event -> ChunkUnClaimConfirmGUI.open(player, playerChunk));

        GuiItem back = ItemBuilder.from(Heads.BACK_BUTTON.getItemStack("§f§l↓ §7§lINDIETRO")).asGuiItem(event -> {PlayerChunksGUI.open(player);});

        manageChunkGUI.setItem(20, chunckMembers);
        manageChunkGUI.setItem(21, chunckOwners);

        if (ChunksDatabase.isInSale(playerChunk)) {
            manageChunkGUI.setItem(22, unSellChunk);
        } else {
            manageChunkGUI.setItem(22, sellChunk);
        }

        manageChunkGUI.setItem(24, unClaimChunk);

        manageChunkGUI.setItem(40, back);

        manageChunkGUI.setDefaultClickAction(event -> {event.setCancelled(true);});

        manageChunkGUI.open(player);

    }

    public static void open(Player player, PlayerChunk playerChunk, boolean backButton) {

        Gui manageChunkGUI = Gui.gui()
                .title(Component.text("§8§l» §b§lGESTISCI CHUNK"))
                .rows(5)
                .create();

        manageChunkGUI.getFiller().fillBorder(ItemBuilder.from(GUIItemsManager.bottomTopFiller()).asGuiItem());

        GuiItem chunckMembers = ItemBuilder.from(Heads.STEVE.getItemStack("§8§l✕ §fMembri")).asGuiItem(event -> {ManageChunkMembersGUI.open(player, playerChunk, !backButton);});
        GuiItem chunckOwners = ItemBuilder.from(Heads.ALEX.getItemStack("§8§l✕ §cProprietari")).asGuiItem(event -> {ManageChunkOwnersGUI.open(player, playerChunk, !backButton);});

        GuiItem unSellChunk = ItemBuilder.from(Heads.GOLD_DOLLAR.getItemStack("§8§l✕ §eAnnulla vendità chunk")).asGuiItem();
        GuiItem sellChunk = ItemBuilder.from(Heads.MONEY.getItemStack("§8§l✕ §aVendi Chunk")).asGuiItem(event -> {

            if (!APIManager.setupEconomy()) {
                player.sendMessage("§8§l» §cFunziona attualmente non disponibile a causa della mancanza di Vault!");
                return;
            }

            if (!ConfigHandler.VAULT_SUPPORT.getBoolean()) {
                player.sendMessage("§8§l» §cFunziona attualmente non disponibile.");
                return;
            }


            if (sellChunkStatus.containsKey(player.getUniqueId())) {
                player.sendMessage("§8§l» §cStai già tentando di vendere un chunk!");
                return;
            }

            player.closeInventory();
            sellChunkStatus.put(player.getUniqueId(), playerChunk);
            noBackButton.add(player.getUniqueId());
            player.sendTitle("§b§lA quanto lo vuoi vendere?", "§f§lScrivi in chat il prezzo a cui vuoi venderei il chunk", 0, 9999999 * 20, 0);
            player.sendMessage("§8§l» §7Scrivi §bAnnulla §7per annullare quest' azione.");

        });

        GuiItem unClaimChunk = ItemBuilder.from(Heads.FARMED_CUBE_RED.getItemStack("§8§l✕ §4UnClaim Chunk")).asGuiItem(event -> ChunkUnClaimConfirmGUI.open(player, playerChunk, true, true));

        if (backButton) {
            GuiItem back = ItemBuilder.from(Heads.BACK_BUTTON.getItemStack("§f§l↓ §7§lINDIETRO")).asGuiItem(event -> {
                PlayerChunksGUI.open(player);
            });
            manageChunkGUI.setItem(40, back);
        }

        manageChunkGUI.setItem(20, chunckMembers);
        manageChunkGUI.setItem(21, chunckOwners);

        if (ChunksDatabase.isInSale(playerChunk)) {
            manageChunkGUI.setItem(22, unSellChunk);
        } else {
            manageChunkGUI.setItem(22, sellChunk);
        }

        manageChunkGUI.setItem(24, unClaimChunk);

        manageChunkGUI.setDefaultClickAction(event -> {event.setCancelled(true);});

        manageChunkGUI.open(player);

    }

}
