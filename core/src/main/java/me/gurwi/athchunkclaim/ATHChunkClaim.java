package me.gurwi.athchunkclaim;

import me.gurwi.athchunkclaim.commands.commandshandler.ATHCACommand;
import me.gurwi.athchunkclaim.commands.commandshandler.ATHCCommand;
import me.gurwi.athchunkclaim.config.LanguagesManager;
import me.gurwi.athchunkclaim.database.ChunksDatabase;
import me.gurwi.athchunkclaim.listeners.ChatEventsHandler;
import me.gurwi.athchunkclaim.listeners.ClaimedChunkEventsHandler;
import me.gurwi.athchunkclaim.listeners.PlotEnterTitle;
import me.gurwi.athchunkclaim.listeners.UnClaimedChunkEventsHandler;
import me.gurwi.athchunkclaim.listeners.chat.AddMemberChat;
import me.gurwi.athchunkclaim.listeners.chat.AddOwnerChat;
import me.gurwi.athchunkclaim.listeners.chat.SellChunkChat;
import me.gurwi.athchunkclaim.utils.APIManager;
import me.gurwi.athchunkclaim.utils.ATHManager;
import me.gurwi.athchunkclaim.config.ConfigHandler;
import me.gurwi.athchunkclaim.utils.worldguard.WorldGuardHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;

public final class ATHChunkClaim extends JavaPlugin {

    private static ATHChunkClaim instance;
    public ATHChunkClaim() {instance = this;}

    public static Economy econ = null;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        // CONFIG

        saveDefaultConfig();
        LanguagesManager.loadLangFile();

        // LICENSE CHECK

        getLogger().info("§6§l§o INFO §r §eChecking license key...");

        if (new ATHManager(URI.create("http://45.141.57.119:3000/api/client"), ConfigHandler.LICENSE.getString(), "ATHChunkClaim", "1.0", "5XH3yrjAQKQvjFfP2TgDbDvikQnFpF4W", ATHManager.getHWID()).check()) {
            getLogger().info("§2§l§o INFO §r §aValid license found!");
        } else {
            getLogger().info("§4§l§o ERROR §r §cInvalid license, re-check it in the config! Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // DATABASE

        ChunksDatabase.setupChunkDataBase();

        // API

        if (APIManager.setupEconomy()) {
            if (ConfigHandler.VAULT_SUPPORT.getBoolean()) {
                getLogger().info("§2§l§o INFO §r §aSuccessfully hooked in to Vault!");
            } else {
                getLogger().info("§6§l§o INFO §r §eVault Found! Vault support is disabled. §ePassing!");
            }

        } else {
            getLogger().info("§6§l§o INFO §r §eCouldn't find Vault! §ePassing!");
        }

        if (APIManager.checkWorldGuardOnLoad()) {
            if (ConfigHandler.WORLDGUARD_SUPPORT.getBoolean()) {
                WorldGuardHandler.loadWorldGuard();
                //getLogger().info("§2§l§o INFO §r §aSuccessfully hooked in to WorldGuard!");
            } else {
                getLogger().info("§6§l§o INFO §r §eWorldGuard Found! WorldGuard support is disabled. §ePassing!");
            }

        } else {
            getLogger().info("§6§l§o INFO §r §eCouldn't find WorldGuard! §ePassing!");
        }

        // COMMANDS

        this.getCommand("athchunkclaim").setExecutor(new ATHCCommand());
        this.getCommand("athchunkadmin").setExecutor(new ATHCACommand());

        // LISTENERS

        Bukkit.getPluginManager().registerEvents(new PlotEnterTitle(), this);

        Bukkit.getPluginManager().registerEvents(new ClaimedChunkEventsHandler(), this);
        Bukkit.getPluginManager().registerEvents(new UnClaimedChunkEventsHandler(), this);

        Bukkit.getPluginManager().registerEvents(new ChatEventsHandler(), this);

        Bukkit.getPluginManager().registerEvents(new AddOwnerChat(), this);
        Bukkit.getPluginManager().registerEvents(new AddMemberChat(), this);
        Bukkit.getPluginManager().registerEvents(new SellChunkChat(), this);

        // CONSOLE MESSAGE

        getLogger().info("");
        getLogger().info("§8§l» §b§lATHCHUNKCLAIM §7v1.0");
        getLogger().info("");
        getLogger().info("§7Plugin enabled successfully. §3(" + (System.currentTimeMillis() - start) + "ms)");
        getLogger().info("§f§oBy @Gurwi30");
        getLogger().info("");

    }

    @Override
    public void onDisable() {
        long start = System.currentTimeMillis();

        // CONSOLE MESSAGE

        getLogger().info("");
        getLogger().info("§8§l» §b§lATHCHUNKCLAIM §7v1.0");
        getLogger().info("");
        getLogger().info("§7Plugin disabled successfully. §3(" + (System.currentTimeMillis() - start) + "ms)");
        getLogger().info("§f§oBy @Gurwi30");
        getLogger().info("");
    }

    public static ATHChunkClaim getInstance() {return instance;}
    public static Economy getEconomy() {return econ;}

}
