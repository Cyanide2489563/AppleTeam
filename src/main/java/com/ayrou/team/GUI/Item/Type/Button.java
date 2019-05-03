package com.ayrou.team.GUI.Item.Type;

import com.ayrou.team.GUI.Item.Enums.ButtonType;
import com.ayrou.team.GUI.Item.Enums.ItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class Button implements Item {

    ItemType itemType = ItemType.BUTTON;

    @Override
    public ItemStack createItemStack(ButtonType type) {

        ItemStack itemStack = new ItemStack(Material.STONE_HOE);
        itemStack.setAmount(type.getValue());
        ItemMeta itemmeta = itemStack.getItemMeta();

        Objects.requireNonNull(itemmeta).setUnbreakable(true);
        itemmeta.setDisplayName("");
        itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemmeta);
        return itemStack;
    }
}
