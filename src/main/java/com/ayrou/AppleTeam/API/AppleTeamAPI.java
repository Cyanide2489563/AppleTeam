package com.Ayrou.AppleTeam.API;

import com.Ayrou.AppleTeam.AppleTeam;
import com.Ayrou.AppleTeam.Team.Team;
import com.Ayrou.AppleTeam.Team.TeamManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AppleTeamAPI {

    private final AppleTeam instance;

    public AppleTeamAPI(AppleTeam instance) {
        this.instance = instance;
    }

    public Team getTeam(Player player) {
        return TeamManager.getInstance().getTeam(player);
    }

    public Team getTeam(UUID player) {
        return TeamManager.getInstance().getTeam(player);
    }

}