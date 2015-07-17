package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.game.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Gelox_.
 */
public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath( PlayerDeathEvent event ) {
        Player player = event.getEntity();
        if( player.getKiller() instanceof Player ) {
            event.setDeathMessage( Message.NORMAL.getPrefix() + ChatColor.GOLD + event.getEntity().getName() + ChatColor.DARK_AQUA + " gekillt worden sein von " + ChatColor.GOLD + player.getKiller().getName() );
        } else {
            event.setDeathMessage( Message.NORMAL.getPrefix() + ChatColor.GOLD + player.getName() + ChatColor.DARK_AQUA + " gestorben ist!");
        }
    }
}
