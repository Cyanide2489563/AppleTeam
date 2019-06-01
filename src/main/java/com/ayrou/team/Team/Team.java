package com.ayrou.team.Team;

import com.ayrou.team.Main;
import com.ayrou.team.Message.Message;
import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;

import java.util.*;

class Team {

    private Message message = Main.getMessage();
    private TeamManager teamManager = Main.getTeamManager();

    private String teamName;
    private boolean leaderInviteOnly;
    private boolean visibility;
    private boolean encryption;
    private byte[] password;
    private UUID leader;
    private ArrayList<UUID> members;
    private HashMap<UUID, Long> invitations;

    Team(@NotNull String teamName, @NotNull UUID leader, Boolean visibility, Boolean encryption) {
        this.teamName = teamName;
        this.visibility = visibility;
        this.encryption = encryption;
        this.leader = leader;
        members = new ArrayList<>();
        members.add(leader);
        invitations = new HashMap<>();
    }

    public boolean isMember(@NotNull UUID player) {
        return members.contains(player);
    }

    public int getTameSize() {
        return members.size();
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public boolean isLeaderInviteOnly() {
        return leaderInviteOnly;
    }

    public boolean isLeader(@NotNull UUID player) {
        return leader.equals(player);
    }

    public boolean isFull() {
        return members.size() < teamManager.maximum;
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
            invitations.put(player, System.currentTimeMillis() + teamManager.inviteTimeout);
        }
    }

    public void sendMessages(@NotNull String messages) {
        for (UUID player : members) {
            Objects.requireNonNull(Bukkit.getPlayer(player)).sendMessage(messages);
        }
    }
}