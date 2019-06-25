package com.Ayrou.AppleTeam.GUI.Component.Enums;

public enum ButtonType {
    BLANK(1,""),
    ARROW_UP(2,""),
    ARROW_DOWN(3,""),
    ARROW_LEFT(4,""),
    ARROW_RIGHT(5,""),
    LARROW_UP(6,""),
    LARROW_DOWN(7,""),
    LARROW_LEFT(8,""),
    LARROW_RIGHT(9,""),
    SORT_NUMBER(10,""),
    SORT_NAME(11,""),
    Affirmative(12,""),
    Negative(13,"");

    private int Value;
    private String name;

    ButtonType(int i, String name) {
        this.Value = i;
        this.name = name;
    }

    public int getValue() {
        return this.Value;
    }

    public String getName() {
        return this.name;
    }
}