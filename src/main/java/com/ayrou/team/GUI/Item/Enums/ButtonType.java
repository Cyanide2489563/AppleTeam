package com.ayrou.team.GUI.Item.Enums;

public enum ButtonType {

    BLANK(1),
    Affirmative(2),
    Negative(3);

    private int Value;

    ButtonType(int i) {
        this.Value = i;
    }

    public int getValue() {
        return this.Value;
    }
}
