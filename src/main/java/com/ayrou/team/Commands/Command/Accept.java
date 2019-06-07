package com.ayrou.team.Commands.Command;

import com.ayrou.team.Commands.SubCommand;
import com.ayrou.team.Main;
import com.ayrou.team.Team.TeamManager;
import org.bukkit.entity.Player;

public class Accept extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        TeamManager teamManager  = Main.getTeamManager();
        teamManager.acceptJoin(args[1], player.getUniqueId());
    }

    @Override
    public String name() {
        return "accept";
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
