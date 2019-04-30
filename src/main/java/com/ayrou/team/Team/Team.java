package com.ayrou.team.Team;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Team {
    private ArrayList<Player> members;
    private HashMap<String, Long> invitations;
    private Player leader;
    private String teamName;

    public void createTeam(String teamName, Player leader) {
        members = new ArrayList<>();
        invitations = new HashMap<String, Long>();
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