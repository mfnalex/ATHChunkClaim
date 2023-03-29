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

public class AddMemberChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String msg = event.getMessage();

        if (ManageChunkMembersGUI.addMemberStatus.containsKey(player.getUniqueId())) {
            event.setCancelled(true);

            Player target = Bukkit.getPlayerExact(msg);

            if (msg.equalsIgnoreCase("Annulla") || msg.equalsIgnoreCase("Cancel")) {
                openGUI(player, ManageChunkMembersGUI.addMemberStatus.get(player.getUniqueId()));
                ManageChunkMembersGUI.addMemberStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);
                player.sendMessage("§8§l» §7Azione annullata con successo!");

                return;
            }

            if (target == null) {
                player.sendMessage("§8§l» §cIl giocatore da te scelto non è stato trovato!");
                openGUI(player, ManageChunkMembersGUI.addMemberStatus.get(player.getUniqueId()));
                ManageChunkMembersGUI.addMemberStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);

                return;
            }

            if (target.equals(player)) {
                player.sendMessage("§8§l» §cNon puoi aggiungere te stesso come membro!");
                openGUI(player, ManageChunkMembersGUI.addMemberStatus.get(player.getUniqueId()));
                ManageChunkMembersGUI.addMemberStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);

                return;
            }

            if (ManageChunkMembersGUI.addMemberStatus.get(player.getUniqueId()).getMembersUUID().contains(target.getUniqueId())) {
                player.sendMessage("§8§l» §cIl giocatore da te scelto è già un membro di questo chunk!");
                openGUI(player, ManageChunkMembersGUI.addMemberStatus.get(player.getUniqueId()));
                ManageChunkMembersGUI.addMemberStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);

                return;
            }

            if (ManageChunkMembersGUI.addMemberStatus.get(player.getUniqueId()).getOwnersUUID().contains(target.getUniqueId())) {
                player.sendMessage("§8§l» §cIl giocatore da te scelto è già un co-owner di questo chunk!");
                openGUI(player, ManageChunkMembersGUI.addMemberStatus.get(player.getUniqueId()));
                ManageChunkMembersGUI.addMemberStatus.remove(player.getUniqueId());
                player.sendTitle("", "", 0, 1, 0);

                return;
            }

            ChunksDatabase.addChunkMember(ManageChunkMembersGUI.addMemberStatus.get(player.getUniqueId()), target);
            player.sendMessage("§8§l» §aHai aggiunto §2" + target.getName() + " §aal tuo plot.");

            if (ManageChunkMembersGUI.noBackButtonMembersGUI.contains(player.getUniqueId())) {
                ManageChunkMembersGUI.noBackButtonMembersGUI.remove(player.getUniqueId());
                ManageChunkMembersGUI.open(player, ManageChunkMembersGUI.addMemberStatus.get(player.getUniqueId()), true);
            } else {
                ManageChunkMembersGUI.open(player, ManageChunkMembersGUI.addMemberStatus.get(player.getUniqueId()));
            }

            ManageChunkMembersGUI.addMemberStatus.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

    }

    public static void openGUI(Player player, PlayerChunk playerChunk) {
        if (ManageChunkMembersGUI.noBackButtonMembersGUI.contains(player.getUniqueId())) {
            ManageChunkMembersGUI.noBackButtonMembersGUI.remove(player.getUniqueId());
            ManageChunkOwnersGUI.open(player, playerChunk, true);
        } else {
            ManageChunkOwnersGUI.open(player, playerChunk);
        }
    }

}
