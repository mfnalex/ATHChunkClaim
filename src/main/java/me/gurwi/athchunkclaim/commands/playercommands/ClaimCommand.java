package me.gurwi.athchunkclaim.commands.playercommands;

import me.gurwi.athchunkclaim.commands.methods.ClaimHandler;
import me.gurwi.athchunkclaim.config.LanguageHandler;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.utils.APIManager;
import me.gurwi.athchunkclaim.utils.BasicFunctions;
import me.gurwi.athchunkclaim.config.ConfigHandler;
import me.gurwi.athchunkclaim.utils.worldguard.WorldGuardAPI_Legacy;
import me.gurwi.athchunkclaim.utils.worldguard.WorldGuardHandler;
import org.bukkit.entity.Player;

public class ClaimCommand {

    public static boolean claim(Player player) {

        if (ConfigHandler.DISABLED_WORLDS.getDisabledWorldsList().contains(player.getWorld())) {
            player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.DISABLED_CLAIM_WORLD.getFormattedString());
            return false;
        }

        if (ConfigHandler.NEED_PERMISSION_TO_CLAIM.getBoolean()) {
            if (!player.hasPermission("athchunclaim.claim")) {
                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.NO_CLAIM_PERMISSIONS.getFormattedString());
                return true;
            }
        }

        if (!ChunksDatabase.inChunk(player.getLocation().getChunk())) {
            player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.NO_CLAIMABLE_CHUNK_FOUND.getFormattedString());
            return true;
        }

        ///////// WORLDGUARD

        if (APIManager.checkWorldGuard()) {
            if (ConfigHandler.WORLDGUARD_SUPPORT.getBoolean()) {

                if (WorldGuardHandler.getWorldGuardManager().getPlayerRegion(player) == null) {
                    if (!ConfigHandler.WORLDGUARD_CLAIM_GLOBAL.getBoolean()) {
                        player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.IMPOSSIBLE_TO_CLAIM_CHUNK.getFormattedString());
                        return true;
                    }
                }

                if (!ConfigHandler.WORLDGUARD_CLAIMABLE_REGIONS.getListString().isEmpty()) {
                    if (WorldGuardHandler.getWorldGuardManager().getPlayerRegion(player) != null) {
                        if (!ConfigHandler.WORLDGUARD_CLAIMABLE_REGIONS.getListString().contains(WorldGuardHandler.getWorldGuardManager().getPlayerRegion(player))) {
                            player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.IMPOSSIBLE_TO_CLAIM_CHUNK.getFormattedString());
                            return true;
                        }
                    }
                }

                if (!ConfigHandler.WORLDGUARD_NON_CLAIMABLE_REGIONS.getListString().isEmpty()) {
                    if (WorldGuardHandler.getWorldGuardManager().getPlayerRegion(player) != null) {
                        if (ConfigHandler.WORLDGUARD_NON_CLAIMABLE_REGIONS.getListString().contains(WorldGuardHandler.getWorldGuardManager().getPlayerRegion(player))) {
                            player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.IMPOSSIBLE_TO_CLAIM_CHUNK.getFormattedString());
                            return true;
                        }
                    }
                }

                if (WorldGuardHandler.getWorldGuardManager().getPlayerRegion(player) != null) {
                    if (!WorldGuardHandler.getWorldGuardManager().getPlayerRegion(player).contains(ConfigHandler.WORLDGUARD_CLAIM_RG_CONTAINING.getString())) {
                        player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.IMPOSSIBLE_TO_CLAIM_CHUNK.getFormattedString());
                        return true;
                    }
                }


                if (!ChunksDatabase.isClaimedChunk(player.getLocation().getChunk()) || ChunksDatabase.isInSale(ChunksDatabase.getPlayerChunk(player.getLocation().getChunk()))) {

                    if (!player.hasPermission("*") || !player.hasPermission("athchunkclaim.*") || !player.isOp()) {
                        if (!(ChunksDatabase.getPlayerChunksNum(player) <= ConfigHandler.MAX_DEFAULT_CLAIMS.getInt())) {
                            if (!BasicFunctions.hasClaimPermission(player, "athchunkclaim.claim.")) {
                                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.MAX_CLAIMS_REACHED.getFormattedString());
                                return true;

                            }  else {
                                int maxClaims = BasicFunctions.getMaxClaims(player, "athchunkclaim.claim.") - 1;

                                if (!(ChunksDatabase.getPlayerChunksNum(player) <= maxClaims)) {
                                    player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.MAX_CLAIMS_REACHED.getFormattedString());
                                    return true;
                                }
                            }
                        }
                    }

                    // VAULT & CLAIM

                    ClaimHandler.claimCommand(player);

                } else {
                    player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.CHUNK_ALREADY_CLAIMED.getFormattedString());
                }
                return true;

            }

        }

        /////// NORMAL

        if (!ConfigHandler.WORLDGUARD_SUPPORT.getBoolean() || !ConfigHandler.WORLDGUARD_SUPPORT.getBoolean()) {
            if (!ChunksDatabase.isClaimedChunk(player.getLocation().getChunk()) || ChunksDatabase.isInSale(ChunksDatabase.getPlayerChunk(player.getLocation().getChunk()))) {

                if (!player.hasPermission("*") || !player.hasPermission("athchunkclaim.*") || !player.isOp()) {
                    if (!(ChunksDatabase.getPlayerChunksNum(player) <= ConfigHandler.MAX_DEFAULT_CLAIMS.getInt())) {
                        if (!BasicFunctions.hasClaimPermission(player, "athchunkclaim.claim.")) {
                            player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.MAX_CLAIMS_REACHED.getFormattedString());
                            return true;

                        } else {
                            int maxClaims = BasicFunctions.getMaxClaims(player, "athchunkclaim.claim.") - 1;

                            if (!(ChunksDatabase.getPlayerChunksNum(player) <= maxClaims)) {
                                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.MAX_CLAIMS_REACHED.getFormattedString());
                                return true;
                            }
                        }
                    }
                }

                // VAULT & CLAIM

                ClaimHandler.claimCommand(player);

            } else {
                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.CHUNK_ALREADY_CLAIMED.getFormattedString());
            }
            return true;
        }

        return false;

    }

}
