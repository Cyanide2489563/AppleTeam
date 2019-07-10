package pers.cyanide.appleTeam.message;

import pers.cyanide.appleTeam.AppleTeam;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Message {

    private static Message instance;
    private File file = new File(AppleTeam.getInstance().getDataFolder(),"Language/language.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    public static Message getInstance() {
        if (instance == null) instance = new Message();
        return instance;
    }

    public String getMessage(String string) {
        return config.getString(string);
    }

}