package com.ayrou.team.Commands.Command;

import com.ayrou.team.Commands.SubCommand;
import com.ayrou.team.Main;
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