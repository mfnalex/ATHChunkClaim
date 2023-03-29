package me.gurwi.athchunkclaim.config;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public enum ConfigHandler {

    LICENSE("Options.license"),
    LANG("Options.lang"),
    DISABLED_WORLDS("Options.disabled-worlds"),

    NEED_PERMISSION_TO_CLAIM("need-permission-to-claim"),
    MAX_DEFAULT_CLAIMS("max-claims-by-default"),

    OPEN_PLAYER_EMPTY_CHUNKS_GUI("open-empty-player-chunks-gui"),


    VAULT_SUPPORT("Options.vault-support"),
    VAULT_CHUNK_CLAIM_PRICE("Options.economy-system.chunk-claim-price"),
    VAULT_CHUNK_DEFAULT_SELL_PRICE("Options.economy-system.chunk-default-sell-price"),
    VAULT_CHUNK_CUSTOM_SELL("Options.economy-system.custom-sell-price"),

    WORLDGUARD_SUPPORT("Options.worldguard-support"),
    WORLDGUARD_USE_CUSTOM_FLAG("Options.worldguard.use-custom-claim-flag"),
    WORLDGUARD_CLAIMABLE_REGIONS("Options.worldguard.claimable-regions"),
    WORLDGUARD_NON_CLAIMABLE_REGIONS("Options.worldguard.non-claimable-regions"),
    WORLDGUARD_CLAIM_RG_CONTAINING("Options.worldguard.claim-rg-containing"),
    WORLDGUARD_CLAIM_GLOBAL("Options.worldguard.claim-global"),

    DISABLED_ACTIONS_UNCLAIMED_CHUNK_BLOCKBREAK("Options.disabled-actions.unclaimed-chunks.block-break"),
    DISABLED_ACTIONS_UNCLAIMED_CHUNK_BLOCKPLACE("Options.disabled-actions.unclaimed-chunks.place"),
    DISABLED_ACTIONS_UNCLAIMED_CHUNK_INTERACTIONS("Options.disabled-actions.unclaimed-chunks.interactions"),
    DISABLED_ACTIONS_UNCLAIMED_CHUNK_PVP("Options.disabled-actions.unclaimed-chunks.disable-pvp"),

    DISABLED_ACTIONS_CLAIMED_CHUNK_BLOCKBREAK("Options.disabled-actions.claimed-chunks.block-break"),
    DISABLED_ACTIONS_CLAIMED_CHUNK_BLOCKPLACE("Options.disabled-actions.claimed-chunks.place"),
    DISABLED_ACTIONS_CLAIMED_CHUNK_INTERACTIONS("Options.disabled-actions.claimed-chunks.interactions"),
    DISABLED_ACTIONS_CLAIMED_CHUNK_PVP("Options.disabled-actions.claimed-chunks.disable-pvp"),

    CHUNKS_DEFAULT_PERMISSIONS_BLOCKBREAK_OWNERS("Options.chunks-default-permissions.owners.block-break"),
    CHUNKS_DEFAULT_PERMISSIONS_BLOCKPLACE_OWNERS("Options.chunks-default-permissions.owners.block-place"),
    CHUNKS_DEFAULT_PERMISSIONS_INTERACT_OWNERS("Options.chunks-default-permissions.owners.interact"),

    CHUNKS_DEFAULT_PERMISSIONS_BLOCKBREAK_MEMBERS("Options.chunks-default-permissions.members.block-break"),
    CHUNKS_DEFAULT_PERMISSIONS_BLOCKPLACE_MEMBERS("Options.chunks-default-permissions.members.block-place"),
    CHUNKS_DEFAULT_PERMISSIONS_INTERACT_MEMBERS("Options.chunks-default-permissions.members.interact");

    // METHODS

    private final String path;

    private ConfigHandler(String path) {this.path = path;}


    public List<String> getListString() {return ATHChunkClaim.getInstance().getConfig().getStringList(this.path);}
    public String getFormattedString() {return ChatColor.translateAlternateColorCodes('&', ATHChunkClaim.getInstance().getConfig().getString(this.path));}
    public String getString() {return ATHChunkClaim.getInstance().getConfig().getString(this.path);}
    public Boolean getBoolean() {
        return ATHChunkClaim.getInstance().getConfig().getBoolean(this.path);
    }
    public Integer getInt() {
        return ATHChunkClaim.getInstance().getConfig().getInt(this.path);
    }
    public Double getDouble() {return ATHChunkClaim.getInstance().getConfig().getDouble(this.path);}

    public List<World> getDisabledWorldsList() {
        List<World> disabledWorlds = new ArrayList<>();
        disabledWorlds.add(Bukkit.getWorld(this.path));
        return disabledWorlds;
    }

}
