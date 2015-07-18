package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.GameState;
import com.muwbi.devathlon.game.Team;
import com.muwbi.devathlon.objects.LaserPistol;
import com.muwbi.devathlon.objects.SpaceCannon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by Gelox_.
 */
public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn( PlayerRespawnEvent event ) {
        Player player = event.getPlayer();

        event.setRespawnLocation( Team.getTeam( player.getUniqueId() ).getSpawnLocation() );

        if ( SpaceFighter.getInstance().getGameSession().getCurrentGameState() == GameState.INGAME ) {
            player.getInventory().clear();
            player.getInventory().setItem( 0, LaserPistol.getByUUID( player.getUniqueId() ).getItemStack() );
        }
    }

}
