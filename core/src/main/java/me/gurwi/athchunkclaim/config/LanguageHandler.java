package me.gurwi.athchunkclaim.config;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import net.md_5.bungee.api.ChatColor;

import java.util.List;

public enum LanguageHandler {

    PREFIX("Messages.Prefix"),
    NO_ARGS("Messages.No-args"),
    COMMAND_NOT_FOUND("Messages.Command-not-found"),
    FUNCTION_NOT_AVAILABLE("Messages.Function-not-available"),
    MISSING_VAULT("Messages.Function-not-available-missing-vault"),

    DISABLED_CLAIM_WORLD("Messages.Claims-disabled-world"),
    IMPOSSIBLE_TO_CLAIM_CHUNK("Messages.Impossible-to-claim-chunk"),
    NO_CLAIMABLE_CHUNK_FOUND("Messages.No-claimable-chunk-found"),

    NO_CLAIM_PERMISSIONS("Messages.Insufficient-permissions-to-claim"),
    MAX_CLAIMS_REACHED("Messages.Max-claims-reached"),
    CHUNK_ALREADY_CLAIMED("Messages.Chunk-already-claimed"),
    INSUFFICENT_BALANCE_TO_CLAIM("Messages.Insufficient-balance-to-claim"),
    UNCLAIMED_CHUNK_ERORR("Messages.Unclaimed-chunk"),

    CHUNK_SOLD("Messages.Chunk-sold"),
    CHUNK_CLAIMED("Messages.Chunk-claimed"),

    ONLY_CHUNK_OWNER_CAN_SELL("Messages.Only-chunk-owner-can-sell"),
    ONLY_CHUNK_OWNER_CAN_UNCLAIM("Messages.Only-chunk-owner-can-unclaim"),

    SELL_PRICE_MISSING("Messages.Missing-sell-price"),

    CHUNK_SELL_SUCCESSFUL("Messages.Chunk-sell-successful"),

    CLAIMED_CHUNK_INFO("Messages.Claimed-Chunk-Info");
    
    // METHODS
    private final String path;

    private LanguageHandler(String path) {this.path = path;}


    public List<String> getListString() {return LanguagesManager.getLangFile().getStringList(this.path);}
    public String getFormattedString() {return ChatColor.translateAlternateColorCodes('&', LanguagesManager.getLangFile().getString(this.path));}
    public String getString() {return LanguagesManager.getLangFile().getString(this.path);}
    public Boolean getBoolean() {return LanguagesManager.getLangFile().getBoolean(this.path);}
    public Integer getInt() {return LanguagesManager.getLangFile().getInt(this.path);}
    public Double getDouble() {return LanguagesManager.getLangFile().getDouble(this.path);}
    
}
