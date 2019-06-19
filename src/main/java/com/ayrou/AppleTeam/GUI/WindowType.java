package com.Ayrou.AppleTeam.GUI;

enum WindowType {
    MAXWINDOWS(54),
    MINWINDOWS(27);

    int size;

    WindowType(int size) {
        this.size = size;
    }

    int getValue() {
        return size;
    }
}
