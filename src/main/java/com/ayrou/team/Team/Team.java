package com.ayrou.team.Team;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Team {
    private ArrayList<Player> members;
    private Player leader;

    public void createTeam(String teamName, Player leader) {
        members = new ArrayList<>();
        members.add(leader);
        this.leader = leader;
    }

    public boolean isMember(Player player) {
        return members.contains(player);
    }
}
