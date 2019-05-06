package com.ayrou.team.GUI.Item.Type;

import com.ayrou.team.GUI.Item.Enums.ButtonType;
import com.ayrou.team.GUI.Item.Enums.ItemType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class Button implements Item {

    ItemType itemType = ItemType.BUTTON;

    @Override
    public ItemStack createItemStack(ButtonType type) {

        ItemStack itemStack = new ItemStack(Material.STONE_HOE,1);
        ItemMeta itemMeta = itemStack.getItemMeta();

        Objects.requireNonNull(itemMeta).setUnbreakable(true);
        ((Damageable)itemMeta).setDamage(type.getValue());
        itemMeta.setDisplayName(ChatColor.RESET + type.getName());
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
