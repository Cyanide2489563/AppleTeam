package com.Ayrou.AppleTeam.Commands;

import com.Ayrou.AppleTeam.Team.Team;
import com.Ayrou.AppleTeam.Team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTabManager implements TabCompleter {

    private static final List<String> COMMANDS = Arrays.asList("menu", "invite", "leave", "kick", "disband");
    private static final List<String> BLANK = Arrays.asList("", "");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            Team team = TeamManager.getInstance().getTeam(player);
            if (team != null) {
                if (args[0].equalsIgnoreCase("invite")) {
                    if (args.length > 2) return BLANK;
                    List<String> PLAYERLIST = new ArrayList<>();
                    for(Player player1 : Bukkit.getServer().getOnlinePlayers()) {
                        PLAYERLIST.add(player1.getName());
                    }
                    return StringUtil.copyPartialMatches(args[0], PLAYERLIST, PLAYERLIST);
                }
                if (args[0].equalsIgnoreCase("kick")) {
                    if (args.length > 2) return BLANK;
                    return team.getTeamMemberName();
                }
                if (args.length >= 2) return BLANK;
                return StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>());
            }
            else {
                if (args.length >= 2) return BLANK;
                List<String> COMMAND = new ArrayList<>();
                COMMAND.add("create");
                COMMAND.add("menu");
                return StringUtil.copyPartialMatches(args[0], COMMAND, new ArrayList<>());
            }
        }
        return BLANK;
    }
}