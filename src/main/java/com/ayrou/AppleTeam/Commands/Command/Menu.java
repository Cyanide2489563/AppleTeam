package com.Ayrou.AppleTeam.Commands.Command;

import com.Ayrou.AppleTeam.AppleTeam;
import com.Ayrou.AppleTeam.Commands.SubCommand;
import com.Ayrou.AppleTeam.GUI.GUIManager;
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
