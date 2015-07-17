package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.events.BorderDestroyedEvent;
import com.muwbi.devathlon.game.Message;
import com.muwbi.devathlon.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Muwbi
 */
public class BorderDestroyedListener implements Listener {

    @EventHandler
    public void onBorderDestroyed( BorderDestroyedEvent event ) {
        Team team = event.getTeam();

        Bukkit.broadcastMessage( Message.NORMAL.getPrefix() + ChatColor.RED + " Das Kraftfeld " + ( team == Team.IMPERIAL ? "des" : "der" ) +
                event.getTeam().getChatColor() + event.getTeam().getFullName() + ChatColor.RED + " wurde zerstört" );
    }

}
