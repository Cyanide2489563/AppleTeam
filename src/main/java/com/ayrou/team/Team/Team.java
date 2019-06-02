package com.ayrou.team.Team;

import com.ayrou.team.Main;
import com.ayrou.team.Message.Message;
import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;

import java.util.*;

final class Team {

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

    Team(TeamManager.Builder builder) {
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
}