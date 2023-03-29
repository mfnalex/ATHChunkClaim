package me.gurwi.worldguard6;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.gurwi.athchunkclaim.utils.worldguard.WorldGuardManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class WorldGuard6API implements WorldGuardManager {

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
