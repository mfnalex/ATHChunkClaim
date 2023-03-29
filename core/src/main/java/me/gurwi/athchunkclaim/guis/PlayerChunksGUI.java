package me.gurwi.athchunkclaim.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.guis.guiitems.GUIItemsManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerChunksGUI {

    public static void open(Player player) {

        Gui playerPlotsGUI = Gui.gui()
                .title(Component.text("§8§l» §b§lI TUOI PLOT"))
                .rows(5)
                .create();

        playerPlotsGUI.getFiller().fillBorder(ItemBuilder.from(GUIItemsManager.bottomTopFiller()).asGuiItem());

        AtomicInteger plotNum = new AtomicInteger();

        ChunksDatabase.getPlayerChunks(player).forEach(playerChunk -> {
            plotNum.getAndIncrement();

            GuiItem chunkIcon = ItemBuilder.from(GUIItemsManager.getChunkIcon(plotNum.get(), playerChunk)).asGuiItem(event -> {
                ManageChunkGUI.open(player, playerChunk);
            });

            playerPlotsGUI.addItem(chunkIcon);

        });

        playerPlotsGUI.setDefaultClickAction(event -> {event.setCancelled(true);});


        playerPlotsGUI.open(player);

    }

}
