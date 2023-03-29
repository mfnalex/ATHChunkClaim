package me.gurwi.athchunkclaim.commands.playercommands;

import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.guis.PlayerChunksGUI;
import me.gurwi.athchunkclaim.config.ConfigHandler;
import org.bukkit.entity.Player;

public class PlayerChunkListGUICommand {

    public static boolean chunksList(Player player) {

        if (!ConfigHandler.OPEN_PLAYER_EMPTY_CHUNKS_GUI.getBoolean()) {
            if (ChunksDatabase.getPlayerChunks(player).isEmpty()) {
                player.sendMessage("§8§l» §cNon possiedi nessun chunk!");
                return false;
            }
        }

        PlayerChunksGUI.open(player);

        return false;

    }

}
