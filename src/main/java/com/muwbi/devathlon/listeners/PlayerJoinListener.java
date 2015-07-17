package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.scheduler.CountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Muwbi
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        event.setJoinMessage( ChatColor.GREEN + "Gejoint " + ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.GREEN + " ist" );

        if( Bukkit.getOnlinePlayers().size() == 6 ) {
            new CountdownTask( 10 ).start();
        }
    }

    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        event.setQuitMessage( event.getPlayer().getName() + ChatColor.RED + " ist im ewigen Kosmos verloren" );
    }

}
