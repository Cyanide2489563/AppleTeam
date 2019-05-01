package com.ayrou.team;

import com.ayrou.team.Commands.CommandManager;
import com.ayrou.team.Message.Message;
import com.ayrou.team.Team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private static Message message;
    private static TeamManager teamManager;

    @Override
    public void onEnable() {
        plugin = this;
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
}