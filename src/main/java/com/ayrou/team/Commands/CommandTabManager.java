package com.ayrou.team.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class CommandTabManager implements TabCompleter {

    private static final List<String> COMMANDS = Arrays.asList("menu");
    private static final List<String> BLANK = Arrays.asList("", "");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
