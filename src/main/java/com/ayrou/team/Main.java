package com.ayrou.team;

import com.ayrou.team.Commands.CommandManager;
import com.ayrou.team.Message.Message;
import com.ayrou.team.Team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private static Message message;
    private static TeamManager teamManager;

    @Override
    public void onEnable() {
        plugin = this;
        createData();
        message = new Message();
        teamManager = new TeamManager();
        CommandManager commandManager = new CommandManager();
        commandManager.setup();
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

    public static Main getInstance() {
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
    }
}