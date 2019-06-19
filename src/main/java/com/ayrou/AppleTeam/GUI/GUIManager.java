package com.Ayrou.AppleTeam.GUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class GUIManager {

    private static HashMap<UUID, Inventory> test;

    public static class Builder {

        Player owner;
        String title;
        int size;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSize(WindowType size) {
            this.size = size.getValue();
            return this;
        }

        public Builder setOwner(Player owner) {
            this.owner = owner;
            return this;
        }

        public Inventory creater() {
            Inventory inventory = Bukkit.createInventory(null, size, title);
            test.put(owner.getUniqueId(), inventory);
            return inventory;
        }
    }
}