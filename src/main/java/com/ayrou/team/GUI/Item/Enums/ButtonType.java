package com.ayrou.team.GUI.Item.Enums;

public enum ButtonType {

    BLANK(1, ""),
    Affirmative(2, "接受"),
    Negative(3, "拒絕"),
    TeamList(4,"隊伍列表"),
    Invite(5,"邀請玩家"),
    TeamOption(6,"設定"),
    TeamInfo(7,"隊伍資訊"),
    leave(8,"離開隊伍"),
    Join(9,"加入隊伍"),
    Public(10,"公開"),
    Password(11,"密碼"),
    Search(12,"搜尋"),
    Prev_Page(13,"上一頁"),
    Next_Page(14,"下一頁"),
    All(15,"全部");

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