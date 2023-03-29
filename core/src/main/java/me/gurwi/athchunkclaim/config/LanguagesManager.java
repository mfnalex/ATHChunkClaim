package me.gurwi.athchunkclaim.config;

import me.gurwi.athchunkclaim.ATHChunkClaim;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LanguagesManager {

    private static FileConfiguration langFile;

    public static FileConfiguration getLangFile() {return langFile;}

    public static void loadLangFile() {

        List<String> defaultLanguages = Arrays.asList("en_US", "it_IT");

        File languageFolder = new File(ATHChunkClaim.getInstance().getDataFolder(), "language");

        if (!languageFolder.exists()) {
            languageFolder.mkdir();
        }

        // DEFAULT LANGUAGES

        for (String langName : defaultLanguages) {

            File customLangFile = new File(ATHChunkClaim.getInstance().getDataFolder(), "language/" + langName + ".yml");

            if (!customLangFile.exists()) {
                customLangFile.getParentFile().mkdirs();
                ATHChunkClaim.getInstance().saveResource("language/" + langName + ".yml", false);
            }

            if (Objects.equals(langName, ConfigHandler.LANG.getString())) {

                langFile = new YamlConfiguration();
                try {
                    langFile.load(customLangFile);
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                }

            }

        }


        for (File lang : Objects.requireNonNull(languageFolder.listFiles())) {

            if (lang.getName().contains(".yml")) {
                if (lang.getName().contains("_")) {



                } else {
                    System.out.println("§4[ATHChunkClaim] §cInvalid language file §f-> §e" + lang.getName());
                }

            } else {

                System.out.println("§4[ATHChunkClaim] §cInvalid language file §f-> §e" + lang.getName());
            }

        }

    }

}
