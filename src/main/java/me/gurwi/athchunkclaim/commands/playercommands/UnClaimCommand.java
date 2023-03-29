package me.gurwi.athchunkclaim.commands.playercommands;

import me.gurwi.athchunkclaim.config.LanguageHandler;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.guis.ChunkUnClaimConfirmGUI;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class UnClaimCommand {

    public static boolean unClaim(Player player) {

        Chunk chunk = player.getLocation().getChunk();

        if (!ChunksDatabase.inChunk(chunk)) {
            player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.NO_CLAIMABLE_CHUNK_FOUND.getFormattedString());
            return true;
        }

        if (ChunksDatabase.isClaimedChunk(chunk)) {

            PlayerChunk playerChunk = ChunksDatabase.getPlayerChunk(chunk);

            if (playerChunk.getClaimerUUID().equals(player.getUniqueId())) {
                ChunkUnClaimConfirmGUI.open(player, playerChunk, false);

            } else if (playerChunk.getMembersUUID().contains(player.getUniqueId()) || playerChunk.getOwnersUUID().contains(player.getUniqueId())) {
                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.ONLY_CHUNK_OWNER_CAN_UNCLAIM.getFormattedString());

            } else {
                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.ONLY_CHUNK_OWNER_CAN_UNCLAIM.getFormattedString());
            }

        } else {
            player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.UNCLAIMED_CHUNK_ERORR.getFormattedString());
        }

        return false;

    }

}
