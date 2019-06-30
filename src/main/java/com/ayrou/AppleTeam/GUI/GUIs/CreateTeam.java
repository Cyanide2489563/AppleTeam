package com.Ayrou.AppleTeam.GUI.GUIs;

import com.Ayrou.AppleTeam.GUI.Component.AnvilGUI;
import com.Ayrou.AppleTeam.GUI.SubGui;
import com.Ayrou.AppleTeam.Team.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CreateTeam extends SubGui {

    private String titleName = "建立隊伍";
    private ItemStack i = new ItemStack(Material.PAPER);
    private AnvilGUI GUI;

    public CreateTeam() {
        ItemMeta itemMeta = i.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "輸入中英文大於3小於10");
        itemMeta.setLore(lore);
        i.setItemMeta(itemMeta);
    }

    @Override
    public void openInventory(Player player) {
        GUI = new AnvilGUI(player, e -> {
            if(e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT & e.hasText()) {
                String name = e.getText();
                if (checkTeamName(name)) {
                    TeamManager
                }
                player.sendMessage("你的隊伍名稱是" + e.getText());
                e.setWillClose(true);
            }
        });

        GUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, i);
        GUI.setSlotName(AnvilGUI.AnvilSlot.INPUT_LEFT,  "§r請輸入隊伍名稱");
        GUI.setTitle(titleName);
        GUI.open();
    }

    private boolean checkTeamName(String name) {

        int num = 0;
        boolean matches = true;
        for (int i = 0,j = name.length(); i < j; i++) {
            String temp = name.substring(i, i + 1);
            if (temp.matches("[\u4e00-\u9fa5]")) {
                num += 1;
            }
            else if (temp.matches("[a-zA-Z0-9]*")) {
                num += 1;
            }
            else matches =! matches;
        }

        return (num <= 10 & num >= 3) & matches;
    }

    @Override
    public String titleName() {
        return titleName;
    }
}