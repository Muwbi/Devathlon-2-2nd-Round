package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.game.Team;
import com.muwbi.devathlon.scheduler.BeamTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Gelox_.
 */
public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract( PlayerInteractEvent event ) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if( isRightClick( action ) ) {
            if( player.getLocation().distance(Team.getTeam( player.getUniqueId() ).getBeamLocation()) < 3 ) {
                new BeamTask( 5, player, Team.getTeam( player.getUniqueId() ).getBeamLocation() ).start();
            }
        }
    }

    private boolean isRightClick( Action action ) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }
}
