package com.ayrou.team.GUI.Item.Enums;

public enum ButtonType {

    BLANK(1, ""),
    Affirmative(2, "接受"),
    Negative(3, "拒絕");

    private int Value;
    private String Name;

    ButtonType(int i) {
        this.Value = i;
    }

    ButtonType(int i, String s) {
        this.Value = i;
        this.Name = s;
    }

    public int getValue() {
        return this.Value;
    }

    public String getName() {
        return this.Name;
    }
}