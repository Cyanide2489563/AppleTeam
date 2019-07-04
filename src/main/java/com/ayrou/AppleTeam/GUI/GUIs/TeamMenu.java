package com.Ayrou.AppleTeam.GUI.GUIs;

import com.Ayrou.AppleTeam.GUI.Component.Button;
import com.Ayrou.AppleTeam.GUI.Component.Enums.ButtonType;
import com.Ayrou.AppleTeam.GUI.GUIManager;
import com.Ayrou.AppleTeam.GUI.SubGui;
import com.Ayrou.AppleTeam.AppleTeam;
import com.Ayrou.AppleTeam.Team.Team;
import com.Ayrou.AppleTeam.Team.TeamManager;
import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.ToggleButton;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamMenu extends SubGui {

    private String titleName = "隊伍列表";
    private GUIManager guiManager = AppleTeam.getInstance().getGuiManager();
    private Gui gui = new Gui(AppleTeam.getInstance(), 6, titleName);
    private ArrayList<GuiItem> teamItem = new ArrayList<>();
    private StaticPane top_Pane = new StaticPane(0, 0, 9, 1, Pane.Priority.NORMAL);

    private GuiItem create_Team = new GuiItem(new Button(ButtonType.CREATE_TEAM)
            .setLore(ChatColor.RESET + "測試")
            .create(), event -> {
        if (event.isLeftClick()) {
            guiManager.get("建立隊伍").openInventory((Player) event.getWhoClicked());
        }
        event.setCancelled(true);
    });

    private GuiItem setup_Team = new GuiItem(new Button(ButtonType.CREATE_TEAM)
            .setLore(ChatColor.RESET + "測試")
            .create(), event -> {
        if (event.isLeftClick()) {
            guiManager.get("建立隊伍").openInventory((Player) event.getWhoClicked());
        }
        event.setCancelled(true);
    });

    private void test() {
       HashMap<String, Team> teams = TeamManager.getInstance().getTeams();

    }

    public TeamMenu() {
        top_Pane.addItem(create_Team,0,0);

        gui.addPane(top_Pane);
    }

    @Override
    public void openInventory(Player player) {
        gui.show(player);
        ToggleButton toggleButton = new ToggleButton(0, 0, 9, 6);

    }

    @Override
    public String titleName() {
        return titleName;
    }

    GuiItem item;
}