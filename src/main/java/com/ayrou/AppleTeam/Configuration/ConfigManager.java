package com.Ayrou.AppleTeam.Configuration;

import com.Ayrou.AppleTeam.AppleTeam;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class ConfigManager {

    private AppleTeam instance;
    private static ConfigManager ConfigManagerInstance;

    public ConfigManager() {
        this.instance = AppleTeam.getInstance();
        createData();
    }

    public static ConfigManager getConfigManagerInstance() {
        if (ConfigManagerInstance == null) return new ConfigManager();
        return ConfigManagerInstance;
    }

    private void createData() {

        File languageFolder = new File(instance.getDataFolder(), "Language");
        File languageFile =  new File(languageFolder, "language.yml");
        File configFile =  new File(instance.getDataFolder(), "config.yml");
        try {

            boolean isDirectoryCreated = languageFolder.exists();
            boolean isLanguageFileCreated = languageFile.exists();
            boolean isConfigFileCreated = configFile.exists();

            if (isLanguageFileCreated || isConfigFileCreated) {
                AppleTeam.info("檔案已存在");
                return;
            }
            else {
                AppleTeam.info("檔案不存在建立檔案");
            }

            if (!isDirectoryCreated) {
                isDirectoryCreated = languageFolder.mkdirs();
            }

            if (isDirectoryCreated && !isLanguageFileCreated) {
                AppleTeam.info("建立檔案中");
                isLanguageFileCreated = languageFile.createNewFile();
                InputStream inputStream = this.getClass().getResourceAsStream("/language.yml");
                Files.copy(inputStream, Paths.get(instance.getDataFolder() + "/Language/language.yml"), StandardCopyOption.REPLACE_EXISTING);
            }

            if (!isConfigFileCreated) {
                isConfigFileCreated = configFile.createNewFile();
                InputStream inputStream = this.getClass().getResourceAsStream("/config.yml");
                Files.copy(inputStream, Paths.get(instance.getDataFolder() + "/config.yml"), StandardCopyOption.REPLACE_EXISTING);
            }

            if (isLanguageFileCreated || isConfigFileCreated) {
                AppleTeam.info("建立檔案成功");
            }
            else {
                AppleTeam.info("建立檔案失敗");
                Bukkit.getPluginManager().disablePlugins();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
