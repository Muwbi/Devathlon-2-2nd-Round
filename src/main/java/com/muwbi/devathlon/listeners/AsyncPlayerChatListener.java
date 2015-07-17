package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Gelox_.
 */
public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onChat( AsyncPlayerChatEvent event ) {
        Team team = Team.getTeam( event.getPlayer().getUniqueId() );
        if (team != null) {
            event.setFormat( ChatColor.GRAY + "[" + team.getChatColor() + team.getShortName() + ChatColor.GRAY + "] " + ChatColor.GOLD + "%s" + ChatColor.GRAY + ": " + ChatColor.WHITE + "%s" );
        } else {
            event.setFormat( ChatColor.GOLD + "%s" + ChatColor.GRAY + ": " + ChatColor.WHITE + "%s");
        }
    }

}
