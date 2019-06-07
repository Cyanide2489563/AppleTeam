package com.ayrou.team.Commands.Command;

import com.ayrou.team.Commands.SubCommand;

import com.ayrou.team.Main;
import com.ayrou.team.Team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class Invite extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {

        TeamManager teamManager = Main.getTeamManager();
        UUID target = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId();
        String status = teamManager.invitePlayer(player.getUniqueId(), target);
        player.sendMessage(status);
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
