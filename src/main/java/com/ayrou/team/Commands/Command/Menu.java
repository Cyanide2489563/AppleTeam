package com.ayrou.team.Commands.Command;

import com.ayrou.team.Commands.SubCommand;
import com.ayrou.team.GUI.MenuView;
import org.bukkit.entity.Player;

public class Menu extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        MenuView.getInventory(player).open(player);
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
