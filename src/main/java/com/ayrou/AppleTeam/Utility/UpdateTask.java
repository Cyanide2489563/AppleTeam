package com.Ayrou.AppleTeam.Utility;

import com.Ayrou.AppleTeam.Main;
import com.Ayrou.AppleTeam.Team.TeamManager;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateTask extends BukkitRunnable {

    public UpdateTask(Main plugin, TeamManager teamManager) {
        runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {
        Main.getTeamManager().update();
    }
}