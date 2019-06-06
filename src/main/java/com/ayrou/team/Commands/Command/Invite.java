package com.ayrou.team.Commands.Command;

import com.ayrou.team.Commands.SubCommand;

import com.ayrou.team.Main;
import com.ayrou.team.Team.TeamManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Invite extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        TeamManager teamManager = Main.getTeamManager();
        teamManager.invitePlayer(player.getUniqueId(), UUID.fromString(args[2]));
    }

    @Override
    public String name() {
        return "invite";
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
