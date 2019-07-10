package pers.cyanide.appleTeam.commands.command;

import pers.cyanide.appleTeam.commands.SubCommand;
import pers.cyanide.appleTeam.team.TeamManager;
import org.bukkit.entity.Player;

public class Disband extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage(TeamManager.getInstance().disbandTeam(player.getUniqueId()));
    }

    @Override
    public String name() {
        return "disband";
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