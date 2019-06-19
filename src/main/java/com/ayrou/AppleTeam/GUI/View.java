package com.Ayrou.AppleTeam.GUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class View {

    public void test(Player player, String title) {
        Inventory inventory = new GUIManager.Builder()
                .setOwner(player)
                .setSize(WindowType.MAXWINDOWS)
                .setTitle(title)
                .creater();
        
    }
}