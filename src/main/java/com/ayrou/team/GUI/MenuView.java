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

    private MenuView(Player player) {

    }

    public static SmartInventory getInventory(Player player) {
        return SmartInventory.builder()
                .provider(new MenuView(player))
                .size(6, 9)
                .title("隊伍面板")
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fill(ClickableItem.empty(new Button().createItemStack(ButtonType.BLANK)));
        contents.set(1, 8, ClickableItem.of(new Button().createItemStack(ButtonType.Prev_Page),
                e -> {

                }));
        contents.set(3, 8, ClickableItem.of(new Button().createItemStack(ButtonType.Next_Page),
                e -> {

                }));
        contents.set(0, 1, ClickableItem.of(new Button().createItemStack(ButtonType.All),
                e -> {
                    if (e.isRightClick()) {
                        contents.set(1, 1,ClickableItem.empty(new Button().createItemStack(ButtonType.Public)));
                    }
                }));
        contents.set(0, 2, ClickableItem.of(new Button().createItemStack(ButtonType.Search),
                e -> player.closeInventory()));
        contents.set(0, 7, ClickableItem.of(new ItemStack(Material.DIAMOND_HOE),
                e -> player.closeInventory()));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}