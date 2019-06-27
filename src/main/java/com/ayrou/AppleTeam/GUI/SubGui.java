package com.Ayrou.AppleTeam.GUI;

import org.bukkit.entity.Player;

public abstract class SubGui {
    private String titleName;

    public abstract void openInventory(Player player);
    public abstract String titleName();
}