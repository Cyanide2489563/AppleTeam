package pers.cyanide.appleTeam.commands.command;

import pers.cyanide.appleTeam.commands.SubCommand;
import pers.cyanide.appleTeam.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class Invite extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            UUID target = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId();
            String status = TeamManager.getInstance().invitePlayer(player.getUniqueId(), target);
            player.sendMessage(status);
        }
        else player.sendMessage("請輸入玩家名稱");
    }

    @Override
    public String name() {
        return "invite";
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
