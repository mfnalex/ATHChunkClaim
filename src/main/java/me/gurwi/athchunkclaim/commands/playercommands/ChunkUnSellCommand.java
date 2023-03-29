package me.gurwi.athchunkclaim.commands.playercommands;

import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ChunkUnSellCommand {

    public static boolean chunkUnSell(Player player) {

        Chunk chunk = player.getLocation().getChunk();

        if (ChunksDatabase.getAllClaimedChunks().contains(chunk)) {

            PlayerChunk playerChunk = ChunksDatabase.getPlayerChunk(chunk);

            if (playerChunk.getClaimerUUID().equals(player.getUniqueId())) {
                if (ChunksDatabase.isInSale(playerChunk)) {

                    ChunksDatabase.unSellChunk(playerChunk);
                    player.sendMessage("§8§l» §aHai rimosso il tuo chunk dalla vendità con successo!");

                } else {
                    player.sendMessage("§8§l» §cQuesto chunk non è in vendità!");
                }

            } else {
                player.sendMessage("§8§l» §cSolo il proprietario del chunk può annullare la vendità di esso!");
            }

        }

        return false;

    }

}
