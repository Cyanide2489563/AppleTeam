package com.Ayrou.AppleTeam.Listener;

import com.Ayrou.AppleTeam.AppleTeam;
import com.Ayrou.AppleTeam.Team.Team;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class Disconnection implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID player = event.getPlayer().getUniqueId();
        Team team = AppleTeam.getTeamManager().getTeam(player);
        if(team != null) team.disconnection(player);
    }
}