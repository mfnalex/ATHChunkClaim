package me.gurwi.athchunkclaim.listeners;

import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.config.ConfigHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class UnClaimedChunkEventsHandler implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getLocation().getChunk();

        if (ChunksDatabase.isUnclaimedChunk(chunk)) {

            if (player.hasPermission("athchunkclaim.*") || player.hasPermission("athchunkclaim.bypass.unclaimedchunks") || player.isOp()) {
                event.setCancelled(false);
                return;
            }

            if (ConfigHandler.DISABLED_ACTIONS_UNCLAIMED_CHUNK_BLOCKBREAK.getBoolean()) {
                event.setCancelled(true);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§8§l» §c§l§oNon puoi rompere blocchi nei plot non claimati!"));
            }
        }

    }

    @EventHandler
    public void onBlocPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getLocation().getChunk();

        if (ChunksDatabase.isUnclaimedChunk(chunk)) {

            if (player.hasPermission("athchunkclaim.*") || player.hasPermission("athchunkclaim.bypass.unclaimedchunks") || player.isOp()) {
                event.setCancelled(false);
                return;
            }

            if (ConfigHandler.DISABLED_ACTIONS_UNCLAIMED_CHUNK_BLOCKPLACE.getBoolean()) {
                event.setCancelled(true);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§8§l» §c§l§oNon puoi piazzare blocchi nei plot non claimati!"));
            }
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        Chunk chunk = null;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            chunk = event.getClickedBlock().getChunk();
        } else if (event.getAction() == Action.PHYSICAL) {
            chunk = player.getLocation().getChunk();
        }

        if (chunk == null) return;

        if (ChunksDatabase.isUnclaimedChunk(chunk)) {

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.PHYSICAL) {

                if (event.getClickedBlock() == null) return;
                if (event.getClickedBlock().getType() == Material.AIR) return;

                if (player.hasPermission("athchunkclaim.*") || player.hasPermission("athchunkclaim.bypass.unclaimedchunks") || player.isOp()) {
                    event.setCancelled(false);
                    return;
                }

                if (ConfigHandler.DISABLED_ACTIONS_UNCLAIMED_CHUNK_INTERACTIONS.getBoolean()) {
                    
                    event.setUseInteractedBlock(Event.Result.DENY);
                    
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§8§l» §c§l§oNon puoi interagire con gli oggetti nei plot non claimati!"));
                }
            }

        }

    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent event) {

        if (!(event.getEntity().getLocation().getChunk() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        Chunk chunk = event.getEntity().getLocation().getChunk();

        if (ChunksDatabase.isUnclaimedChunk(chunk)) {

            if (player.hasPermission("athchunkclaim.*") || player.hasPermission("athchunkclaim.bypass.unclaimedchunks") || player.isOp()) {
                event.setCancelled(false);
                return;
            }

            if (ConfigHandler.DISABLED_ACTIONS_UNCLAIMED_CHUNK_PVP.getBoolean()) {
                event.setCancelled(true);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§8§l» §c§l§oNon puoi fare pvp nei plot claimati!"));
            }
        }

    }

}
