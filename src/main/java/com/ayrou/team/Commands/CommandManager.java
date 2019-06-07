package com.ayrou.team.Commands;

import com.ayrou.team.Commands.Command.Accept;
import com.ayrou.team.Commands.Command.Cancel;
import com.ayrou.team.Commands.Command.Invite;
import com.ayrou.team.Commands.Command.Menu;
import com.ayrou.team.Main;
import com.ayrou.team.Message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<>();
    private Main plugin = Main.getInstance();
    private Message message = Main.getMessage();

    private String command = "team";

    public void setup() {
        Objects.requireNonNull(plugin.getCommand(command)).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand(command)).setTabCompleter(new CommandTabManager());
        this.commands.add(new Menu());
        this.commands.add(new Invite());
        this.commands.add(new Accept());
        this.commands.add(new Cancel());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(message.getMessage("Team_Command_Invalid_Sender"));
            return true;
        }

        Player player = (Player) sender;

        if (arg1.getName().equalsIgnoreCase(command)) {
            if (args.length == 0) {
                player.sendMessage(message.getMessage("Team_Command_Help"));
                return true;
            }

            SubCommand target = this.get(args[0]);

            if (target == null) {
                player.sendMessage(message.getMessage("Team_Command_Invalid_SubCommand"));
                return true;
            }


            try {
                target.onCommand(player,args);
            }
            catch (Exception e) {
                player.sendMessage(message.getMessage("Team_Command_Error"));
                e.printStackTrace();
            }
        }
        return true;
    }

    private SubCommand get(String name) {

        for (SubCommand sc : this.commands) {
            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for (int i = 0; i < length; i++) {
                String alias = aliases[i];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }
            }
        }
        return null;
    }
}