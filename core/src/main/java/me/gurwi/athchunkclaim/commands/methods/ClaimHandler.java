package me.gurwi.athchunkclaim.commands.methods;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import me.gurwi.athchunkclaim.config.LanguageHandler;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import me.gurwi.athchunkclaim.utils.APIManager;
import me.gurwi.athchunkclaim.config.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClaimHandler {

    public static boolean claimCommand(Player player) {
        if (APIManager.setupEconomy()) {
            if (ConfigHandler.VAULT_SUPPORT.getBoolean()) {
                if (!ATHChunkClaim.getEconomy().has(player, ConfigHandler.VAULT_CHUNK_CLAIM_PRICE.getDouble()) && !ChunksDatabase.isInSale(ChunksDatabase.getPlayerChunk(player.getLocation().getChunk()))) {
                    player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.INSUFFICENT_BALANCE_TO_CLAIM.getFormattedString());
                    return true;

                }

                if (ChunksDatabase.getAllClaimedChunks().contains(player.getLocation().getChunk())) {
                    if (!ATHChunkClaim.getEconomy().has(player, ChunksDatabase.getSalePrice(ChunksDatabase.getPlayerChunk(player.getLocation().getChunk()))) && ChunksDatabase.isInSale(ChunksDatabase.getPlayerChunk(player.getLocation().getChunk()))) {
                        player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.INSUFFICENT_BALANCE_TO_CLAIM.getFormattedString());
                        return true;
                    }
                }


                if (!ChunksDatabase.isClaimedChunk(player.getLocation().getChunk())) {
                    ATHChunkClaim.getEconomy().withdrawPlayer(player, ConfigHandler.VAULT_CHUNK_CLAIM_PRICE.getDouble());
                } else if (ChunksDatabase.isInSale(ChunksDatabase.getPlayerChunk(player.getLocation().getChunk()))) {

                    PlayerChunk playerChunk = ChunksDatabase.getPlayerChunk(player.getLocation().getChunk());
                    Player previousOwner = Bukkit.getPlayer(playerChunk.getClaimerUUID());

                    ATHChunkClaim.getEconomy().withdrawPlayer(player, ChunksDatabase.getSalePrice(playerChunk));
                    ATHChunkClaim.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(playerChunk.getClaimerUUID()), ChunksDatabase.getSalePrice(playerChunk));

                    if (previousOwner != null) {
                        previousOwner.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.CHUNK_SOLD.getFormattedString().replace("%buyer%", player.getName()).replace("%sell_price%", ATHChunkClaim.getEconomy().format(ChunksDatabase.getSalePrice(playerChunk))));
                    }

                }

            }

        }


        ChunksDatabase.unClaimChunk(player.getLocation().getChunk());
        ChunksDatabase.claimChunk(player.getLocation().getChunk(), player.getUniqueId());
        player.sendMessage(LanguageHandler.CHUNK_CLAIMED.getFormattedString());

        return false;

    }

}
