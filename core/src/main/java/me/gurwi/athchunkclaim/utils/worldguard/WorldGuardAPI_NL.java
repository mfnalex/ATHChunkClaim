package me.gurwi.athchunkclaim.utils.worldguard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class WorldGuardAPI_NL implements WorldGuardManager{

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
