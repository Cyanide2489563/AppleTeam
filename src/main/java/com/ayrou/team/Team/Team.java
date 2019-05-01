package com.ayrou.team.Team;

import com.ayrou.team.Main;
import com.ayrou.team.Message.Message;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Team {

    private Main plugin = Main.getInstance();
    private Message message = Main.getMessage();
    private TeamManager teamManager = Main.getTeamManager();
    private ArrayList<Player> members;
    private HashMap<Player, Long> invitations;
    private Player leader;
    private String teamName;
    private boolean leaderInviteOnly;
    private boolean visibility;

    public void createTeam(String teamName, Player leader) {
        members = new ArrayList<>();
        invitations = new HashMap<Player, Long>();
        members.add(leader);
        this.leader = leader;
    }

    public boolean isMember(Player player) {
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

    public boolean isLeader(Player player) {
        if (leader.equals(player)) {
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return members.size() < Main.getTeamManager().getMaximum();
    }

    void checkInvitations() {
        for (Map.Entry<Player, Long> invitation : invitations.entrySet()) {
            if (invitation.getValue() < System.currentTimeMillis()) {
                invitation.getKey().sendMessage(message.getMessage("Team_Invite_TimeOut"));
                invitations.remove(invitation.getKey());
                return;
            }
        }
    }

    public void invite(Player player) {
        if (!members.contains(player) && !invitations.containsKey(player)) {
            invitations.put(player, System.currentTimeMillis() + teamManager.getInviteTimeout());
        }
    }

    public void sendMessages(String messages) {
        for (Player player : members) {
            player.sendMessage(messages);
        }
    }
}