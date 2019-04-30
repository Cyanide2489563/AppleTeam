package com.ayrou.team.Message;

import com.ayrou.team.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Message {

    private Main plugin = Main.getInstance();
    private File file = new File(plugin.getDataFolder(),"Language/language.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    public String getMessage(String string) {
        return config.getString(string);
    }
}
