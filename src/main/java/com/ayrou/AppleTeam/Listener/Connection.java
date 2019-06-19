package com.Ayrou.AppleTeam.Listener;

import com.Ayrou.AppleTeam.Main;
import com.Ayrou.AppleTeam.Team.Team;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class Connection implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID player = event.getPlayer().getUniqueId();
        Team team = Main.getTeamManager().getTeam(player);
        if(team != null) event.getPlayer().sendMessage(team.reConnection(player));
    }
}