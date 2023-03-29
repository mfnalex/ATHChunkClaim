package me.gurwi.athchunkclaim.athchunkclaimapis;

import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class ATHChunkAPI {

    public static PlayerChunk getPlayerChunk(Chunk chunk) {
        return ChunksDatabase.getPlayerChunk(chunk);
    }

    public static List<OfflinePlayer> getChunkOwner(PlayerChunk playerChunk) {
        return ChunksDatabase.getChunkOwners(playerChunk);
    }

}
