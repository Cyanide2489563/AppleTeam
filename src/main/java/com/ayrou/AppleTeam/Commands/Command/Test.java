package com.Ayrou.AppleTeam.Commands.Command;

import com.Ayrou.AppleTeam.Commands.SubCommand;

import com.Ayrou.AppleTeam.GUI.Component.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Test extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Player P = player;

        AnvilGUI GUI = new AnvilGUI(P, e -> {
            if(e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT & e.hasText()) {
                e.setWillClose(true);
                P.sendMessage("你的隊伍名稱是" + e.getText());
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
    }

    @Override
    public String name() {
        return "Test";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
