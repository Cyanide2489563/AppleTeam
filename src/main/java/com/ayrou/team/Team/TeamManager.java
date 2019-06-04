package com.ayrou.team.Team;

import com.avaje.ebean.validation.NotNull;
import com.ayrou.team.Main;
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

    public boolean removeTeam(Team team) {
        if (team == null) throw new NullPointerException();
        teams.remove(team);
        return !teams.contains(team);
    }

    public boolean hasTeam(UUID player) {
        if (player == null) throw new NullPointerException();
        return teams.stream().filter(team -> team.isMember(player)).findFirst().orElse(null) != null;
    }

    public Team getTeam(UUID player) {
        if (player == null) throw new NullPointerException();
        return teams.stream().filter(team -> team.isMember(player)).findFirst().orElse(null);
    }

    public void update() {
        teams.forEach(Team::checkInvitations);
    }

    public static class Builder {
        String name;
        UUID leader;
        Visibility visibility;
        boolean encryption;
        String password;
        boolean leaderInviteOnly;

        public Builder setName(String name) {
            if (name == null) throw new NullPointerException();
            this.name = name;
            return this;
        }

        public Builder setLeader(UUID player) {
            if (player == null) throw new NullPointerException();
            this.leader = player;
            return this;
        }

        public Builder setlederInviteOnly() {
            this.leaderInviteOnly = true;
            return this;
        }

        public Builder setPublic() {
            this.visibility = Visibility.Public;
            return this;
        }

        public Builder setPrivate() {
            this.visibility = Visibility.Private;
            return this;
        }

        public Builder setEncryption(String password) {
            if (password == null) throw new NullPointerException();
            this.encryption = true;
            this.password = password;
            return this;
        }

        public Team create() {
            Team team = new Team(this);
            TeamManager.teams.add(team);
            return team;
        }
    }
}
