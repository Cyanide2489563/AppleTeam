package com.ayrou.team.Team;

import com.ayrou.team.Main;
import com.ayrou.team.Message.Message;
import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;

import java.util.*;

class Team {

    private Message message = Main.getMessage();
    private TeamManager teamManager = Main.getTeamManager();

    private String name;
    private UUID leader;
    private Visibility visibility;
    private boolean encryption;
    private byte[] password;
    private boolean leaderInviteOnly;
    private ArrayList<UUID> members;
    private HashMap<UUID, Long> invitations;
    private HashMap<UUID, Long> reviewList;

    static class Builder {
        private String name;
        private UUID leader;
        private Visibility visibility;
        private boolean encryption;
        private byte[] password;
        private boolean leaderInviteOnly;

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setLeader(UUID player) {
            this.leader = player;
            return this;
        }

        Builder setlederInviteOnly() {
            this.leaderInviteOnly = true;
            return this;
        }

        Builder setPublic() {
            this.visibility = Visibility.Public;
            return this;
        }

        Builder setPrivate() {
            this.visibility = Visibility.Private;
            return this;
        }

        Builder setEncryption(byte[] password) {
            this.encryption = true;
            this.password = password;
            return this;
        }

        public Team create() {
            return new Team(this);
        }
    }

    private Team(Builder builder) {
        this.name = builder.name;
        this.leader = builder.leader;
        this.visibility = builder.visibility;
        this.encryption = builder.encryption;
        this.password = builder.password;
        this.leaderInviteOnly = builder.leaderInviteOnly;
        this.members = new ArrayList<>();
        this.members.add(leader);
        this.invitations = new HashMap<>();
        this.reviewList = new HashMap<>();
    }

    public boolean isMember(@NotNull UUID player) {
        return members.contains(player);
    }

    public int getTameSize() {
        return members.size();
    }

    public String getTeamName() {
        return name;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public boolean isEncryption() {
        return encryption;
    }

    public boolean isLeaderInviteOnly() {
        return leaderInviteOnly;
    }

    public boolean isLeader(@NotNull UUID player) {
        return leader.equals(player);
    }

    public boolean isTeamFull() {
        return members.size() < teamManager.getMaximum();
    }

    void checkInvitations() {
        for (Map.Entry<UUID, Long> invitation : invitations.entrySet()) {
            if (invitation.getValue() < System.currentTimeMillis()) {
                Objects.requireNonNull(Bukkit.getPlayer(invitation.getKey()))
                        .sendMessage(message.getMessage("Team_Invite_TimeOut"));
                invitations.remove(invitation.getKey());
                return;
            }
        }
    }

    public void invite(@NotNull UUID player) {
        if (!members.contains(player) && !invitations.containsKey(player)) {
            invitations.put(player, System.currentTimeMillis() + teamManager.getInviteTimeout());
        }
    }

    public void sendMessages(@NotNull String messages) {
        for (UUID player : members) {
            Objects.requireNonNull(Bukkit.getPlayer(player)).sendMessage(messages);
        }
    }

    public enum Visibility {
        Public(0),
        Private(1);

        private int value;

        Visibility(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}