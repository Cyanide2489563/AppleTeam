package com.Ayrou.AppleTeam.Listener;

import com.Ayrou.AppleTeam.Main;
import com.Ayrou.AppleTeam.Team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Connection implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Team team = Main.getTeamManager().getOfflineTeam(player);
        if(team != null) {
            String status = team.reConnection(player.getUniqueId());
            event.getPlayer().sendMessage(status);
        }
    }
}