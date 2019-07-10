package pers.cyanide.appleTeam.utility;

import pers.cyanide.appleTeam.AppleTeam;
import pers.cyanide.appleTeam.team.TeamManager;
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