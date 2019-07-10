package pers.cyanide.appleTeam.commands.command;

import pers.cyanide.appleTeam.commands.SubCommand;
import pers.cyanide.appleTeam.team.Team;
import pers.cyanide.appleTeam.team.TeamManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class Leave extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Team team = TeamManager.getInstance().getTeam(player);

        if (team != null) player.sendMessage(team.leave(player.getUniqueId()));
        else player.sendMessage(ChatColor.GREEN + "你沒有隊伍");
    }

    @Override
    public String name() {
        return "leave";
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