package com.Ayrou.AppleTeam.GUI.GUIs;

import com.Ayrou.AppleTeam.AppleTeam;
import com.Ayrou.AppleTeam.GUI.SubGui;
import com.github.stefvanschie.inventoryframework.Gui;
import org.bukkit.entity.Player;

public class Team_Setup extends SubGui {

    private String titleName = "隊伍設定";
    private Gui gui = new Gui(AppleTeam.getInstance(), 6, titleName);

    @Override
    public void openInventory(Player player) {
        gui.show(player);
    }

    @Override
    public String titleName() {
        return titleName;
    }
}
