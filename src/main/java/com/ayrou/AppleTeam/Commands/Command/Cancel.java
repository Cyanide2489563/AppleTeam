package com.Ayrou.AppleTeam.Commands.Command;

import com.Ayrou.AppleTeam.Commands.SubCommand;
import com.Ayrou.AppleTeam.Team.TeamManager;
import org.bukkit.entity.Player;

public class Cancel extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            String status = TeamManager.getInstance().cancelJoin(args[1], player.getUniqueId());

            player.sendMessage(status);
        }
        else player.sendMessage("錯誤");
    }

    @Override
    public String name() {
        return "cancel";
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
