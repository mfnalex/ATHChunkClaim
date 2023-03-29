package me.gurwi.athchunkclaim.guis.guiitems;

import com.cryptomorin.xseries.XMaterial;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.objects.PlayerChunk;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class GUIItemsManager {

    public static ItemStack bottomTopFiller() {
        ItemStack bottomTopFillerGlass = XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE.parseItem();
        ItemMeta bottomTopFillerGlassMeta = bottomTopFillerGlass.getItemMeta();
        bottomTopFillerGlassMeta.setDisplayName("§7");
        bottomTopFillerGlass.setItemMeta(bottomTopFillerGlassMeta);

        return bottomTopFillerGlass;
    }

    public static ItemStack getChunkIcon(Integer plotN, PlayerChunk playerChunk) {

        ItemStack head = XMaterial.PLAYER_HEAD.parseItem();
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        if (!ChunksDatabase.isInSale(playerChunk)) {
            headMeta.setOwner("cake");
        } else {
            headMeta.setOwner("HotdogParmesan");
        }
        headMeta.setDisplayName("§8§l× §3Plot §b" + plotN);

        if (!ChunksDatabase.isInSale(playerChunk)) {
            headMeta.setLore(Arrays.asList("", "§b§lPOSIZIONE §7" + playerChunk.getX() + "x, " + playerChunk.getZ() + "z", "§b§lPROPRIETARIO §7" + Bukkit.getOfflinePlayer(playerChunk.getClaimerUUID()).getName(), "", "§f§lCLICCA §7Per gestire il chunk", ""));
        } else {
            headMeta.setLore(Arrays.asList("", "§b§lPOSIZIONE §7" + playerChunk.getX() + "x, " + playerChunk.getZ() + "z", "§b§lPROPRIETARIO §7" + Bukkit.getOfflinePlayer(playerChunk.getClaimerUUID()).getName(), "", "§e§lCHUNK IN VENDITA'", "", "§f§lCLICCA §7Per gestire il chunk", ""));
        }

        head.setItemMeta(headMeta);

        return head;

    }

    public static ItemStack getPlayerHead(Player player) {

        ItemStack head = XMaterial.PLAYER_HEAD.parseItem();
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwner(player.getName());
        headMeta.setDisplayName("§8§l× §f" + player.getName());
        head.setItemMeta(headMeta);

        return head;

    }

    public static ItemStack getPlayerHead(OfflinePlayer player) {

        ItemStack head = XMaterial.PLAYER_HEAD.parseItem();
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwner(player.getName());
        headMeta.setDisplayName("§8§l× §f" + player.getName());
        headMeta.setLore(Arrays.asList("", "§f§lCLICK §7Rimuovi player"));
        head.setItemMeta(headMeta);

        return head;

    }


}
