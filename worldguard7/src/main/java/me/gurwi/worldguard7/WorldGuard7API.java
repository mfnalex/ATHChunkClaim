package me.gurwi.worldguard7;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.gurwi.athchunkclaim.utils.worldguard.WorldGuardManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class WorldGuard7API implements WorldGuardManager {

    @Override
    public String getPlayerRegion(Player player) {

        Location location = player.getLocation();
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);;

        for (ProtectedRegion regions : WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(loc)) {
            if (regions.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                return regions.getId();
            }
        }

        return null;

    }

    @Override
    public String getLocationRegion(Location location) {

        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);;

        for (ProtectedRegion regions : WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(loc)) {
            if (regions.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                return regions.getId();
            }
        }

        return null;

    }

}
