package me.gurwi.athchunkclaim.commands.playercommands;

import me.gurwi.athchunkclaim.config.LanguageHandler;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChunkInfoCommand {

    public static boolean info(Player player) {

        Chunk chunk = player.getLocation().getChunk();

        if (!ChunksDatabase.inChunk(chunk)) {
            player.sendMessage(LanguageHandler.PREFIX.getFormattedString() + LanguageHandler.NO_CLAIMABLE_CHUNK_FOUND.getFormattedString());
            return true;
        }

        if (ChunksDatabase.getAllClaimedChunks().contains(chunk)) {

            PlayerChunk playerChunk = ChunksDatabase.getPlayerChunk(chunk);
            List<String> ownersName = new ArrayList<>();
            List<String> membersName = new ArrayList<>();

            playerChunk.getOwnersUUID().forEach(uuid -> {
                ownersName.add(Bukkit.getOfflinePlayer(uuid).getName());
            });

            playerChunk.getMembersUUID().forEach(uuid -> {
                membersName.add(Bukkit.getOfflinePlayer(uuid).getName());
            });

            String ownersNameResult = ownersName.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            String membersNameResult = membersName.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            String finalString;

            if (ownersName.isEmpty() && membersName.isEmpty()) {
                finalString = "N/D";
            } else if (!ownersName.isEmpty() && membersName.isEmpty()) {
                finalString = ownersNameResult;
            } else if (ownersName.isEmpty()) {
                finalString = membersNameResult;
            } else {
                finalString = ownersNameResult + ", " + membersNameResult;
            }

            player.sendMessage("");
            player.sendMessage("  §3§l» §f§lINFORMAZIONI CHUNK");
            player.sendMessage("");
            player.sendMessage("     §b§lPosizione: §7" + chunk.getX() + "x, " + chunk.getZ() + "z");
            player.sendMessage("     §b§lProprietario: §7" + Bukkit.getOfflinePlayer(playerChunk.getClaimerUUID()).getName());
            player.sendMessage("     §b§lMembri: §7" + finalString);
            player.sendMessage("");

        } else {

            if (ChunksDatabase.isUnclaimedChunk(chunk)) {

                player.sendMessage("");
                player.sendMessage("§3§l» §f§lINFORMAZIONI CHUNK");
                player.sendMessage("");
                player.sendMessage("    §b§lPosizione: §7" + chunk.getX() + "x, " + chunk.getZ() + "z");
                player.sendMessage("    §b§lProprietario: §7N/D");
                player.sendMessage("");
                player.sendMessage("  §3§o§lCHUNK NON CLAIMATO");
                player.sendMessage("");

            }

        }

        return false;

    }

}
