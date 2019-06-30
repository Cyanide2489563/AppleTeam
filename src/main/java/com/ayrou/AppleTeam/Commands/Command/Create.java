package com.Ayrou.AppleTeam.Commands.Command;

import com.Ayrou.AppleTeam.Commands.SubCommand;
import com.Ayrou.AppleTeam.Team.TeamBuilder;
import com.Ayrou.AppleTeam.Team.TeamManager;
import org.bukkit.entity.Player;

public class Create extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            if (!TeamManager.getInstance().hasTeam(player.getUniqueId())) {

                new TeamBuilder()
                        .setName(args[1])
                        .setLeader(player.getUniqueId())
                        .create();
            }
            else player.sendMessage("你已有隊伍");
        }
        else player.sendMessage("請輸入隊伍名稱");
    }

    @Override
    public String name() {
        return "create";
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
