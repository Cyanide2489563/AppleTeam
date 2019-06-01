package com.ayrou.team.GUI.Item.Type;

import com.ayrou.team.GUI.Item.Enums.ButtonType;
import com.ayrou.team.GUI.Item.Enums.ItemType;
import fr.minuskube.inv.ClickableItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.function.Consumer;

public class Button {

    ItemType itemType = ItemType.BUTTON;


    public static ItemStack createItemStack(ButtonType type) {

        ItemStack itemStack = new ItemStack(Material.STONE_HOE,1);
        ItemMeta itemMeta = itemStack.getItemMeta();

        Objects.requireNonNull(itemMeta).setUnbreakable(true);
        ((Damageable)itemMeta).setDamage(type.getValue());
        itemMeta.setDisplayName(ChatColor.RESET + type.getName());
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack item;
    private Consumer<InventoryClickEvent> consumer;

    private Button(ItemStack item, Consumer<InventoryClickEvent> consumer) {
        this.item = item;
        this.consumer = consumer;
    }

    public static Button empty(ButtonType item) {
        return of(createItemStack(item), e -> {});
    }

    public static Button of(ItemStack item, Consumer<InventoryClickEvent> consumer) {
        return new Button(item, consumer);
    }

    public void run(InventoryClickEvent e) {
        consumer.accept(e);
    }

    public ItemStack getItem() {
        return item;
    }
}
