package com.Ayrou.AppleTeam.Team;

import java.util.UUID;

public class TeamBuilder {
    String name;
    UUID leader;
    Visibility visibility;
    boolean encryption;
    boolean application;
    boolean friend;
    String password;
    Invite invite;
    ItemGet itemGet;

    public TeamBuilder setName(String name) {
        if (name == null) throw new NullPointerException();
        this.name = name;
        return this;
    }

    public TeamBuilder setLeader(UUID player) {
        if (player == null) throw new NullPointerException();
        this.leader = player;
        return this;
    }

    public TeamBuilder setVisibility(Visibility visibility) {
        if (visibility == null) throw new NullPointerException();
        this.visibility = visibility;
        return this;
    }

    public TeamBuilder setEncryption(String password) {
        if (password == null) throw new NullPointerException();
        this.encryption = true;
        this.password = password;
        return this;
    }

    public TeamBuilder setApplication(boolean application) {
        this.application = application;
        return this;
    }

    public TeamBuilder setFriend(boolean friend) {
        this.friend = friend;
        return this;
    }

    public TeamBuilder setInvite(Invite invite) {
        if (invite == null) throw new NullPointerException();
        this.invite = invite;
        return this;
    }

    public TeamBuilder setItemGet(ItemGet itemGet) {
        this.itemGet = itemGet;
        return this;
    }

    public void create() {
        Team team = new Team(this);
        TeamManager.getInstance().addTeam(name, team);
    }
}
