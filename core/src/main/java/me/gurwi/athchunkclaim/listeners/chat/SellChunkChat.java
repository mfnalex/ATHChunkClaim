package me.gurwi.athchunkclaim.listeners.chat;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.guis.ManageChunkGUI;
import me.gurwi.athchunkclaim.guis.ManageChunkOwnersGUI;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import me.gurwi.athchunkclaim.utils.FloatCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SellChunkChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String msg = event.getMessage();

        if (ManageChunkGUI.sellChunkStatus.containsKey(player.getUniqueId())) {
            event.setCancelled(true);

            if (msg.equalsIgnoreCase("Annulla") || msg.equalsIgnoreCase("Cancel")) {
                openGUI(player, ManageChunkGUI.sellChunkStatus.get(player.getUniqueId()));
                ManageChunkGUI.sellChunkStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);
                player.sendMessage("§8§l» §7Azione annullata con successo!");
                return;
            }

            if (!FloatCheck.isFloat(msg)) {
                openGUI(player, ManageChunkGUI.sellChunkStatus.get(player.getUniqueId()));
                ManageChunkGUI.sellChunkStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);
                player.sendMessage("§8§l» §cDevi inserire un numero come prezzo di venidità del chunk!");
                return;
            }

            if (ChunksDatabase.isInSale(ManageChunkGUI.sellChunkStatus.get(player.getUniqueId()))) {
                openGUI(player, ManageChunkGUI.sellChunkStatus.get(player.getUniqueId()));
                ManageChunkGUI.sellChunkStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);
                player.sendMessage("§8§l» §cQuesto chunk è già in vendità!");
                return;
            }

            ChunksDatabase.sellChunk(ManageChunkGUI.sellChunkStatus.get(player.getUniqueId()), Double.parseDouble(msg));
            openGUI(player, ManageChunkGUI.sellChunkStatus.get(player.getUniqueId()));
            ManageChunkGUI.sellChunkStatus.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
            player.sendMessage("§8§l» §aHai messo il tuo chunk in vendità a §2" + ATHChunkClaim.getEconomy().format(Double.parseDouble(msg)));
            player.sendMessage("§8§l» §eFai §6/chunk unsell" + " §e per togliere un chunk dalla vendità.");

        }

    }

    private void openGUI(Player player, PlayerChunk playerChunk) {
        if (ManageChunkGUI.noBackButton.contains(player.getUniqueId())) {
            ManageChunkGUI.noBackButton.remove(player.getUniqueId());
            ManageChunkGUI.open(player, playerChunk, false);
        } else {
            ManageChunkGUI.open(player, playerChunk);
        }
    }

}
