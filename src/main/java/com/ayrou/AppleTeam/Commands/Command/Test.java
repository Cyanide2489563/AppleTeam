package com.Ayrou.AppleTeam.Commands.Command;

import com.Ayrou.AppleTeam.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class Test extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Inventory test = Bukkit.createInventory(null, InventoryType.DISPENSER, "Titel");


    }

    @Override
    public String name() {
        return "Test";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
