package pers.cyanide.appleTeam.api;

import pers.cyanide.appleTeam.AppleTeam;
import pers.cyanide.appleTeam.team.Team;
import pers.cyanide.appleTeam.team.TeamManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AppleTeamAPI {

    private AppleTeam instance;

    public AppleTeamAPI(AppleTeam instance) {
        this.instance = instance;
    }

    public Team getTeam(Player player) {
        return TeamManager.getInstance().getTeam(player);
    }

    public Team getTeam(UUID player) {
        return TeamManager.getInstance().getTeam(player);
    }

    public boolean isTeamLeader(Player player) {
        return TeamManager.getInstance().getTeam(player).getLeader().equals(player.getUniqueId());
    }

    public boolean isTeamLeader(UUID player) {
        return TeamManager.getInstance().getTeam(player).getLeader().equals(player);
    }

    public int getTeamSize(Team team) {
        return team.getMemberSize();
    }

}