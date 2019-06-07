package com.ayrou.team.Commands.Command;

import com.ayrou.team.Commands.SubCommand;
import com.ayrou.team.Main;
import org.bukkit.entity.Player;

public class Accept extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 1) return;
        String status = Main.getTeamManager().acceptJoin(args[1], player.getUniqueId());

        player.sendMessage(status);
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
