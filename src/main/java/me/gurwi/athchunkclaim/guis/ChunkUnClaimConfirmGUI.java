package me.gurwi.athchunkclaim.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import me.gurwi.athchunkclaim.config.ConfigHandler;
import me.gurwi.athchunkclaim.utils.customheads.Heads;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ChunkUnClaimConfirmGUI {

    public static void open(Player player, PlayerChunk playerChunk) {

        Gui confirmChunkRemove = Gui.gui()
                .title(Component.text("§8§l» §3§lVUOI UNCLAIMARE QUESTO CHUNK?"))
                .rows(3)
                .create();


        GuiItem confirmButton = ItemBuilder.from(Heads.CHECKMARK.getItemStack("§a§lCONFERMA")).asGuiItem(event -> {

            event.setCancelled(true);
            player.closeInventory();
            ChunksDatabase.unClaimChunk(playerChunk.getChunk());
            player.sendMessage("§8§l» §aHai unclaimato questo plot con successo!");

            if (ChunksDatabase.getPlayerChunks(player).isEmpty()) {
                if (ConfigHandler.OPEN_PLAYER_EMPTY_CHUNKS_GUI.getBoolean()) {
                    PlayerChunksGUI.open(player);
                }
            } else {
                PlayerChunksGUI.open(player);
            }

        });

        GuiItem cancelButton = ItemBuilder.from(Heads.RED_CROCE.getItemStack("§c§lANNULLA")).asGuiItem(event -> {

            event.setCancelled(true);
            ManageChunkGUI.open(player, playerChunk);

        });

        confirmChunkRemove.setItem(11, confirmButton);
        confirmChunkRemove.setItem(15, cancelButton);

        confirmChunkRemove.open(player);

    }

    public static void open(Player player, PlayerChunk playerChunk, Boolean openGUIsAfter) {

        Gui confirmChunkRemove = Gui.gui()
                .title(Component.text("§8§l» §3§lVUOI UNCLAIMARE QUESTO CHUNK?"))
                .rows(3)
                .create();


        GuiItem confirmButton = ItemBuilder.from(Heads.CHECKMARK.getItemStack("§a§lCONFERMA")).asGuiItem(event -> {

            event.setCancelled(true);
            player.closeInventory();
            ChunksDatabase.unClaimChunk(playerChunk.getChunk());
            player.sendMessage("§8§l» §aHai unclaimato questo plot con successo!");

            if (openGUIsAfter) {
                if (ChunksDatabase.getPlayerChunks(player).isEmpty()) {
                    if (ConfigHandler.OPEN_PLAYER_EMPTY_CHUNKS_GUI.getBoolean()) {
                        PlayerChunksGUI.open(player);
                    }
                } else {
                    PlayerChunksGUI.open(player);
                }
            }

        });

        GuiItem cancelButton = ItemBuilder.from(Heads.RED_CROCE.getItemStack("§c§lANNULLA")).asGuiItem(event -> {

            event.setCancelled(true);

            if (openGUIsAfter) {
                ManageChunkGUI.open(player, playerChunk);
            } else {
                player.closeInventory();
            }

        });

        confirmChunkRemove.setItem(11, confirmButton);
        confirmChunkRemove.setItem(15, cancelButton);

        confirmChunkRemove.open(player);

    }

    public static void open(Player player, PlayerChunk playerChunk, Boolean openGUIsAfter, Boolean openFromManageCommand) {

        Gui confirmChunkRemove = Gui.gui()
                .title(Component.text("§8§l» §3§lVUOI UNCLAIMARE QUESTO CHUNK?"))
                .rows(3)
                .create();


        GuiItem confirmButton = ItemBuilder.from(Heads.CHECKMARK.getItemStack("§a§lCONFERMA")).asGuiItem(event -> {

            event.setCancelled(true);
            player.closeInventory();
            ChunksDatabase.unClaimChunk(playerChunk.getChunk());
            player.sendMessage("§8§l» §aHai unclaimato questo plot con successo!");

            if (!openFromManageCommand) {
                if (openGUIsAfter) {
                    if (ChunksDatabase.getPlayerChunks(player).isEmpty()) {
                        if (ConfigHandler.OPEN_PLAYER_EMPTY_CHUNKS_GUI.getBoolean()) {
                            PlayerChunksGUI.open(player);
                        }
                    } else {
                        PlayerChunksGUI.open(player);
                    }
                }
            }

        });

        GuiItem cancelButton = ItemBuilder.from(Heads.RED_CROCE.getItemStack("§c§lANNULLA")).asGuiItem(event -> {

            event.setCancelled(true);

            if (openGUIsAfter) {
                if (!openFromManageCommand) {
                    ManageChunkGUI.open(player, playerChunk);
                } else {
                    ManageChunkGUI.open(player, playerChunk, false);
                }
            } else {
                player.closeInventory();
            }

        });

        confirmChunkRemove.setItem(11, confirmButton);
        confirmChunkRemove.setItem(15, cancelButton);

        confirmChunkRemove.open(player);

    }

}
