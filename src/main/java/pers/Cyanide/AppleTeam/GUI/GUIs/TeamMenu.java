package pers.cyanide.appleTeam.gui.GUIs;

import pers.Cyanide.AppleTeam.GUI.Component.Button;
import pers.Cyanide.AppleTeam.GUI.Component.Enums.ButtonType;
import pers.Cyanide.AppleTeam.GUI.Component.SkullCreator;
import pers.cyanide.appleTeam.gui.GUIManager;
import pers.cyanide.appleTeam.gui.SubGui;
import pers.cyanide.appleTeam.AppleTeam;
import pers.cyanide.appleTeam.team.Team;
import pers.cyanide.appleTeam.team.TeamManager;
import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
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
    private PaginatedPane teamListPane = new PaginatedPane(0, 1, 9, 4);
    private StaticPane pane = new StaticPane(0, 1, 9, 4, Pane.Priority.LOW);

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
            .create(),
            event -> {
        if (event.isLeftClick()) {
            guiManager.get("建立隊伍").openInventory((Player) event.getWhoClicked());
        }
        event.setCancelled(true);
    });

    private GuiItem previous = new GuiItem(new Button(ButtonType.CREATE_TEAM)
            .setLore(ChatColor.RESET + "測試")
            .create(),
            event -> {
                if (event.isLeftClick()) {
                    guiManager.get("建立隊伍").openInventory((Player) event.getWhoClicked());
                }
                event.setCancelled(true);
            });

    private ArrayList<GuiItem> createTeamItem() {
        HashMap<String, Team> teams = TeamManager.getInstance().getTeams();
        if (teamItem.size() == teams.size()) return teamItem;
        ArrayList<GuiItem> item = new ArrayList<>();
        if (!teams.isEmpty()) {
            for (Map.Entry<String, Team> team : teams.entrySet()) {
                String reset = ChatColor.RESET.toString();
                String teamName = team.getKey();
                String leaderName = Bukkit.getPlayer(team.getValue().getLeader()).getName();

                GuiItem guiItem = new GuiItem(new Button(SkullCreator.itemFromUuid(team.getValue().getLeader()))
                        .setName(reset + "隊伍名稱:" + ChatColor.GOLD + teamName)
                        .setLore(reset + "隊長:" + ChatColor.YELLOW + leaderName)
                        .setLore(reset + "隊伍人數:" + team.getValue().getMemberSize() + "/5")
                        .create(),
                        event -> {
                    if (event.isLeftClick()) {
                        guiManager.addGui(new TeamInfo(teamName));
                        guiManager.get(teamName).openInventory((Player) event.getWhoClicked());
                    }
                    event.setCancelled(true);
                });
                item.add(guiItem);
                teamItem.add(guiItem);
            }
        }
        return item;
    }

    private void test() {
        ArrayList<GuiItem> item = createTeamItem();
        if (item.isEmpty()) return;
        int x = 1;
        int y = 1;
        for (int i = 0; i < item.size(); i++) {
            pane.addItem(item.get(i), x - 1, y - 1);
            x += 1;

            if (x > 9) {
                x = 1;
                y += 1;
            }
        }
    }

    public TeamMenu() {
        top_Pane.addItem(create_Team,0,0);
        test();
        gui.addPane(top_Pane);
        gui.addPane(pane);
    }

    @Override
    public void openInventory(Player player) {
        test();
        gui.addPane(pane);
        gui.show(player);
    }

    @Override
    public String titleName() {
        return titleName;
    }

    GuiItem item;
}