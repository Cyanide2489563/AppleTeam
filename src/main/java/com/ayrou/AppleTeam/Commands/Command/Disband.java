package com.Ayrou.AppleTeam.Commands.Command;

import com.Ayrou.AppleTeam.Commands.SubCommand;
import com.Ayrou.AppleTeam.Main;
import org.bukkit.entity.Player;

public class Disband extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage(Main.getTeamManager().disbandTeam(player.getUniqueId()));
    }

    @Override
    public String name() {
        return "disband";
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