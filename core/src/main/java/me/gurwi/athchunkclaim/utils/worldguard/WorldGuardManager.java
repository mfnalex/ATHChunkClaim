package me.gurwi.athchunkclaim.utils.worldguard;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface WorldGuardManager {

    public String getPlayerRegion(Player player);
    public String getLocationRegion(Location location);

}
