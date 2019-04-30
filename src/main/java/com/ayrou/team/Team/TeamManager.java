package com.ayrou.team.Team;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TeamManager {
    ArrayList<Team> team;

    TeamManager() {
        team = new ArrayList<>();
    }

    public void addTeam(Team team) {
        this.team.add(team);
    }

    public Team getTeam(Player player) {
        for (Team team : team) {
            if (team.isMember(player)) return team;
        }
        return null;
    }
}
