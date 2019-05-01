package com.ayrou.team.Team;

import com.ayrou.team.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TeamManager {

    private Main plugin = Main.getInstance();
    private ArrayList<Team> team;
    private int maximum; //隊伍人數上限
    private boolean leaderInviteOnly; //是否僅限隊長邀請
    private boolean isPublic;
    private long inviteTimeout; //邀請失效時間

    public TeamManager() {
        team = new ArrayList<>();
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

    public boolean isLeaderInviteOnly() {
        return leaderInviteOnly;
    }

    //取得經驗分享公式
    public boolean isPublic() {
        return isPublic;
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
        this.team.add(team);
    }

    public Team getTeam(Player player) {
        for (Team team : team) {
            if (team.isMember(player)) return team;
        }
        return null;
    }

    public void update() {

    }
}
