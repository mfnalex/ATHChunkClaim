package me.gurwi.athchunkclaim.database;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import me.gurwi.athchunkclaim.utils.APIManager;
import me.gurwi.athchunkclaim.config.ConfigHandler;
import me.gurwi.athchunkclaim.utils.worldguard.WorldGuardHandler;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ChunksDatabase {

    private static File dataFile;

    private static YamlConfiguration dataBase;

    public File getDataFile() {return dataFile;}

    public static YamlConfiguration getChunksDataBase() {return dataBase;}

    ////

    public static void saveChunksDataBase() {

        try {
            dataBase.save(dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setupChunkDataBase() {

        dataFile = new File(ATHChunkClaim.getInstance().getDataFolder(), "chunksdata.yml");
        if (!ATHChunkClaim.getInstance().getDataFolder().exists()) {

            ATHChunkClaim.getInstance().getDataFolder().mkdir();

        }

        if (!dataFile.exists()) {

            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        dataBase = YamlConfiguration.loadConfiguration(dataFile);

        if (!getChunksDataBase().isSet(".Claimed-Chunks")) {
            getChunksDataBase().createSection(".Claimed-Chunks");
            saveChunksDataBase();
        }

    }

    // METHODS

    public static void claimChunk(Chunk chunk, UUID claimerUUID) {

        List<UUID> members = new ArrayList<>();
        List<UUID> owners = new ArrayList<>();

        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "location." + "x", chunk.getX());
        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "location." + "z", chunk.getZ());
        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "location." + "world", chunk.getWorld().getName());

        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "permissions." + "owners." + "block-break", ConfigHandler.CHUNKS_DEFAULT_PERMISSIONS_BLOCKBREAK_OWNERS.getBoolean());
        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "permissions." + "owners." + "block-place", ConfigHandler.CHUNKS_DEFAULT_PERMISSIONS_BLOCKPLACE_OWNERS.getBoolean());
        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "permissions." + "owners." + "interact", ConfigHandler.CHUNKS_DEFAULT_PERMISSIONS_INTERACT_OWNERS.getBoolean());

        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "permissions." + "members." + "block-break", ConfigHandler.CHUNKS_DEFAULT_PERMISSIONS_BLOCKBREAK_MEMBERS.getBoolean());
        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "permissions." + "members." + "block-place", ConfigHandler.CHUNKS_DEFAULT_PERMISSIONS_BLOCKPLACE_MEMBERS.getBoolean());
        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "permissions." + "members." + "interact", ConfigHandler.CHUNKS_DEFAULT_PERMISSIONS_INTERACT_MEMBERS.getBoolean());

        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "claimer", claimerUUID.toString());
        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "owners", owners);
        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "members", members);
        saveChunksDataBase();

    }

    public static void unClaimChunk(Chunk chunk) {

        if (!getAllClaimedChunks().contains(chunk)) return;

        getChunksDataBase().set("Claimed-Chunks." + chunk, null);
        saveChunksDataBase();

    }

    public static void sellChunk(PlayerChunk playerChunk, Double price) {

        Chunk chunk = playerChunk.getChunk();

        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "in-sale." + "sell-price", price);
        saveChunksDataBase();

    }

    public static void unSellChunk(PlayerChunk playerChunk) {

        Chunk chunk = playerChunk.getChunk();

        if (getChunksDataBase().isSet("Claimed-Chunks." + chunk + "." + "in-sale." + "sell-price")) {
            getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "in-sale", null);
            saveChunksDataBase();
        }

    }

    public static boolean isInSale(PlayerChunk playerChunk) {
        Chunk chunk = playerChunk.getChunk();
        return getChunksDataBase().isSet("Claimed-Chunks." + chunk + "." + "in-sale." + "sell-price");
    }

    public static Double getSalePrice(PlayerChunk playerChunk) {
        Chunk chunk = playerChunk.getChunk();
        if (getChunksDataBase().isSet("Claimed-Chunks." + chunk + "." + "in-sale." + "sell-price")) {
            return getChunksDataBase().getDouble("Claimed-Chunks." + chunk + "." + "in-sale." + "sell-price");
        }
        return null;
    }

    public static PlayerChunk getPlayerChunk(Chunk chunk) {

        PlayerChunk playerChunk = new PlayerChunk();

        if (getAllClaimedChunks().contains(chunk)) {

            List<UUID> chunkOwners = new ArrayList<>();
            List<UUID> chunkMembers = new ArrayList<>();

            World world = Bukkit.getWorld(getChunksDataBase().getString("Claimed-Chunks." + chunk + "." + "location." + "world"));

            int x = getChunksDataBase().getInt("Claimed-Chunks." + chunk + "." + "location." + "x");
            int z = getChunksDataBase().getInt("Claimed-Chunks." + chunk + "." + "location." + "z");

            UUID claimerUUD = UUID.fromString(getChunksDataBase().getString("Claimed-Chunks." + chunk + "." + "claimer"));

            boolean ownerBlockBreak = getChunksDataBase().getBoolean("Claimed-Chunks." + chunk + "." + "permissions." + "owners." + "block-break");
            boolean ownerBlockPlace = getChunksDataBase().getBoolean("Claimed-Chunks." + chunk + "." + "permissions." + "owners." + "block-place");;
            boolean ownerInteract = getChunksDataBase().getBoolean("Claimed-Chunks." + chunk + "." + "permissions." + "owners." + "interact");

            boolean memberBlockBreak = getChunksDataBase().getBoolean("Claimed-Chunks." + chunk + "." + "permissions." + "members." + "block-break");
            boolean membersBlockPlace = getChunksDataBase().getBoolean("Claimed-Chunks." + chunk + "." + "permissions." + "members." + "block-place");;
            boolean memberInteract = getChunksDataBase().getBoolean("Claimed-Chunks." + chunk + "." + "permissions." + "members." + "interact");

            getChunksDataBase().getStringList("Claimed-Chunks." + chunk + "." + "owners").forEach(s -> {chunkOwners.add(UUID.fromString(s));});
            getChunksDataBase().getStringList("Claimed-Chunks." + chunk + "." + "members").forEach(s -> {chunkMembers.add(UUID.fromString(s));});

            playerChunk.setClaimerUUID(claimerUUD);
            playerChunk.setOwnersUUID(chunkOwners);
            playerChunk.setMembersUUID(chunkMembers);

            playerChunk.setChunk(chunk);
            playerChunk.setWorld(world);
            playerChunk.setX(x);
            playerChunk.setZ(z);

            playerChunk.setOwnerCanBreak(ownerBlockBreak);
            playerChunk.setOwnerCanPlace(ownerBlockPlace);
            playerChunk.setOwnerCanInteract(ownerInteract);

            playerChunk.setMemberCanBreak(memberBlockBreak);
            playerChunk.setMemberCanPlace(membersBlockPlace);
            playerChunk.setMemberCanInteract(memberInteract);

        }

        return playerChunk;

    }

    public static List<Chunk> getAllClaimedChunks() {

        List<Chunk> claimedChunks = new ArrayList<>();

        if (getChunksDataBase().getConfigurationSection("Claimed-Chunks.") == null) return claimedChunks;

        getChunksDataBase().getConfigurationSection("Claimed-Chunks.").getKeys(false).forEach(s -> {

            if (s.isEmpty()) return;

            String worldName = getChunksDataBase().getString("Claimed-Chunks." + s + "." + "location." + "world");

            if (worldName == null) return;

            World world = Bukkit.getWorld(worldName);

            if (world == null) return;

            int x = getChunksDataBase().getInt("Claimed-Chunks." + s + "." + "location." + "x");
            int y = getChunksDataBase().getInt("Claimed-Chunks." + s + "." + "location." + "z");

            Chunk chunk = world.getChunkAt(x, y);

            claimedChunks.add(chunk);

        });

        return claimedChunks;

    }

    public static List<PlayerChunk> getPlayerChunks(OfflinePlayer player) {

        List<PlayerChunk> playerChunks = new ArrayList<>();

        getChunksDataBase().getConfigurationSection("Claimed-Chunks.").getKeys(false).forEach(s -> {

            UUID chunkClaimerUUID = UUID.fromString(getChunksDataBase().getString("Claimed-Chunks." + s + "." + "claimer"));

            if (chunkClaimerUUID.equals(player.getUniqueId())) {

                PlayerChunk playerChunk = new PlayerChunk();

                List<UUID> chunkOwners = new ArrayList<>();
                List<UUID> chunkMembers = new ArrayList<>();

                int x = getChunksDataBase().getInt("Claimed-Chunks." + s + "." + "location." + "x");
                int z = getChunksDataBase().getInt("Claimed-Chunks." + s + "." + "location." + "z");

                UUID claimerUUD = UUID.fromString(getChunksDataBase().getString("Claimed-Chunks." + s + "." + "claimer"));
                World world = Bukkit.getWorld(getChunksDataBase().getString("Claimed-Chunks." + s + "." + "location." + "world"));
                Chunk chunk = world.getChunkAt(x, z);

                boolean ownerBlockBreak = getChunksDataBase().getBoolean("Claimed-Chunks." + s + "." + "permissions." + "owners." + "block-break");
                boolean ownerBlockPlace = getChunksDataBase().getBoolean("Claimed-Chunks." + s + "." + "permissions." + "owners." + "block-place");;
                boolean ownerInteract = getChunksDataBase().getBoolean("Claimed-Chunks." + s + "." + "permissions." + "owners." + "interact");

                boolean memberBlockBreak = getChunksDataBase().getBoolean("Claimed-Chunks." + s + "." + "permissions." + "members." + "block-break");
                boolean membersBlockPlace = getChunksDataBase().getBoolean("Claimed-Chunks." + s + "." + "permissions." + "members." + "block-place");;
                boolean memberInteract = getChunksDataBase().getBoolean("Claimed-Chunks." + s + "." + "permissions." + "members." + "interact");

                getChunksDataBase().getStringList("Claimed-Chunks." + s + "." + "owners").forEach(s1 -> {chunkOwners.add(UUID.fromString(s1));});
                getChunksDataBase().getStringList("Claimed-Chunks." + s + "." + "members").forEach(s1 -> {chunkMembers.add(UUID.fromString(s1));});

                playerChunk.setClaimerUUID(claimerUUD);
                playerChunk.setOwnersUUID(chunkOwners);
                playerChunk.setMembersUUID(chunkMembers);

                playerChunk.setChunk(chunk);
                playerChunk.setWorld(world);
                playerChunk.setX(x);
                playerChunk.setZ(z);

                playerChunk.setOwnerCanBreak(ownerBlockBreak);
                playerChunk.setOwnerCanPlace(ownerBlockPlace);
                playerChunk.setOwnerCanInteract(ownerInteract);

                playerChunk.setMemberCanBreak(memberBlockBreak);
                playerChunk.setMemberCanPlace(membersBlockPlace);
                playerChunk.setMemberCanInteract(memberInteract);

                playerChunks.add(playerChunk);

            }

        });

        return playerChunks;

    }

    public static Integer getPlayerChunksNum(Player player) {

        AtomicInteger num = new AtomicInteger();

        getAllClaimedChunks().forEach(chunk -> {
            if (getPlayerChunk(chunk).getClaimerUUID().equals(player.getUniqueId())) {
                num.getAndIncrement();
            }

        });

        return num.get();

    }

    public static boolean isUnclaimedChunk(Chunk chunk) {

        Location location = new Location(chunk.getWorld(), chunk.getBlock(6, 127, 6).getX(), chunk.getBlock(6, 127, 6).getY(), chunk.getBlock(6, 127, 6).getZ());

        if (APIManager.checkWorldGuard() && ConfigHandler.WORLDGUARD_SUPPORT.getBoolean()) {
            if (WorldGuardHandler.getWorldGuardManager().getLocationRegion(location) != null) {
                if (ConfigHandler.WORLDGUARD_CLAIMABLE_REGIONS.getListString().contains(WorldGuardHandler.getWorldGuardManager().getLocationRegion(location))) {
                    if (!ChunksDatabase.getAllClaimedChunks().contains(chunk)) {
                        return true;
                    }
                }
            }

            if (WorldGuardHandler.getWorldGuardManager().getLocationRegion(location) != null) {
                if (WorldGuardHandler.getWorldGuardManager().getLocationRegion(location).contains(ConfigHandler.WORLDGUARD_CLAIM_RG_CONTAINING.getString()) &&
                        !ConfigHandler.WORLDGUARD_NON_CLAIMABLE_REGIONS.getListString().contains(WorldGuardHandler.getWorldGuardManager().getLocationRegion(location))) {
                    if (!ChunksDatabase.getAllClaimedChunks().contains(chunk)) {
                        return true;
                    }
                }
            }
        }

        if (inChunk(chunk)) {
            return !ChunksDatabase.getAllClaimedChunks().contains(chunk);
        }

        return false;

    }

    public static boolean inChunk(Chunk chunk) {

        if (APIManager.checkWorldGuard()) {
            if (ConfigHandler.WORLDGUARD_SUPPORT.getBoolean()) {

                Location location = new Location(chunk.getWorld(), chunk.getBlock(6, 127, 6).getX(), chunk.getBlock(6, 127, 6).getY(), chunk.getBlock(6, 127, 6).getZ());

                if (WorldGuardHandler.getWorldGuardManager().getLocationRegion(location) == null) {
                    if (!ConfigHandler.WORLDGUARD_CLAIM_GLOBAL.getBoolean()) {
                        return false;
                    }
                }

                if (!ConfigHandler.WORLDGUARD_CLAIMABLE_REGIONS.getListString().isEmpty()) {
                    if (WorldGuardHandler.getWorldGuardManager().getLocationRegion(location) != null) {
                        if (!ConfigHandler.WORLDGUARD_CLAIMABLE_REGIONS.getListString().contains(WorldGuardHandler.getWorldGuardManager().getLocationRegion(location))) {
                            return false;
                        }
                    }
                }

                if (!ConfigHandler.WORLDGUARD_NON_CLAIMABLE_REGIONS.getListString().isEmpty()) {
                    if (WorldGuardHandler.getWorldGuardManager().getLocationRegion(location) != null) {
                        if (ConfigHandler.WORLDGUARD_NON_CLAIMABLE_REGIONS.getListString().contains(WorldGuardHandler.getWorldGuardManager().getLocationRegion(location))) {
                            return false;
                        }
                    }
                }

                if (WorldGuardHandler.getWorldGuardManager().getLocationRegion(location) != null) {
                    if (!WorldGuardHandler.getWorldGuardManager().getLocationRegion(location).contains(ConfigHandler.WORLDGUARD_CLAIM_RG_CONTAINING.getString())) {
                        return false;
                    }
                }

            }
        }

        return true;

    }

    public static void addChunkOwner(PlayerChunk playerChunk, Player owner) {

        List<String> ownersString = new ArrayList<>();
        List<UUID> ownersUUID = new ArrayList<>();
        Chunk chunk = playerChunk.getChunk();

        playerChunk.getOwnersUUID().forEach(uuid -> {
            ownersUUID.add(uuid);
            ownersString.add(uuid.toString());
        });

        ownersUUID.add(owner.getUniqueId());
        ownersString.add(owner.getUniqueId().toString());
        playerChunk.setOwnersUUID(ownersUUID);
        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "owners", ownersString);
        saveChunksDataBase();

    }

    public static void addChunkMember(PlayerChunk playerChunk, Player member) {

        List<String> membersString = new ArrayList<>();
        List<UUID> membersUUID = new ArrayList<>();
        Chunk chunk = playerChunk.getChunk();

        playerChunk.getMembersUUID().forEach(uuid -> {
            membersUUID.add(uuid);
            membersString.add(uuid.toString());
        });

        membersUUID.add(member.getUniqueId());
        membersString.add(member.getUniqueId().toString());
        playerChunk.setMembersUUID(membersUUID);
        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "members", membersString);
        saveChunksDataBase();

    }

    public static void removeChunkOwner(PlayerChunk playerChunk, OfflinePlayer owner) {

        List<UUID> ownersUUID = new ArrayList<>();
        List<String> ownersString = new ArrayList<>();
        Chunk chunk = playerChunk.getChunk();

        ownersUUID = playerChunk.getOwnersUUID();
        ownersUUID.remove(owner.getUniqueId());
        playerChunk.setOwnersUUID(ownersUUID);

        playerChunk.getOwnersUUID().forEach(uuid -> {
            ownersString.add(uuid.toString());
        });

        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "owners", ownersString);
        saveChunksDataBase();

    }

    public static void removeChunkMember(PlayerChunk playerChunk, OfflinePlayer member) {

        List<UUID> membersUUID = new ArrayList<>();
        List<String> membersString = new ArrayList<>();
        Chunk chunk = playerChunk.getChunk();

        membersUUID = playerChunk.getMembersUUID();
        membersUUID.remove(member.getUniqueId());
        playerChunk.setMembersUUID(membersUUID);

        playerChunk.getMembersUUID().forEach(uuid -> {
            membersString.add(uuid.toString());
        });

        getChunksDataBase().set("Claimed-Chunks." + chunk + "." + "members", membersString);
        saveChunksDataBase();

    }

    public static List<OfflinePlayer> getChunkOwners(PlayerChunk playerChunk) {
        List<OfflinePlayer> owners = new ArrayList<>();
        playerChunk.getOwnersUUID().forEach(uuid -> {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(uuid);
            owners.add(owner);
        });
        return owners;
    }

    public static List<OfflinePlayer> getChunkMembers(PlayerChunk playerChunk) {
        List<OfflinePlayer> members = new ArrayList<>();
        playerChunk.getMembersUUID().forEach(uuid -> {
            OfflinePlayer member = Bukkit.getOfflinePlayer(uuid);
            members.add(member);
        });
        return members;
    }

    public static boolean isClaimedChunk(Chunk chunk) {return getAllClaimedChunks().contains(chunk);}

}
