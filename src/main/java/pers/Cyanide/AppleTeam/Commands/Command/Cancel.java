package pers.cyanide.appleTeam.commands.command;

import pers.cyanide.appleTeam.commands.SubCommand;
import pers.cyanide.appleTeam.team.TeamManager;
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
