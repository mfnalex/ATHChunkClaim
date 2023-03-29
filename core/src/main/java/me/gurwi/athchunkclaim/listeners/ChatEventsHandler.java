package me.gurwi.athchunkclaim.listeners;

import me.gurwi.athchunkclaim.guis.ManageChunkGUI;
import me.gurwi.athchunkclaim.guis.ManageChunkMembersGUI;
import me.gurwi.athchunkclaim.guis.ManageChunkOwnersGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatEventsHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity().getPlayer();

        if (ManageChunkMembersGUI.addMemberStatus.containsKey(player.getUniqueId())) {
            ManageChunkMembersGUI.addMemberStatus.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

        if (ManageChunkOwnersGUI.addOwnerStatus.containsKey(player.getUniqueId())) {
            ManageChunkOwnersGUI.addOwnerStatus.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

        if (ManageChunkGUI.sellChunkStatus.containsKey(player.getUniqueId())) {
            ManageChunkGUI.sellChunkStatus.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

        if (ManageChunkMembersGUI.noBackButtonMembersGUI.contains(player.getUniqueId())) {
            ManageChunkMembersGUI.noBackButtonMembersGUI.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

        if (ManageChunkOwnersGUI.noBackButtonOwnersGUI.contains(player.getUniqueId())) {
            ManageChunkOwnersGUI.noBackButtonOwnersGUI.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

        if (ManageChunkGUI.noBackButton.contains(player.getUniqueId())) {
            ManageChunkGUI.noBackButton.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (ManageChunkMembersGUI.addMemberStatus.containsKey(player.getUniqueId())) {
            ManageChunkMembersGUI.addMemberStatus.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

        if (ManageChunkOwnersGUI.addOwnerStatus.containsKey(player.getUniqueId())) {
            ManageChunkOwnersGUI.addOwnerStatus.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

        if (ManageChunkGUI.sellChunkStatus.containsKey(player.getUniqueId())) {
            ManageChunkGUI.sellChunkStatus.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

        if (ManageChunkMembersGUI.noBackButtonMembersGUI.contains(player.getUniqueId())) {
            ManageChunkMembersGUI.noBackButtonMembersGUI.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

        if (ManageChunkOwnersGUI.noBackButtonOwnersGUI.contains(player.getUniqueId())) {
            ManageChunkOwnersGUI.noBackButtonOwnersGUI.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }

        if (ManageChunkGUI.noBackButton.contains(player.getUniqueId())) {
            ManageChunkGUI.noBackButton.remove(player.getUniqueId());
            player.sendTitle("", "", 0, 1, 0);
        }


    }

}
