package pers.cyanide.appleTeam.commands.command;

import pers.cyanide.appleTeam.AppleTeam;
import pers.cyanide.appleTeam.commands.SubCommand;
import pers.cyanide.appleTeam.gui.GUIManager;
import org.bukkit.entity.Player;

public class Menu extends SubCommand {

    private GUIManager guiManager = AppleTeam.getInstance().getGuiManager();

    @Override
    public void onCommand(Player player, String[] args) {
        guiManager.get("隊伍列表").openInventory(player);
    }

    @Override
    public String name() {
        return "menu";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
