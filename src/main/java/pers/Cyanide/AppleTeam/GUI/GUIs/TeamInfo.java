package pers.cyanide.appleTeam.gui.GUIs;

import pers.cyanide.appleTeam.AppleTeam;
import pers.cyanide.appleTeam.gui.SubGui;
import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TeamInfo extends SubGui {
    private String titleName = "隊伍資訊";
    private Gui gui = new Gui(AppleTeam.getInstance(), 6, titleName);
    private ArrayList<GuiItem> item = new ArrayList<>();

    public TeamInfo(String titleName) {
        this.titleName = titleName;
    }

    @Override
    public void openInventory(Player player) {
        gui.show(player);
    }

    @Override
    public String titleName() {
        return titleName;
    }
}
