package me.gurwi.athchunkclaim.listeners.chat;

import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.guis.ManageChunkMembersGUI;
import me.gurwi.athchunkclaim.guis.ManageChunkOwnersGUI;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AddOwnerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String msg = event.getMessage();

        if (ManageChunkOwnersGUI.addOwnerStatus.containsKey(player.getUniqueId())) {
            event.setCancelled(true);

            Player target = Bukkit.getPlayerExact(msg);

            if (msg.equalsIgnoreCase("Annulla") || msg.equalsIgnoreCase("Cancel")) {
                openGUI(player ,ManageChunkOwnersGUI.addOwnerStatus.get(player.getUniqueId()));
                ManageChunkOwnersGUI.addOwnerStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);
                player.sendMessage("§8§l» §7Azione annullata con successo!");

                if (ManageChunkOwnersGUI.noBackButtonOwnersGUI.contains(player.getUniqueId())) {
                    ManageChunkOwnersGUI.noBackButtonOwnersGUI.remove(player.getUniqueId());
                }

                return;
            }
            
            if (target == null) {
                player.sendMessage("§8§l» §cIl giocatore da te scelto non è stato trovato!");
                openGUI(player ,ManageChunkOwnersGUI.addOwnerStatus.get(player.getUniqueId()));
                ManageChunkOwnersGUI.addOwnerStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);

                if (ManageChunkOwnersGUI.noBackButtonOwnersGUI.contains(player.getUniqueId())) {
                    ManageChunkOwnersGUI.noBackButtonOwnersGUI.remove(player.getUniqueId());
                }

                return;
            }

            if (target.equals(player)) {
                player.sendMessage("§8§l» §cNon puoi aggiungere te stesso come owner!");
                openGUI(player ,ManageChunkOwnersGUI.addOwnerStatus.get(player.getUniqueId()));
                ManageChunkOwnersGUI.addOwnerStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);

                return;
            }

            if (ManageChunkOwnersGUI.addOwnerStatus.get(player.getUniqueId()).getOwnersUUID().contains(target.getUniqueId())) {
                player.sendMessage("§8§l» §cIl giocatore da te scelto è già un co-owner di questo chunk!");
                ManageChunkOwnersGUI.open(player, ManageChunkOwnersGUI.addOwnerStatus.get(player.getUniqueId()));
                ManageChunkOwnersGUI.addOwnerStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);

                if (ManageChunkOwnersGUI.noBackButtonOwnersGUI.contains(player.getUniqueId())) {
                    ManageChunkOwnersGUI.noBackButtonOwnersGUI.remove(player.getUniqueId());
                }

                return;
            }

            if (ManageChunkOwnersGUI.addOwnerStatus.get(player.getUniqueId()).getMembersUUID().contains(target.getUniqueId())) {
                ChunksDatabase.removeChunkMember(ManageChunkOwnersGUI.addOwnerStatus.get(player.getUniqueId()), target);
                player.sendMessage("§8§l» §aHai promosso §2" + target.getName() + " §aa co-owner del tuo plot.");
            } else {
                player.sendMessage("§8§l» §aHai aggiunto §2" + target.getName() + " §aal tuo plot come co-owner.");
            }

            ChunksDatabase.addChunkOwner(ManageChunkOwnersGUI.addOwnerStatus.get(player.getUniqueId()), target);

            if (ManageChunkOwnersGUI.noBackButtonOwnersGUI.contains(player.getUniqueId())) {
                ManageChunkOwnersGUI.noBackButtonOwnersGUI.remove(player.getUniqueId());
                ManageChunkOwnersGUI.open(player, ManageChunkOwnersGUI.addOwnerStatus.get(player.getUniqueId()), true);
            } else {
                ManageChunkOwnersGUI.open(player, ManageChunkOwnersGUI.addOwnerStatus.get(player.getUniqueId()));
            }

            ManageChunkOwnersGUI.addOwnerStatus.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

    }

    private void openGUI(Player player, PlayerChunk playerChunk) {
        if (ManageChunkOwnersGUI.noBackButtonOwnersGUI.contains(player.getUniqueId())) {
            ManageChunkOwnersGUI.noBackButtonOwnersGUI.remove(player.getUniqueId());
            ManageChunkOwnersGUI.open(player, playerChunk, true);
        } else {
            ManageChunkOwnersGUI.open(player, playerChunk);
        }
    }
    
}
