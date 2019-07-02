package com.Ayrou.AppleTeam;

import com.Ayrou.AppleTeam.API.AppleTeamAPI;
import com.Ayrou.AppleTeam.Commands.CommandManager;
import com.Ayrou.AppleTeam.Configuration.ConfigManager;
import com.Ayrou.AppleTeam.GUI.GUIManager;
import com.Ayrou.AppleTeam.Listener.Connection;
import com.Ayrou.AppleTeam.Listener.Disconnection;
import com.Ayrou.AppleTeam.Message.Message;
import com.Ayrou.AppleTeam.Utility.UpdateTask;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public final class AppleTeam extends JavaPlugin {

    private static AppleTeam instance;
    private static Message message;
    private static AppleTeamAPI API;
    private static GUIManager guiManager;

    public AppleTeam() {
        super();
    }

    protected AppleTeam(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onLoad() {
        setInstance(this);
        API = new AppleTeamAPI(instance);
        message = new Message();
        ConfigManager.getConfigManagerInstance();
    }

    @Override
    public void onEnable() {
        initialization();
    }

    @Override
    public void onDisable() {
        info(message.getMessage("Plugin_Close"));
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getOpenInventory().close();
        }
    }

    private void initialization() {
        info(message.getMessage("Plugin_Initialize"));
        guiManager = new GUIManager();
        guiManager.setup();
        new CommandManager().setup();
        guiManager = new GUIManager();
        new UpdateTask(instance);
        getServer().getPluginManager().registerEvents(new Connection(), this);
        getServer().getPluginManager().registerEvents(new Disconnection(), this);
        info("插件初始化完成");
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

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public Message getMessage() {
        return message;
    }

    private void setInstance(AppleTeam instance) {
        AppleTeam.instance = instance;
    }

}