package com.ayrou.team.Team;

import com.ayrou.team.Main;
import com.sun.istack.internal.NotNull;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.UUID;

public class TeamManager {

    private Main plugin = Main.getInstance();
    private static ArrayList<Team> teams;
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

    int getMaximum() {
        return maximum;
    }

    long getInviteTimeout() {
        return inviteTimeout;
    }

    public boolean removeTeam(@NotNull Team team) {
        teams.remove(team);
        return !teams.contains(team);
    }

    public boolean hasTeam(@NotNull UUID player) {
        return teams.stream().filter(team -> team.isMember(player)).findFirst().orElse(null) != null;
    }

    public Team getTeam(@NotNull UUID player) {
        return teams.stream().filter(team -> team.isMember(player)).findFirst().orElse(null);
    }

    public void update() {
        if (teams != null) {
            for (Team team : teams) {
                team.checkInvitations();
            }
        }
    }

    static class Builder {
        String name;
        UUID leader;
        Visibility visibility;
        boolean encryption;
        byte[] password;
        boolean leaderInviteOnly;

        Builder setName(@NotNull String name) {
            this.name = name;
            return this;
        }

        Builder setLeader(@NotNull UUID player) {
            this.leader = player;
            return this;
        }

        Builder setlederInviteOnly() {
            this.leaderInviteOnly = true;
            return this;
        }

        Builder setPublic() {
            this.visibility = Visibility.Public;
            return this;
        }

        Builder setPrivate() {
            this.visibility = Visibility.Private;
            return this;
        }

        Builder setEncryption(@NotNull byte[] password) {
            this.encryption = true;
            this.password = password;
            return this;
        }

        Team create() {
            Team team = new Team(this);
            TeamManager.teams.add(team);
            return team;
        }
    }
}
