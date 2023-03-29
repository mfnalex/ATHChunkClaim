package me.gurwi.athchunkclaim.commands.playercommands;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import me.gurwi.athchunkclaim.config.LanguageHandler;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ChunkSellCommand {

    public static boolean sell(Player player, Double sellPrice) {

        Chunk chunk = player.getLocation().getChunk();

        if (ChunksDatabase.getAllClaimedChunks().contains(chunk)) {

            PlayerChunk playerChunk = ChunksDatabase.getPlayerChunk(chunk);

            if (playerChunk.getClaimerUUID().equals(player.getUniqueId())) {
                if (!ChunksDatabase.isInSale(playerChunk)) {
                    ChunksDatabase.sellChunk(playerChunk, sellPrice);
                    player.sendMessage(LanguageHandler.CHUNK_SELL_SUCCESSFUL.getFormattedString().replace("%sell_price%", ATHChunkClaim.getEconomy().format(sellPrice)));
                } else {
                    player.sendMessage("§8§l» §cQuesto chunk è già in vendità!");
                }

            } else {
                player.sendMessage("§8§l» §cSolo il proprietario del chunk può vendere questo chunk!");
            }

        } else {
            player.sendMessage("§8§l» §cNon puoi vendere un chunk non claimato da te!");
        }

        return false;

    }

}
