package me.gurwi.athchunkclaim.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import me.gurwi.athchunkclaim.utils.customheads.Heads;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ChunkOwnersRemoveConfirmGUI {

    public static void open(Player player, OfflinePlayer target, PlayerChunk playerChunk, Boolean manageCommand) {

        Gui confirmOwnerRemove = Gui.gui()
                .title(Component.text("§8§l» §3§lVUOI RIMUOVERE " + target.getName() + " DAL PLOT?"))
                .rows(3)
                .create();


        GuiItem confirmButton = ItemBuilder.from(Heads.CHECKMARK.getItemStack("§a§lCONFERMA")).asGuiItem(event -> {

            event.setCancelled(true);
            player.closeInventory();
            ChunksDatabase.removeChunkOwner(playerChunk, target);
            player.sendMessage("§8§l» §aRimosso " + target.getName() + " dai co-proprietari del plot con successo!");

            if (!manageCommand) {
                ManageChunkOwnersGUI.open(player, playerChunk);
            } else {
                ManageChunkOwnersGUI.open(player, playerChunk, true);
            }

        });

        GuiItem cancelButton = ItemBuilder.from(Heads.RED_CROCE.getItemStack("§c§lANNULLA")).asGuiItem(event -> {

            event.setCancelled(true);

            if (!manageCommand) {
                ManageChunkOwnersGUI.open(player, playerChunk);
            } else {
                ManageChunkOwnersGUI.open(player, playerChunk, true);
            }

        });

        confirmOwnerRemove.setItem(11, confirmButton);
        confirmOwnerRemove.setItem(15, cancelButton);

        confirmOwnerRemove.open(player);

    }

}
