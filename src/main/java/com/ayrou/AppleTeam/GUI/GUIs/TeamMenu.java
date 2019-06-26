package com.Ayrou.AppleTeam.GUI.GUIs;

import com.Ayrou.AppleTeam.GUI.Component.Button;
import com.Ayrou.AppleTeam.GUI.Component.Enums.ButtonType;
import com.Ayrou.AppleTeam.GUI.SubGui;
import com.Ayrou.AppleTeam.Main;
import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.entity.Player;

public class TeamMenu extends SubGui {

    private String titleName = "隊伍列表";
    private Gui gui = new Gui(Main.getInstance(), 6, titleName);
    private StaticPane top_Pane = new StaticPane(0, 1, 9, 4, Pane.Priority.NORMAL);

    GuiItem create_Team = new GuiItem(Button.getItemStack(ButtonType.CREATE_TEAM), event -> {
        if (event.isLeftClick()) {
            event.getWhoClicked().sendMessage("建立隊伍");
        }
        event.setCancelled(true);
    });

    public void openTeamMenu(Player player) {
        top_Pane.addItem(create_Team,0,0);
        gui.addPane(top_Pane);
        gui.show(player);
    }

    @Override
    public String titleName() {
        return titleName;
    }
}