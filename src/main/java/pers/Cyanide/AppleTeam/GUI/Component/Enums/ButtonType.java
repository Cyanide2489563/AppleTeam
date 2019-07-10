package pers.cyanide.appleTeam.gui.Component.Enums;

public enum ButtonType {
    BLANK(1,"") ,
    CREATE_TEAM(2,"建立隊伍"),
    JOIN_TEAM(3,"加入隊伍"),
    PREVIOUS_PAGE(4,"上一頁"),
    NEXT_PAGE(5,"下一頁"),
    LARROW_UP(6,""),
    LARROW_DOWN(7,""),
    LARROW_LEFT(8,""),
    LARROW_RIGHT(9,""),
    SORT_NUMBER(10,""),
    SORT_NAME(11,"名稱篩選"),
    Affirmative(12,"接受"),
    Negative(13,"拒絕");

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