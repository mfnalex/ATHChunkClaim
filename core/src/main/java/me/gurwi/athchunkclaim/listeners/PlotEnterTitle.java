package me.gurwi.athchunkclaim.listeners;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import me.gurwi.athchunkclaim.utils.BasicFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlotEnterTitle implements Listener {

    private static final HashMap<UUID, PlayerChunk> plot = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        Chunk chunk = player.getLocation().getChunk();

        if (ChunksDatabase.isClaimedChunk(chunk)) {

            PlayerChunk playerChunk = ChunksDatabase.getPlayerChunk(chunk);

            if (!ChunksDatabase.isInSale(playerChunk)) {


                if (plot.containsKey(player.getUniqueId())) {
                    if (plot.get(player.getUniqueId()) != null) {
                        if (plot.get(player.getUniqueId()).getClaimerUUID().equals(playerChunk.getClaimerUUID())) {
                            if (!ChunksDatabase.isInSale(plot.get(player.getUniqueId()))) {
                                return;
                            }
                        }
                    } else {
                        plot.remove(player.getUniqueId());
                        plot.put(player.getUniqueId(), playerChunk);
                    }
                }

                if (plot.containsKey(player.getUniqueId())) {
                    if (plot.get(player.getUniqueId()) != null) {
                        if (ChunksDatabase.isInSale(plot.get(player.getUniqueId()))) {
                            plot.remove(player.getUniqueId());
                            plot.put(player.getUniqueId(), playerChunk);
                        }
                    }
                }

                plot.put(player.getUniqueId(), playerChunk);
                player.sendTitle("", "§b§l> §7§lSei entrato nel plot di §b§l" + Bukkit.getOfflinePlayer(playerChunk.getClaimerUUID()).getName(), 0, 20, 20);

            } else {

                if (!plot.containsKey(player.getUniqueId())) {
                    plot.put(player.getUniqueId(), playerChunk);

                } else if (plot.get(player.getUniqueId()) == null) {
                    plot.remove(player.getUniqueId());
                    plot.put(player.getUniqueId(), playerChunk);

                } else if (plot.get(player.getUniqueId()).getChunk().equals(chunk)) {
                    return;
                }

                plot.put(player.getUniqueId(), playerChunk);
                player.sendTitle("§e§lChunk In Vendità", "§b§l> §7§lSei entrato nel plot di §b§l" + Bukkit.getOfflinePlayer(playerChunk.getClaimerUUID()).getName(), 0, 20, 20);

                player.sendMessage("");
                player.sendMessage("   §8§l[ §e§lCHUNK IN VENDITA' §8§l]");
                player.sendMessage("   ");
                player.sendMessage("   §fQuesto chunk risulta essere in vendità,");
                player.sendMessage("   §fcompralo ora facendo §e/chunk claim.");
                player.sendMessage("");
                player.sendMessage("   §6§lPosizione: §7" + playerChunk.getX() + "x, " + playerChunk.getZ() + "z");
                player.sendMessage("   §6§lVenditore: §7" + Bukkit.getOfflinePlayer(playerChunk.getClaimerUUID()).getName());
                player.sendMessage("   §6§lPrezzo: §7" + ATHChunkClaim.getEconomy().format(ChunksDatabase.getSalePrice(playerChunk)));
                player.sendMessage("");

            }

        } else if (ChunksDatabase.isUnclaimedChunk(chunk)) {

            if (!plot.containsKey(player.getUniqueId())) {

                plot.put(player.getUniqueId(), null);
                player.sendTitle("", "§b§l> §3§lPlot libero", 0, 20, 20);

                if (BasicFunctions.canClaim(player)) {

                    player.sendMessage("");
                    player.sendMessage("   §8§l[ §b§lCHUNK LIBERO! §8§l]");
                    player.sendMessage("   ");
                    player.sendMessage("   §fQuesto chunk risulta essere libero, claimalo");
                    player.sendMessage("   §fora facendo §b/chunk claim§f, pagando 5.000$");
                    player.sendMessage("");

                }

            } else {

                if (plot.get(player.getUniqueId()) != null) {

                    if (plot.get(player.getUniqueId()) != null) {
                        plot.remove(player.getUniqueId());
                    }

                    plot.put(player.getUniqueId(), null);
                    player.sendTitle("", "§b§l> §3§lPlot libero", 0, 20, 20);

                    if (BasicFunctions.canClaim(player)) {

                        player.sendMessage("");
                        player.sendMessage("   §8§l[ §b§lCHUNK LIBERO! §8§l]");
                        player.sendMessage("   ");
                        player.sendMessage("   §fQuesto chunk risulta essere libero, claimalo");
                        player.sendMessage("   §fora facendo §b/chunk claim§f, pagando 5.000$");
                        player.sendMessage("");

                    }

                }

            }

        } else {

            plot.remove(player.getUniqueId());

        }

    }

}
