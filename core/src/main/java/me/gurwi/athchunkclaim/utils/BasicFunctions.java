package me.gurwi.athchunkclaim.utils;

import me.gurwi.athchunkclaim.config.ConfigHandler;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class BasicFunctions {

    public static boolean hasClaimPermission(Player player, String match) {
        for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
            if (permission.getPermission().startsWith(match) && permission.getValue()) {
                return true;
            }
        }

        return false;
    }

    public static Integer getMaxClaims(Player player, String match) {

        int maxClaim = 0;

        for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
            if (permission.getPermission().startsWith(match) && permission.getValue()) {
               maxClaim = Integer.parseInt(permission.getPermission().replace(match, ""));
            }
        }

        return maxClaim;
    }

    public static boolean canClaim(Player player) {

        if (!player.hasPermission("*") || !player.hasPermission("athchunkclaim.*") || !player.isOp()) {
            if (!(ChunksDatabase.getPlayerChunksNum(player) <= ConfigHandler.MAX_DEFAULT_CLAIMS.getInt())) {
                if (!BasicFunctions.hasClaimPermission(player, "athchunkclaim.claim.")) {
                    return false;

                } else {
                    int maxClaims = BasicFunctions.getMaxClaims(player, "athchunkclaim.claim.") - 1;
                    return ChunksDatabase.getPlayerChunksNum(player) <= maxClaims;
                }
            }
        }

        return true;

    }

    public static void tpToChunk(Chunk chunk, Player player) {

        Block block = chunk.getBlock(8, 0, 8);
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        player.teleport(new Location(chunk.getWorld(), x, chunk.getWorld().getHighestBlockYAt(new Location(chunk.getWorld(), x, y, z)), z));

    }

}
