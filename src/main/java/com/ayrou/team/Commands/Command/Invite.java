package com.ayrou.team.Commands.Command;

import com.ayrou.team.Commands.SubCommand;

import com.ayrou.team.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class Invite extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            UUID target = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId();
            String status = Main.getTeamManager().invitePlayer(player.getUniqueId(), target);
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
