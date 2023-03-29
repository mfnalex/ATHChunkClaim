package me.gurwi.athchunkclaim.guis;

import me.gurwi.athchunkclaim.database.ChunksDatabase;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

public class EntityChunkInteract implements Listener {

    public void onChunkInteract(EntityInteractEvent event) {

        if (ChunksDatabase.inChunk(event.getEntity().getLocation().getChunk())) {
            event.setCancelled(true);
        }

    }

}
