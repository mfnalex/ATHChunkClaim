package me.gurwi.athchunkclaim.commands.commandshandler;

import me.gurwi.athchunkclaim.config.LanguageHandler;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import me.gurwi.athchunkclaim.utils.BasicFunctions;
import me.gurwi.athchunkclaim.utils.IntCheck;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class ATHCACommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission("athchunkclaim.*") || player.hasPermission("athchunclaim.admin")) {

                if (args.length == 0) {
                    player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + "§7Fai §b/chunkadmin help §7per ottenere la lista di tutti i comandi staff disponibili.");
                    return true;
                }

                if (args[0].equalsIgnoreCase("unclaim")) {

                    Chunk chunk = player.getLocation().getChunk();

                    if (!ChunksDatabase.inChunk(chunk)) {
                        player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.NO_CLAIMABLE_CHUNK_FOUND.getFormattedString());
                        return true;
                    }

                    if (ChunksDatabase.getAllClaimedChunks().contains(chunk)) {

                        PlayerChunk playerChunk = ChunksDatabase.getPlayerChunk(chunk);

                        ChunksDatabase.unClaimChunk(chunk);
                        player.sendMessage("§8§l» §aIl chunk di §2" + Bukkit.getOfflinePlayer(playerChunk.getClaimerUUID()).getName() + " §a è stato unclaimato con successo!");

                    } else {
                        player.sendMessage("§8§l» §cQuesto chunk non è stato claimato da nessuno!");
                    }

                } else if (args[0].equalsIgnoreCase("list")) {

                    if (args.length < 2) {
                        player.sendMessage("§8§l» §cInserisci il nick del player a cui vuoi trovare i chunk!");
                        return false;
                    }

                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    AtomicInteger num = new AtomicInteger();

                    player.sendMessage("");
                    player.sendMessage("§8§l» §3§lLISTA CHUNK DI " + target.getName());
                    player.sendMessage("");

                    ChunksDatabase.getPlayerChunks(target).forEach(playerChunk -> {

                        Chunk chunk = playerChunk.getChunk();

                        num.getAndIncrement();
                        Location tpLocation = new Location(chunk.getWorld(), (chunk.getX() - 1) * 16 + 8, 64, (chunk.getZ() - 1) * 16 + 8);

                        TextComponent tpButton = new TextComponent("§7(TP)");
                        tpButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/chunkadmin tpchunk " + chunk.getX() + " " + chunk.getZ()));
                        tpButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8§l» §bTeletrasportati al chunk").create()));

                        TextComponent tpButtonMessage = new TextComponent("   §8" + num.get() + ") §b" + playerChunk.getX() + "§3x§7, §b" + playerChunk.getZ() + "§3z ");

                        //player.sendMessage("   §8" + num.get() + ") §b" + playerChunk.getX() + "§3x§7, §b" + playerChunk.getZ() + "§3z");

                        player.spigot().sendMessage(tpButtonMessage, tpButton);

                    });

                } else if (args[0].equalsIgnoreCase("tpchunk")) {

                    if (args.length < 3) {
                        player.sendMessage("§8§l» §cUtilizzo corretto: &b/chunkadmin chunktp [x] [z]§c!");
                        return false;
                    }

                    if (!IntCheck.isInt(args[1]) || !IntCheck.isInt(args[2])) {
                        player.sendMessage("§8§l» §cLe coordinate §4x §ce §4z §cdevono essere un numero intero!");
                        return false;
                    }

                    Chunk chunk = player.getWorld().getChunkAt(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    BasicFunctions.tpToChunk(chunk, player);

                } else if (args[0].equalsIgnoreCase("help")) {

                    player.sendMessage("");
                    player.sendMessage("§3§l» §b§lATHChunks ADMIN §8§l(§f§lv1.2.0§8§l)");
                    player.sendMessage("  §7/chunkadmin unclaim §fUnclaima il chunk in cui ti trovi.");
                    player.sendMessage("  §7/chunkadmin tpchunk [x] [z] §fTeletrasportati in un chunk.");
                    player.sendMessage("  §7/chunkadmin list <Player> §fMostra tutti i chunk del player scelto.");
                    player.sendMessage("  §7/chunkadmin reload §fReloada il config del plugin.");
                    player.sendMessage("");

                } else {
                    player.sendMessage("§8§l» §cComando non trovato! §7Fai §b/chunkadmin help §7ottenere la lista di tutti i comandi staff disponibili.");
                }

            } else {
                player.sendMessage("§8§l» §cNon disponi dei permessi necessari richiesti per poter utilizzare questo comando!");
            }

        }

        return false;
    }
}
