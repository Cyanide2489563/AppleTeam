package com.ayrou.team.GUI;

import com.ayrou.team.GUI.Item.Enums.ButtonType;
import com.ayrou.team.GUI.Item.Type.Button;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuView implements InventoryProvider {

    MenuView(Player player) {

    }

    public static SmartInventory getInventory(Player player) {
        return SmartInventory.builder()
                .provider(new MenuView(player))
                .size(3, 9)
                .title("Inventory of " + player.getName())
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(new Button().createItemStack(ButtonType.BLANK)));

        contents.set(1, 1, ClickableItem.of(new ItemStack(Material.STONE_HOE),
                e -> {

                }));

        contents.set(1, 7, ClickableItem.of(new ItemStack(Material.BARRIER),
                e -> player.closeInventory()));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}