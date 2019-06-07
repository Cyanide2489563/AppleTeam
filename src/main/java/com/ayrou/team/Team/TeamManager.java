package com.ayrou.team.Team;

import com.ayrou.team.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.UUID;

public final class TeamManager {

    private static TeamManager teamManager;
    private Main plugin = Main.getInstance();
    private static HashMap<String, Team> teams; //隊伍列表
    private int maximum; //隊伍人數上限
    private long inviteTimeout; //邀請失效時間

    private TeamManager() {
        teams = new HashMap<>();
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
            TeamManager.teams.put(name, team);
            return team;
        }
    }

    public String invitePlayer(UUID inviter,UUID player) {
        if (inviter == null | player == null) throw new NullPointerException();

        if (Bukkit.getPlayer(player) == null) return "該玩家不存在";
        if (inviter.equals(player)) return "你不能邀請自己";

        Team team = getTeam(inviter);
        if (team == null) return ChatColor.GREEN + "你沒有隊伍";
        if (team.isTeamFull()) return ChatColor.GREEN + "隊伍已滿";
        //TODO 判斷是否為黑名單成員
        if (team.isInvited(player)) return ChatColor.GREEN + "已邀請過該玩家";
        if (team.isMember(player)) return ChatColor.GREEN + "該玩家是隊伍成員";
        if (hasTeam(player)) return ChatColor.GREEN + "該玩家已有隊伍";
        if (!team.invite(inviter, player)) return ChatColor.GREEN + "邀請失敗";

        return ChatColor.GREEN + "已成功邀請" + ChatColor.YELLOW + Bukkit.getPlayer(player).getName();
    }

    public String acceptJoin(String name, UUID player) {
        if (player == null) throw new NullPointerException();

        Team team = teams.get(name);
        if (team == null) return ChatColor.GREEN + "該隊伍不存在";
        if (team.isTeamFull()) return ChatColor.GREEN + "隊伍已滿";
        if (!team.isInvited(player)) return ChatColor.GREEN + "你沒有被邀請";
        if (team.isMember(player)) return ChatColor.GREEN + "你已是該隊伍成員";
        if (hasTeam(player)) return ChatColor.GREEN + "已有隊伍";
        if (!team.accept(player)) return ChatColor.GREEN + "加入失敗";

        return ChatColor.GREEN + "已成功加入" + ChatColor.YELLOW + name;
    }

    public String joinTeam(String name, UUID player) {
        if (name == null | player == null) throw new NullPointerException();

        if (hasTeam(player)) return "已有隊伍";

        Team team = teams.get(name);
        if (team == null) return "該隊伍不存在";
        if (team.isTeamFull()) return "隊伍已滿";
        if (team.isMember(player)) return "你已是隊伍成員";
        //TODO 判斷是否為黑名單成員
        if (team.isFriendCanJoin()) {

        }
        if (team.isEncryptionCanJoin()) {
            if (!enterPassword()) return "";
        }
        if (team.isApplicationCanJoin()) {

        }

        return "加入成功";
    }

    private boolean enterPassword() {
        return true;
    }

    private void getConfig() {
        FileConfiguration config = plugin.getConfig();

        if (config.getInt("Team.maximum") > 1) {
            maximum = config.getInt("Team.maximum");
        }
        else {
            maximum = 5;
        }

        if (config.getLong("Team.inviteTimeout") > 30) {
            inviteTimeout = config.getLong("Team.inviteTimeout") * 1000;
        }
        else {
            inviteTimeout = 30;
        }
    }

    int getMaximum() {
        return maximum;
    }

    long getInviteTimeout() {
        return inviteTimeout;
    }

    public boolean removeTeam(String name) {
        if (name == null) throw new NullPointerException();
        teams.remove(name);
        return teams.containsKey(name);
    }

    public boolean hasTeam(UUID player) {
        if (player == null) throw new NullPointerException();
        return teams.values().stream().filter(team -> team.isMember(player)).findFirst().orElse(null) != null;
    }

    public Team getTeam(UUID player) {
        if (player == null) throw new NullPointerException();
        return teams.values().stream().filter(team -> team.isMember(player)).findFirst().orElse(null);
    }

    public void update() {
        teams.forEach((String, Team) -> Team.checkInvitations());
    }
}