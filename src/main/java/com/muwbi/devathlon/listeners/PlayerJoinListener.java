package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.GameState;
import com.muwbi.devathlon.game.Message;
import com.muwbi.devathlon.game.Team;
import com.muwbi.devathlon.scheduler.CountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Created by Muwbi
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        event.setJoinMessage( Message.NORMAL.getPrefix() + "Gejoint " + ChatColor.GOLD + event.getPlayer().getName() + ChatColor.DARK_AQUA + " ist" );

        if( Bukkit.getOnlinePlayers().size() == 2 ) {
            SpaceFighter.getInstance().getGameSession().nextGameState();
            new CountdownTask( 10 ).start();
        }
    }

    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        UUID uuid = event.getPlayer().getUniqueId();

        event.setQuitMessage( Message.NORMAL.getPrefix() + ChatColor.GOLD + event.getPlayer().getName() + ChatColor.DARK_AQUA + " ist im ewigen Kosmos verloren!" );
        if( Team.hasTeam( uuid ) ) {
            Team.getTeam( uuid ).removeMember( uuid );
        }

    }

     @EventHandler
    public void onLogin( PlayerLoginEvent event ) {
         if(SpaceFighter.getInstance().getGameSession().getCurrentGameState() == GameState.INGAME) {
             event.disallow(null, Message.NORMAL.getPrefix() + "Das Spiel läuft schon! Versuche es zu einem späteren Zeitpunkt erneut!");
         }
    }
}
