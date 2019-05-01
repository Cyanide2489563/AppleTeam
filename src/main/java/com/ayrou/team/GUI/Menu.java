package com.ayrou.team.GUI;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Menu implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("myInventory")
            .provider(new Menu())
            .size(3, 9)
            .title(ChatColor.BLUE + "測試用")
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(new ItemStack(Material.STONE_HOE)));

        contents.set(1, 1, ClickableItem.of(new ItemStack(Material.DIAMOND),
                e -> player.sendMessage(ChatColor.GOLD + "You clicked on a potato.")));

        contents.set(1, 7, ClickableItem.of(new ItemStack(Material.BARRIER),
                e -> player.closeInventory()));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
