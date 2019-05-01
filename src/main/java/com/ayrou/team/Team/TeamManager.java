package com.ayrou.team.Team;

import com.ayrou.team.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TeamManager {

    private Main plugin = Main.getInstance();
    private ArrayList<Team> teams;
    private int maximum; //隊伍人數上限
    private long inviteTimeout; //邀請失效時間

    public TeamManager() {
        teams = new ArrayList<>();
        getConfig();
    }

    private void getConfig() {
        FileConfiguration config = plugin.getConfig();

        if (config.getInt("maximum") > 1) {
            maximum = config.getInt("maximum");
        }
        else {
            maximum = 5;
        }

        if (config.getLong("inviteTimeout") > 30) {
            inviteTimeout = config.getLong("inviteTimeout")*1000;
        }
        else {
            inviteTimeout = 30000;
        }
    }

    //取得團隊人數上限
    public int getMaximum() {
        return maximum;
    }

    //取得邀請過期時間
    public long getInviteTimeout() {
        return inviteTimeout;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
    }

    public Team getTeam(Player player) {
        for (Team team : teams) {
            if (team.isMember(player)) return team;
        }
        return null;
    }

    public void update() {
        if (teams != null) {
            for (Team team : teams) {
                team.checkInvitations();
            }
        }
    }
}
