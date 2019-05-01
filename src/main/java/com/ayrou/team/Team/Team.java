package com.ayrou.team.Team;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Team {

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
}