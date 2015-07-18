package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.game.Team;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by Gelox_.
 */
public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn( PlayerRespawnEvent event ) {
        event.setRespawnLocation( Team.getTeam( event.getPlayer().getUniqueId() ).getSpawnLocation() );
    }

}
