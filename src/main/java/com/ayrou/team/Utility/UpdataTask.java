package com.ayrou.team.Utility;

import com.ayrou.team.Main;
import com.ayrou.team.Team.TeamManager;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdataTask extends BukkitRunnable {

    public UpdataTask(Main plugin, TeamManager teamManager) {
        runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {
        Main.getTeamManager().update();
    }
}