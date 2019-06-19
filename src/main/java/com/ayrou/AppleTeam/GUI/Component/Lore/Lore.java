package com.Ayrou.AppleTeam.GUI.Component.Lore;

import java.util.ArrayList;
import java.util.Arrays;

public class Lore {
    public static ArrayList<String> createLore(String ...text) {
        return new ArrayList<>(Arrays.asList(text));
    }
}