package pers.cyanide.appleTeam;

import pers.cyanide.appleTeam.api.AppleTeamAPI;
import pers.cyanide.appleTeam.commands.CommandManager;
import pers.cyanide.appleTeam.configuration.ConfigManager;
import pers.cyanide.appleTeam.gui.GUIManager;
import pers.cyanide.appleTeam.listener.Connection;
import pers.cyanide.appleTeam.listener.Disconnection;
import pers.cyanide.appleTeam.message.Message;
import pers.cyanide.appleTeam.utility.UpdateTask;

import java.io.File;

import me.clip.placeholderapi.PlaceholderAPI;
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
    private static PlaceholderAPI placeholderAPI;

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
        new UpdateTask(instance);
        getServer().getPluginManager().registerEvents(new Connection(), this);
        getServer().getPluginManager().registerEvents(new Disconnection(), this);
        info("插件初始化完成");
    }

    private void test() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

        }
        else {
            throw new RuntimeException("本插件需要依賴 PlaceholderAPI");
        }
    }

    private void setInstance(AppleTeam instance) {
        AppleTeam.instance = instance;
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

}