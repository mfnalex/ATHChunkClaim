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

public class ChunkMemberRemoveConfirmGUI {

    public static void open(Player player, OfflinePlayer target, PlayerChunk playerChunk, Boolean manageCommand) {

        Gui confirmMemberRemove = Gui.gui()
                .title(Component.text("§8§l» §3§lVUOI RIMUOVERE " + target.getName() + " DAL PLOT?"))
                .rows(3)
                .create();


        GuiItem confirmButton = ItemBuilder.from(Heads.CHECKMARK.getItemStack("§a§lCONFERMA")).asGuiItem(event -> {

            event.setCancelled(true);
            player.closeInventory();
            ChunksDatabase.removeChunkMember(playerChunk, target);
            player.sendMessage("§8§l» §aRimosso " + target.getName() + " dai membri del plot con successo!");

            if (!manageCommand) {
                ManageChunkMembersGUI.open(player, playerChunk);
            } else {
                ManageChunkMembersGUI.open(player, playerChunk, true);
            }

        });

        GuiItem cancelButton = ItemBuilder.from(Heads.RED_CROCE.getItemStack("§c§lANNULLA")).asGuiItem(event -> {

            event.setCancelled(true);

            if (!manageCommand) {
                ManageChunkMembersGUI.open(player, playerChunk);
            } else {
                ManageChunkMembersGUI.open(player, playerChunk, true);
            }

        });

        confirmMemberRemove.setItem(11, confirmButton);
        confirmMemberRemove.setItem(15, cancelButton);

        confirmMemberRemove.open(player);

    }

}
