package com.Ayrou.AppleTeam.GUI.GUIs;

import com.Ayrou.AppleTeam.GUI.Component.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CreateTeam {
    public String createTeam(Player player) {
        String name = "";
        AnvilGUI GUI = new AnvilGUI(player, e -> {
            if(e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT & e.hasText()) {
                e.setWillClose(true);
                player.sendMessage("你的隊伍名稱是" + e.getText());
            }
        });

        ItemStack i = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = i.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "輸入中英文大於3小於10");
        itemMeta.setLore(lore);
        i.setItemMeta(itemMeta);

        GUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, i);
        GUI.setSlotName(AnvilGUI.AnvilSlot.INPUT_LEFT,  "§r請輸入隊伍名稱");
        GUI.setTitle("隊伍名稱輸入介面");
        GUI.open();
        return name;
    }
}