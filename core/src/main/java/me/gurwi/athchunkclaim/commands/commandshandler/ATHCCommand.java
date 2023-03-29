package me.gurwi.athchunkclaim.commands.commandshandler;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import me.gurwi.athchunkclaim.commands.playercommands.*;
import me.gurwi.athchunkclaim.config.LanguageHandler;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import me.gurwi.athchunkclaim.utils.APIManager;
import me.gurwi.athchunkclaim.config.ConfigHandler;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ATHCCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 0) {
                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.NO_ARGS.getFormattedString());
                return false;
            }

            if (args[0].equalsIgnoreCase("claim")) {
                ClaimCommand.claim(player);

            } else if (args[0].equalsIgnoreCase("unclaim")) {
                UnClaimCommand.unClaim(player);

            } else if (args[0].equalsIgnoreCase("info")) {
                ChunkInfoCommand.info(player);

            } else if (args[0].equalsIgnoreCase("list")) {
                PlayerChunkListGUICommand.chunksList(player);
                
            } else if (args[0].equalsIgnoreCase("sell")) {

                if (APIManager.setupEconomy()) {
                    if (ConfigHandler.VAULT_SUPPORT.getBoolean()) {

                        Chunk chunk = player.getLocation().getChunk();
                        PlayerChunk playerChunk = ChunksDatabase.getPlayerChunk(chunk);

                        if (ConfigHandler.VAULT_CHUNK_CUSTOM_SELL.getBoolean()) {

                            if (!ChunksDatabase.inChunk(chunk)) {
                                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.NO_CLAIMABLE_CHUNK_FOUND.getFormattedString());
                                return true;
                            }

                            if (ChunksDatabase.isUnclaimedChunk(chunk)) {
                                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.UNCLAIMED_CHUNK_ERORR.getFormattedString());
                                return true;
                            }

                            if (!playerChunk.getClaimerUUID().equals(player.getUniqueId())) {
                                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.ONLY_CHUNK_OWNER_CAN_SELL.getFormattedString());
                                return true;
                            }

                            if (args.length < 2) {
                                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.SELL_PRICE_MISSING.getFormattedString());
                                return true;
                            }

                            double sellPrice = Double.parseDouble(args[1]);
                            ChunkSellCommand.sell(player, sellPrice);

                        } else {
                            ChunksDatabase.sellChunk(playerChunk, ConfigHandler.VAULT_CHUNK_DEFAULT_SELL_PRICE.getDouble());
                            player.sendMessage(LanguageHandler.CHUNK_SELL_SUCCESSFUL.getFormattedString().replace("%sell_price%", ATHChunkClaim.getEconomy().format(ConfigHandler.VAULT_CHUNK_CLAIM_PRICE.getDouble())));
                        }

                    } else {
                        player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.FUNCTION_NOT_AVAILABLE.getFormattedString());
                    }

                } else {
                    player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.MISSING_VAULT.getFormattedString());
                }

            } else if (args[0].equalsIgnoreCase("unsell")) {

                Chunk chunk = player.getLocation().getChunk();

                if (!ChunksDatabase.inChunk(chunk)) {
                    player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.NO_CLAIMABLE_CHUNK_FOUND.getFormattedString());
                    return true;
                }

                if (ChunksDatabase.isUnclaimedChunk(chunk)) {
                    player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.UNCLAIMED_CHUNK_ERORR.getFormattedString());
                    return true;
                }

                ChunkUnSellCommand.chunkUnSell(player);

            } else if (args[0].equalsIgnoreCase("manage")) {
                ChunkManageCommand.chunkManage(player);

            } else if (args[0].equalsIgnoreCase("help")) {

                player.sendMessage("");
                player.sendMessage("§3§l» §b§lATHChunkClaim §8§l(§f§lv1.2.0§8§l)");
                player.sendMessage("  §7/chunk claim §fClaima il chunk in cui ti trovi");
                player.sendMessage("  §7/chunk unclaim §fUnclaima il chunk in cui ti trovi.");
                player.sendMessage("  §7/chunk manage §fGestisci il chunk in cui ti trovi.");
                player.sendMessage("  §7/chunk list §fMostra la lista di tutti i chunk in tuo possesso.");
                player.sendMessage("  §7/chunk sell §fVendi il chunk in cui ti trovi.");
                player.sendMessage("  §7/chunk unsell §fRimuovi dalla vendità il chunk in cui ti trovi.");
                player.sendMessage("  §7/chunk info §fOttieni maggiori informazioni sul chunk in cui ti trovi.");
                player.sendMessage("  §7/chunk version §fOttieni maggiori informazioni su questo plugin");
                player.sendMessage("");

            } else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver")) {
                player.sendMessage("");
                player.sendMessage("    §7ATHChunkClaim v1.2.0");
                player.sendMessage("    §7By @Gurwi30");
                player.sendMessage("");

            } else {
                player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.COMMAND_NOT_FOUND.getFormattedString());
            }

        }

        return false;
    }

}
