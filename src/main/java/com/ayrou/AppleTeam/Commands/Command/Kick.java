package com.Ayrou.AppleTeam.Commands.Command;

import com.Ayrou.AppleTeam.Commands.SubCommand;
import com.Ayrou.AppleTeam.Team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Kick extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            player.sendMessage(
                TeamManager.getInstance().kickMember(player.getUniqueId(), Bukkit.getPlayer(args[1]).getUniqueId())
            );
        }
        else player.sendMessage("請輸入該名玩家名稱");
    }

    @Override
    public String name() {
        return "kick";
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
