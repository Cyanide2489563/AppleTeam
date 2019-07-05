package com.Ayrou.AppleTeam.GUI;

import com.Ayrou.AppleTeam.GUI.GUIs.CreateTeam;
import com.Ayrou.AppleTeam.GUI.GUIs.TeamMenu;
import com.Ayrou.AppleTeam.GUI.GUIs.Team_Setup;

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