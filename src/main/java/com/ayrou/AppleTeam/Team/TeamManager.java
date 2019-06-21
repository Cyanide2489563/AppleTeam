package com.Ayrou.AppleTeam.Team;

import com.Ayrou.AppleTeam.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public final class TeamManager {

    private static TeamManager teamManager;
    private Main plugin = Main.getInstance();
    private static HashMap<String, Team> teams; //隊伍列表
    private int maximum; //隊伍人數上限
    private int inviteTimeout; //邀請失效時間
    private int disconnectionTimeout; //斷線逾期時間

    private TeamManager() {
        teams = new HashMap<>();
        getConfig();
    }

    public static TeamManager getInstance() {
        if (teamManager == null) teamManager = new TeamManager();
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

        public void create() {
            Team team = new Team(this);
            TeamManager.teams.put(name, team);
        }
    }

    public String invitePlayer(UUID inviter,UUID player) {
        if (Bukkit.getPlayer(player) == null) return ChatColor.GREEN + "該玩家不存在";
        if (inviter.equals(player)) return ChatColor.GREEN + "你不能邀請自己";

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
        Team team = teams.get(name);
        if (team == null) return ChatColor.GREEN + "該隊伍不存在";
        if (team.isTeamFull()) return ChatColor.GREEN + "隊伍已滿";
        if (!team.isInvited(player)) return ChatColor.GREEN + "你沒有被邀請";
        if (team.isMember(player)) return ChatColor.GREEN + "你已是該隊伍成員";
        if (hasTeam(player)) return ChatColor.GREEN + "已有隊伍";
        if (!team.accept(player)) return ChatColor.GREEN + "加入失敗";

        return ChatColor.GREEN + "已成功加入" + ChatColor.YELLOW + name;
    }

    public String cancelJoin(String name, UUID player) {
        Team team = teams.get(name);
        if (team == null) return ChatColor.GREEN + "該隊伍不存在";
        if (!team.isInvited(player)) return ChatColor.GREEN + "你沒有被邀請";
        if (!team.cancel(player)) return ChatColor.GREEN + "拒絕隊伍邀請失敗";
        if (team.isMember(player)) return ChatColor.GREEN + "你已是該隊伍成員";

        return ChatColor.GREEN + "已拒絕隊伍邀請";
    }

    public String joinTeam(String name, UUID player) {
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

        return ChatColor.GREEN + "加入成功";
    }

    public String disbandTeam(UUID player) {
        Team team = getTeam(player);
        if (team == null) return ChatColor.GREEN + "你沒有隊伍";
        if (!team.isLeader(player)) return ChatColor.GREEN + "僅隊長能解散隊伍";
        team.disband();
        removeTeam(team.getTeamName());
        return ChatColor.GREEN + "已成功解散隊伍";
    }

    public String kickMember(UUID player, UUID member) {
        Team team = getTeam(player);
        if (team == null) return ChatColor.GREEN + "你沒有隊伍";
        if (!team.isLeader(player)) return ChatColor.GREEN + "僅隊長能踢除隊伍成員";
        if (!team.isMember(member)) return ChatColor.GREEN + "該名玩家不是隊伍成員";
        if (player.equals(member)) return ChatColor.GREEN + "你不能踢除自己";
        if (team.isLeader(member)) return ChatColor.GREEN + "你不能踢除隊長";
        if (!team.kick(member)) return ChatColor.GREEN + "踢除玩家失敗";

        return ChatColor.GREEN + "已成功剔除玩家" + ChatColor.YELLOW + Bukkit.getPlayer(member).getName();
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

        if (config.getLong("Team.inviteTimeout") >= 30) {
            inviteTimeout = config.getInt("Team.inviteTimeout") * 1000;
        }
        else {
            inviteTimeout = 30000;
        }
        if (config.getInt("Team.DisconnectionTimeout") > 180)
            disconnectionTimeout = config.getInt("Team.DisconnectionTimeout") * 1000;
        else disconnectionTimeout = 180000;
    }

    int getMaximum() {
        return maximum;
    }

    int getInviteTimeout() {
        return inviteTimeout;
    }

    int getDisconnectionTimeout() {
        return disconnectionTimeout;
    }

    private boolean removeTeam(String name) {
        if (name == null) throw new NullPointerException();
        teams.remove(name);
        return teams.containsKey(name);
    }

    private boolean removeTeam(Team team) {
        if (team == null) throw new NullPointerException();
        teams.remove(team.getTeamName());
        return teams.containsKey(team.getTeamName());
    }

    public boolean hasTeam(UUID player) {
        if (player == null) throw new NullPointerException();
        return teams.values().stream().filter(team -> team.isMember(player)).findFirst().orElse(null) != null;
    }

    public Team getTeam(UUID player) {
        if (player == null) throw new NullPointerException();
        return teams.values().stream().filter(team -> team.isMember(player)).findFirst().orElse(null);
    }

    public Team getTeam(Player player) {
        if (player == null) throw new NullPointerException();
        return teams.values().stream().filter(team -> team.isMember(player.getUniqueId())).findFirst().orElse(null);
    }

    public Team getOfflineTeam(Player player) {
        if (player == null) throw new NullPointerException();
        return teams.values().stream().filter(team -> team.isOfflineMember(player.getUniqueId())).findFirst().orElse(null);
    }

    public void update() {
        new BukkitRunnable() {
            @Override
            public void run() {
                teams.forEach((String, Team) -> {
                    Team.checkInvitations();
                    Team.updateScoreBoard();
                });
            }
        }.runTaskTimer(plugin, 1, 1);
    }
}