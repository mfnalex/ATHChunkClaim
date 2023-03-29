package me.gurwi.athchunkclaim.commands.playercommands;

import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.guis.ManageChunkGUI;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ChunkManageCommand {

    public static boolean chunkManage(Player player) {
        Chunk chunk = player.getLocation().getChunk();

        if (ChunksDatabase.inChunk(chunk)) {
            if (ChunksDatabase.isClaimedChunk(chunk)) {

                PlayerChunk playerChunk = ChunksDatabase.getPlayerChunk(chunk);

                if (playerChunk.getClaimerUUID().equals(player.getUniqueId())) {
                    ManageChunkGUI.open(player, playerChunk, false);
                } else {
                    player.sendMessage("§8§l» §cSolo il proprietario del chunk può gestire questo chunk!");
                }

            } else {
                player.sendMessage("§8§l» §cQuesto chunk non è stato claimato da nessuno!");
            }

        } else {
            player.sendMessage("§8§l» §cNessun chunk trovato nella tua posizione!");
        }

        return false;

    }

}
