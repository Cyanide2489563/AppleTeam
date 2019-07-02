package com.Ayrou.AppleTeam.GUI.GUIs;

import com.Ayrou.AppleTeam.AppleTeam;
import com.Ayrou.AppleTeam.GUI.SubGui;
import com.Ayrou.AppleTeam.Team.TeamBuilder;
import com.Ayrou.AppleTeam.Team.TeamManager;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;

public class CreateTeam extends SubGui {

    private String titleName = "建立隊伍";

    @Override
    public void openInventory(Player player) {
        new AnvilGUI(AppleTeam.getInstance(), player, "隊伍名稱", (player1, reply) -> {
            if (!reply.isEmpty()) {
                if (!TeamManager.getInstance().hasTeam(player.getUniqueId())) {
                    if (TeamBuilder.checkTeamName(reply)) {
                        new TeamBuilder()
                                .setName(reply)
                                .setLeader(player.getUniqueId())
                                .create();
                        player.sendMessage("已成功建立隊伍");
                        return null;
                    } else {
                        player.sendMessage("隊伍名稱不符合規範");
                        return "Incorrect.";
                    }
                } else {
                    player.sendMessage("你已有隊伍");
                    return "Incorrect.";
                }
            }
            return "Incorrect.";
        });
    }

    @Override
    public String titleName() {
        return titleName;
    }
}