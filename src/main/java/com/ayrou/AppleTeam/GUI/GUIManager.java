package com.Ayrou.AppleTeam.GUI;

import com.Ayrou.AppleTeam.GUI.GUIs.TeamMenu;

import java.util.ArrayList;

public class GUIManager {
    private ArrayList<SubGui> guis = new ArrayList<>();

    public void setup() {
        this.guis.add(new TeamMenu());
    }
}