package com.ayrou.team.GUI.Item.Type;

import com.ayrou.team.GUI.Item.Enums.ButtonType;
import com.ayrou.team.GUI.Item.Enums.ItemType;
import org.bukkit.inventory.ItemStack;

interface Item {

    ItemType itemType = null;

    ItemStack createItemStack(ButtonType type);
}
