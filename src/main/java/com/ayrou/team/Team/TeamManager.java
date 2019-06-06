package com.ayrou.team.Team;

import com.ayrou.team.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public final class TeamManager {

    private static TeamManager teamManager;
    private Main plugin = Main.getInstance();
    private static ArrayList<Team> teams; //隊伍列表
    private int maximum; //隊伍人數上限
    private long inviteTimeout; //邀請失效時間

    private TeamManager() {
        teams = new ArrayList<>();
        getConfig();
    }

    public static TeamManager getInstance() {
        if (teamManager == null) {
            teamManager = new TeamManager();
        }
        return teamManager;
    }

    public static class Builder {
        String name;
        UUID leader;
        Visibility visibility;
        boolean encryption;
        boolean application;
        boolean friend;
        String password;
        Invite invite;
        ItemGet itemGet;

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

        public Builder setVisibility(Visibility visibility) {
            if (visibility == null) throw new NullPointerException();
            this.visibility = visibility;
            return this;
        }

        public Builder setEncryption(String password) {
            if (password == null) throw new NullPointerException();
            this.encryption = true;
            this.password = password;
            return this;
        }

        public Builder setApplication(boolean application) {
            this.application = application;
            return this;
        }

        public Builder setFriend(boolean friend) {
            this.friend = friend;
            return this;
        }

        public Builder setInvite(Invite invite) {
            if (invite == null) throw new NullPointerException();
            this.invite = invite;
            return this;
        }

        public Builder setItemGet(ItemGet itemGet) {
            this.itemGet = itemGet;
            return this;
        }

        public Team create() {
            Team team = new Team(this);
            TeamManager.teams.add(team);
            return team;
        }
    }

    public String invitePlayer(UUID inviter,UUID player) {
        if (Bukkit.getPlayer(player) == null) return "該玩家不存在";
        if (inviter.equals(player)) return "你不能邀請自己";
        if (hasTeam(player)) return "該玩家已有隊伍";

        Team team = getTeam(inviter);
        if (team == null) return "你沒有隊伍";
        if (team.isTeamFull()) return "隊伍已滿";
        //TODO 判斷是否為黑名單成員
        if (team.isInvited(player)) return "已邀請過該玩家";
        if (team.isMember(player)) return "該玩家是隊伍成員";
        if (team.invite(inviter, player)) return "邀請成功";

        return "邀請失敗";
    }

    public void joinTeam(UUID player) {

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
}