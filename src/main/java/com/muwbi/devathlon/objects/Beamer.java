package com.muwbi.devathlon.objects;

import com.muwbi.devathlon.game.Team;
import com.muwbi.devathlon.scheduler.BeamTask;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Muwbi
 */
public class Beamer implements Listener {

    private static final Material BLOCK_MATERIAL = Material.BEACON;

    @EventHandler
    public void onInteract( PlayerInteractEvent event ) {
        Player player = event.getPlayer();

        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == BLOCK_MATERIAL ) {
            if ( player.getLocation().distanceSquared( Team.getTeam( player.getUniqueId() ).getBeamLocation() ) < 9 ) {
                new BeamTask( 5, player, Team.getTeam( player.getUniqueId() ).getBeamLocation() ).start();
            }
        }
    }

}
