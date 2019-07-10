package pers.cyanide.appleTeam.listener;

import pers.cyanide.appleTeam.team.Team;
import pers.cyanide.appleTeam.team.TeamManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class Disconnection implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID player = event.getPlayer().getUniqueId();
        Team team = TeamManager.getInstance().getTeam(player);
        if(team != null) team.disconnection(player);
    }
}