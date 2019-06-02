package com.ayrou.team.Team;

enum Visibility {
    Public(0),
    Private(1);

    private int value;

    Visibility(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}