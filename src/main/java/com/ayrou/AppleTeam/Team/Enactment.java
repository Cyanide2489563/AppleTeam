package com.Ayrou.AppleTeam.Team;

enum Visibility {
    Public(1),
    Private(2);

    private int value;

    Visibility(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

}

enum Invite {
    Leader_Invite(64),
    Member_Invite(128);

    private int value;

    Invite(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}

enum ItemGet {
    Killer(256),
    Dice(512),
    Score(1024);

    private int value;

    ItemGet(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}