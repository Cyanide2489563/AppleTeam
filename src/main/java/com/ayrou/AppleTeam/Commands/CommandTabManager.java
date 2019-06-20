package com.Ayrou.AppleTeam.Commands;

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

    private static final List<String> COMMANDS = Arrays.asList("menu", "create", "invite");
    private static final List<String> BLANK = Arrays.asList("", "");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("kick")) {
            if(args.length > 2) {
                return BLANK;
            }
            List<String> PLAYERLIST = new ArrayList<>();
            for(Player player : Bukkit.getServer().getOnlinePlayers()){
                PLAYERLIST.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[0], PLAYERLIST, PLAYERLIST);
        }
        if (args.length >= 2) return BLANK;
        return (args.length > 0) ? StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>()) : null;
    }
}