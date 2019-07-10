package pers.cyanide.appleTeam.commands;

import pers.cyanide.appleTeam.AppleTeam;
import pers.cyanide.appleTeam.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pers.cyanide.appleTeam.commands.command.*;

import java.util.ArrayList;
import java.util.Objects;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<>();
    private AppleTeam plugin = AppleTeam.getInstance();
    private Message message = Message.getInstance();
    private String command = "team";

    public void setup() {
        Objects.requireNonNull(plugin.getCommand(command)).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand(command)).setTabCompleter(new CommandTabManager());
        this.commands.add(new Menu());
        this.commands.add(new Create());
        this.commands.add(new Invite());
        this.commands.add(new Accept());
        this.commands.add(new Cancel());
        this.commands.add(new Disband());
        this.commands.add(new Leave());
        this.commands.add(new Kick());

        this.commands.add(new Test());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

        if (arg1.getName().equalsIgnoreCase(command)) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(message.getMessage("Team_Command_Invalid_Sender"));
                return true;
            }

            Player player = (Player) sender;

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
            if (sc.name().equalsIgnoreCase(name)) return sc;

            String[] aliases = sc.aliases();
            for (int i = 0, length = sc.aliases().length; i < length; i++) {
                if (name.equalsIgnoreCase(aliases[i])) return sc;
            }
        }
        return null;
    }
}