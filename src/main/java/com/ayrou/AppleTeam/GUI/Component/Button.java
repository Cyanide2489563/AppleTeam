package com.Ayrou.AppleTeam.GUI.Component;

import com.Ayrou.AppleTeam.GUI.Component.Enums.ButtonType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class Button{

    public ItemStack createItemStack(ButtonType type) {
        ItemStack itemStack = new ItemStack(Material.STONE_HOE);
        ItemMeta itemmeta = itemStack.getItemMeta();

        Objects.requireNonNull(itemmeta).setUnbreakable(true);
        itemmeta.setDisplayName("");
        itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemmeta);
        return itemStack;
    }
}