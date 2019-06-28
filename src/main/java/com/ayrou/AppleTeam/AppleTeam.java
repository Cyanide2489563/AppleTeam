package com.Ayrou.AppleTeam;

import com.Ayrou.AppleTeam.Commands.CommandManager;
import com.Ayrou.AppleTeam.GUI.GUIManager;
import com.Ayrou.AppleTeam.Listener.Connection;
import com.Ayrou.AppleTeam.Listener.Disconnection;
import com.Ayrou.AppleTeam.Message.Message;
import com.Ayrou.AppleTeam.Team.TeamManager;
import com.Ayrou.AppleTeam.Utility.UpdateTask;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public final class AppleTeam extends JavaPlugin {

    private static AppleTeam instance;
    private static Message message;
    private static TeamManager teamManager;
    private static GUIManager guiManager;

    public AppleTeam() {
        super();
    }

    protected AppleTeam(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        info(message.getMessage("Plugin_Initialize"));
        initialization();
        info("插件初始化完成");
    }

    @Override
    public void onDisable() {
        info(message.getMessage("Plugin_Close"));
    }

    private void initialization() {
        setInstance(this);
        createData();
        message = new Message();
        teamManager = TeamManager.getInstance();
        guiManager = new GUIManager();
        guiManager.setup();
        new CommandManager().setup();
        guiManager = new GUIManager();
        new UpdateTask(instance,teamManager);
        getServer().getPluginManager().registerEvents(new Connection(), this);
        getServer().getPluginManager().registerEvents(new Disconnection(), this);
    }

    public static AppleTeam getInstance() {
        return instance;
    }

    public static void info(String string) {
        Bukkit.getConsoleSender().sendMessage("[AppleTeam] " + string);
    }

    public static void deBug(String string) {
        Bukkit.getConsoleSender().sendMessage("[AppleTeam] §4" + string);
    }

    public static TeamManager getTeamManager() {
        return teamManager;
    }

    public static GUIManager getGuiManager() {
        return guiManager;
    }

    public static Message getMessage() {
        return message;
    }

    private void setInstance(AppleTeam instance) {
        AppleTeam.instance = instance;
    }

    private void createData() {

        File languageFolder = new File(getDataFolder(), "Language");
        File languageFile =  new File(languageFolder, "language.yml");
        File configFile =  new File(getDataFolder(), "config.yml");
        try {

            boolean isDirectoryCreated = languageFolder.exists();
            if (!isDirectoryCreated) {
                isDirectoryCreated = languageFolder.mkdirs();
            }

            boolean isLanguageFileCreated = languageFile.exists();
            if (isDirectoryCreated && !isLanguageFileCreated) {
                info("建立檔案中");
                isLanguageFileCreated = languageFile.createNewFile();
                InputStream inputStream = this.getClass().getResourceAsStream("/language.yml");
                Files.copy(inputStream, Paths.get(getDataFolder() + "/Language/language.yml"), StandardCopyOption.REPLACE_EXISTING);
            }

            boolean isConfigFileCreated = configFile.exists();
            if (!isConfigFileCreated) {
                isConfigFileCreated = configFile.createNewFile();
                InputStream inputStream = this.getClass().getResourceAsStream("/config.yml");
                Files.copy(inputStream, Paths.get(getDataFolder() + "/config.yml"), StandardCopyOption.REPLACE_EXISTING);
            }

            if (isLanguageFileCreated || isConfigFileCreated) {
                info("建立檔案成功");
            }
            else {
                info("建立檔案失敗");
                Bukkit.getPluginManager().disablePlugins();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}