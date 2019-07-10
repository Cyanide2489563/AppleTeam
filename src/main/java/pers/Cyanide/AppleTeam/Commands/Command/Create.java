package pers.cyanide.appleTeam.commands.command;

import pers.cyanide.appleTeam.commands.SubCommand;
import pers.cyanide.appleTeam.team.TeamBuilder;
import pers.cyanide.appleTeam.team.TeamManager;
import org.bukkit.entity.Player;

public class Create extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            if (!TeamManager.getInstance().hasTeam(player.getUniqueId())) {
                if (TeamBuilder.checkTeamName(args[1])) {
                    new TeamBuilder()
                            .setName(args[1])
                            .setLeader(player.getUniqueId())
                            .create();
                    player.sendMessage("已成功建立隊伍");
                }
                else player.sendMessage("隊伍名稱不符合規範");
            }
            else player.sendMessage("你已有隊伍");
        }
        else player.sendMessage("請輸入隊伍名稱");
    }

    @Override
    public String name() {
        return "create";
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
