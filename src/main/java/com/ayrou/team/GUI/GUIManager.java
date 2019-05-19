package com.ayrou.team.GUI;

import com.ayrou.team.GUI.Item.Enums.ButtonType;
import com.ayrou.team.GUI.Item.Type.Button;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GUIManager implements InventoryProvider {

    private static SmartInventory INVENTORY = null;

    GUIManager(UUID uuid, String title) {
        INVENTORY = SmartInventory.builder()
                .id(uuid.toString())
                .provider(this)
                .size(3, 9)
                .title(ChatColor.BLUE + title)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(0, 5, ClickableItem.empty(new Button().createItemStack(ButtonType.GUI_BACKGROUND_UP)));
        contents.set(7, 5, ClickableItem.empty(new Button().createItemStack(ButtonType.GUI_BACKGROUND_DOWN)));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}