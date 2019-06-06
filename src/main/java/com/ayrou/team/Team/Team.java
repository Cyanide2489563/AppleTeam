package com.ayrou.team.Team;

import com.ayrou.team.Main;
import com.ayrou.team.Message.Message;
import org.bukkit.Bukkit;

import java.util.*;

final class Team {

    private Message message = Main.getMessage();
    private TeamManager teamManager = Main.getTeamManager();

    private String name;
    private UUID leader;
    private Visibility visibility;
    private boolean encryption;
    private boolean application;
    private boolean friend;
    private String password;
    private Invite invite;
    private ItemGet itemGet;
    private ArrayList<UUID> members;
    private HashMap<UUID, Long> invitations;
    private HashMap<UUID, Long> reviewList;

    Team(TeamManager.Builder builder) {
        this.name = builder.name;
        this.leader = builder.leader;
        this.visibility = builder.visibility;
        this.encryption = builder.encryption;
        this.application = builder.application;
        this.friend = builder.friend;
        this.password = builder.password;
        this.invite = builder.invite;
        this.itemGet = builder.itemGet;
        this.members = new ArrayList<>();
        this.members.add(leader);
        this.invitations = new HashMap<>();
        if (this.application) this.reviewList = new HashMap<>();
    }

    public String getTeamName() {
        return name;
    }

    public UUID getLeader() {
        return leader;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public boolean isEncryptionCanJoin() {
        return encryption;
    }

    public boolean isApplicationCanJoin() {
        return application;
    }

    public boolean isFriendCanJoin() {
        return friend;
    }

    public boolean thisPlayerCanInvite(UUID player) {
        return player.equals(leader) | (isMember(player) & invite.equals(Invite.Member_Invite));
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public boolean isTeamFull() {
        return !(members.size() < teamManager.getMaximum());
    }

    public boolean isMember(UUID player) {
        return members.contains(player);
    }

    public int getTameSize() {
        return members.size();
    }

    void checkInvitations() {
        for (Map.Entry<UUID, Long> invitation : invitations.entrySet()) {
            if (isInviteTimeOut(invitation.getValue())) {
                Objects.requireNonNull(Bukkit.getPlayer(invitation.getKey()))
                        .sendMessage(message.getMessage("Team_Invite_TimeOut"));
                invitations.remove(invitation.getKey());
            }
            if (isInvitePlayerJoined(invitation.getKey())) {
                //TODO
                Objects.requireNonNull(Bukkit.getPlayer(invitation.getKey()))
                        .sendMessage(message.getMessage("Team_Invite_TimeOut"));
                invitations.remove(invitation.getKey());
            }
        }
    }

    private boolean isInviteTimeOut(long time) {
        return time < System.currentTimeMillis();
    }

    private boolean isInvitePlayerJoined(UUID player) {
        return isMember(player);
    }

    int invite(UUID player) {
        if (!members.contains(player) && !invitations.containsKey(player)) {
            invitations.put(player, System.currentTimeMillis() + teamManager.getInviteTimeout());
        }

        return 100;
    }

    public void sendMessages(String messages) {
        for (UUID player : members) {
            Objects.requireNonNull(Bukkit.getPlayer(player)).sendMessage(messages);
        }
    }

    public enum ErrorCode {

    }
}