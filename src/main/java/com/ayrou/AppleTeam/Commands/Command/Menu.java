package com.Ayrou.AppleTeam.Commands.Command;

import com.Ayrou.AppleTeam.Commands.SubCommand;
import com.Ayrou.AppleTeam.GUI.GUIs.TeamMenu;
import org.bukkit.entity.Player;

public class Menu extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        TeamMenu teamMenu = new TeamMenu();
        teamMenu.openTeamMenu(player);
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
