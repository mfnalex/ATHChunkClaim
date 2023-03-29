package me.gurwi.athchunkclaim.utils.worldguard;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldGuardHandler {

    private static String serverVer;
    private static WorldGuardManager worldGuardManager;
    private static Boolean worldGuardLoaded = false;

    public static void loadWorldGuard() {

        ATHChunkClaim.getInstance().getLogger().info("§6[WorldGuard] §6§l§o LOADING §r §eLoading WorldGuard methods.");

        serverVer = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (serverVer == null || serverVer.isEmpty()) {
            System.out.println("§4[WorldGuard] §4§l§o ERROR §r §cCouldn' t load WorldGuard! Report this to the developer §f-> §aVersion Not Found");
            return;
        }

        if (!getSupportedVersion().contains(serverVer)) {
            ATHChunkClaim.getInstance().getLogger().info("§4[WorldGuard] §4§l§o ERROR §r §cCouldn' t load WorldGuard! Unsupported WorldGuard Version.");
            worldGuardLoaded = false;
            return;
        }

        if (serverVer.equals("v1_12_R1")) {
            worldGuardManager = new WorldGuardAPI_Legacy();
        } else {
            worldGuardManager = new WorldGuardAPI_NL();
        }
        worldGuardLoaded = true;
        ATHChunkClaim.getInstance().getLogger().info("§2[WorldGuard] §2§l§o INFO §r §aWorldGuard for version §f" + serverVer + " §awas loaded successfully.");

    }

    public static WorldGuardManager getWorldGuardManager() {
        return worldGuardManager;
    }

    public static Boolean isWorldGuardLoaded() {
        return worldGuardLoaded;
    }

    public static List<String> getSupportedVersion() {
        return Arrays.asList("v1_12_R1", "v1_13_R1", "v1_14_R1", "v1_15_R1", "v1_16_R1", "v1_17_R1", "v1_18_R1", "v1_19_R1");
    }

}
