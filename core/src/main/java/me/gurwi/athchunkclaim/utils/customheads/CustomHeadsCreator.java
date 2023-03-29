package me.gurwi.athchunkclaim.utils.customheads;

import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class CustomHeadsCreator {

    public static ItemStack createSkull(String url, String name) {

        ItemStack head = XMaterial.PLAYER_HEAD.parseItem();
        if (url.isEmpty()) return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {

            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        head.setItemMeta(headMeta);

        return head;

    }

}
