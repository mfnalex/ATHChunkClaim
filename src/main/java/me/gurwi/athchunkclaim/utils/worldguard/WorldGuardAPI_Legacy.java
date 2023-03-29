package me.gurwi.athchunkclaim.utils.worldguard;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardAPI_Legacy implements WorldGuardManager {

    public String getPlayerRegion(Player player) {

        Location location = player.getLocation();

        for (ProtectedRegion regions : WGBukkit.getRegionManager(location.getWorld()).getApplicableRegions(location)) {
            if (regions.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                return regions.getId();
            }
        }

        return null;

    }

    public String getLocationRegion(Location location) {

        for (ProtectedRegion regions : WGBukkit.getRegionManager(location.getWorld()).getApplicableRegions(location)) {
            if (regions.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                return regions.getId();
            }
        }

        return null;

    }

}
