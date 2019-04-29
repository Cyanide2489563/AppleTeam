package com.ayrou.team;

import com.ayrou.team.Message.Message;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private static Message message;

    @Override
    public void onEnable() {
        plugin = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void info(String string) {
        Bukkit.getConsoleSender().sendMessage("[AppleGuild] " + string);
    }

    public static void deBug(String string) {
        Bukkit.getConsoleSender().sendMessage("[AppleGuild] §4" + string);
    }

    public static Main getInstance() {
        return plugin;
    }

    public static Message getMessage() {
        return message;
    }
}