package pers.cyanide.appleTeam.gui;

import pers.cyanide.appleTeam.gui.GUIs.CreateTeam;
import pers.cyanide.appleTeam.gui.GUIs.TeamMenu;
import pers.cyanide.appleTeam.gui.GUIs.Team_Setup;

import java.util.ArrayList;

public class GUIManager {
    private ArrayList<SubGui> guiList = new ArrayList<>();

    public void setup() {
        this.guiList.add(new TeamMenu());
        this.guiList.add(new CreateTeam());
        this.guiList.add(new Team_Setup());
    }

    public void addGui(SubGui subGui) {
        this.guiList.add(subGui);
    }

    public SubGui get(String titleName) {
        for (SubGui subGui : this.guiList) {
            if (subGui.titleName().equalsIgnoreCase(titleName)) return subGui;
        }
        return null;
    }

}