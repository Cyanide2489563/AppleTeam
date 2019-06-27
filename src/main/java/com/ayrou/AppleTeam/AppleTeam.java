package com.Ayrou.AppleTeam;

import com.Ayrou.AppleTeam.Commands.CommandManager;
import com.Ayrou.AppleTeam.Listener.Connection;
import com.Ayrou.AppleTeam.Listener.Disconnection;
import com.Ayrou.AppleTeam.Message.Message;
import com.Ayrou.AppleTeam.Team.TeamManager;
import com.Ayrou.AppleTeam.Utility.UpdateTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class AppleTeam extends JavaPlugin {

    private static AppleTeam plugin;
    private static Message message;
    private static TeamManager teamManager;

    public AppleTeam() {
        super();
    }

    protected AppleTeam(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        plugin = this;
        message = new Message();
        teamManager = TeamManager.getInstance();
        createData();
        new CommandManager().setup();
        new UpdateTask(plugin,teamManager);
        getServer().getPluginManager().registerEvents(new Connection(), this);
        getServer().getPluginManager().registerEvents(new Disconnection(), this);
        info(message.getMessage("Plugin_Initialize"));
    }

    @Override
    public void onDisable() {
        info(message.getMessage("Plugin_Close"));
    }

    public static void info(String string) {
        Bukkit.getConsoleSender().sendMessage("[AppleTeam] " + string);
    }

    public static void deBug(String string) {
        Bukkit.getConsoleSender().sendMessage("[AppleTeam] §4" + string);
    }

    public static AppleTeam getInstance() {
        return plugin;
    }

    public static TeamManager getTeamManager() {
        return teamManager;
    }

    public static Message getMessage() {
        return message;
    }

    private void createData() {
        File languageFolder =  new File(getDataFolder(), "Language");
        if(!languageFolder.exists())
            languageFolder.mkdirs();

        File languageFile =  new File(languageFolder, "language.yml");
        if (!languageFile.exists()) {
            try {
                languageFile.createNewFile();
                InputStream inputStream = this.getClass().getResourceAsStream("/language.yml");
                Files.copy(inputStream, Paths.get(getDataFolder() + "/Language/language.yml"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File configFile =  new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                InputStream inputStream = this.getClass().getResourceAsStream("/config.yml");
                Files.copy(inputStream, Paths.get(getDataFolder() + "/config.yml"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}