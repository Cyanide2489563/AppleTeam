package com.ayrou.team.GUI;

import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;

public class Menu implements InventoryProvider {

    Menu(Player player) {

    }

    public static SmartInventory getInventory(Player player) {
        return SmartInventory.builder()
                .provider(new Menu(player))
                .size(3, 9)
                .title("Inventory of " + player.getName())
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}