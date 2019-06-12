package com.ayrou.team.Commands.Command;

import com.ayrou.team.Commands.SubCommand;
import com.ayrou.team.Main;
import com.ayrou.team.Team.Team;
import org.bukkit.entity.Player;

public class Disband extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Team team = Main.getTeamManager().getTeam(player.getUniqueId());
        if (team != null) {

        }
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
