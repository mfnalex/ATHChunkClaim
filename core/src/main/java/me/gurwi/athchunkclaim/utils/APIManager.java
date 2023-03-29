package me.gurwi.athchunkclaim.utils;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import me.gurwi.athchunkclaim.utils.worldguard.WorldGuardHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class APIManager {

    public static boolean checkWorldGuardOnLoad() {
        return ATHChunkClaim.getInstance().getServer().getPluginManager().getPlugin("WorldGuard") != null;
    }

    public static boolean checkWorldGuard() {
        return ATHChunkClaim.getInstance().getServer().getPluginManager().getPlugin("WorldGuard") != null && WorldGuardHandler.isWorldGuardLoaded();
    }

    public static boolean setupEconomy() {
        if (ATHChunkClaim.getInstance().getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = ATHChunkClaim.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        ATHChunkClaim.econ = (Economy)rsp.getProvider();
        return (ATHChunkClaim.econ != null);
    }

}
