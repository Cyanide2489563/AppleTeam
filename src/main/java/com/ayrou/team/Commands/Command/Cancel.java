package com.ayrou.team.Commands.Command;

import com.ayrou.team.Commands.SubCommand;
import com.ayrou.team.Main;
import org.bukkit.entity.Player;

public class Cancel extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            String status = Main.getTeamManager().cancelJoin(args[1], player.getUniqueId());

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
