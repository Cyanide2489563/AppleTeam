package com.Ayrou.AppleTeam.Commands.Command;

import com.Ayrou.AppleTeam.Commands.SubCommand;
import com.Ayrou.AppleTeam.Main;
import org.bukkit.entity.Player;

public class Accept extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            String status = Main.getTeamManager().acceptJoin(args[1], player.getUniqueId());
            player.sendMessage(status);
        }
        else player.sendMessage("錯誤");
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
