package com.Ayrou.AppleTeam.Utility;

import com.Ayrou.AppleTeam.AppleTeam;
import com.Ayrou.AppleTeam.Team.TeamManager;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateTask extends BukkitRunnable {

    public UpdateTask(AppleTeam plugin) {
        runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {
        TeamManager.getInstance().update();
    }
}