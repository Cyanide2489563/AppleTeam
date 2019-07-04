package com.Ayrou.AppleTeam.GUI.Component;

import com.Ayrou.AppleTeam.GUI.Component.Enums.ButtonType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Button {

    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private ArrayList<String> lore = new ArrayList<>();

    public Button(@NotNull ButtonType type) {
        itemStack = new ItemStack(Material.STONE_HOE);
        itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setUnbreakable(true);
        ((Damageable) itemMeta).setDamage(type.getValue());
        itemMeta.setDisplayName(ChatColor.RESET + type.getName());
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
    }

    public Button setLore(@NotNull String lore) {
        this.lore.add(lore);
        itemMeta.setLore(this.lore);
        return this;
    }

    public ItemStack create() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}