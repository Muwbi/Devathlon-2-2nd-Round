package com.muwbi.devathlon.objects;

import com.muwbi.devathlon.game.Team;
import com.muwbi.devathlon.scheduler.BoardTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Gelox_.
 */
public class BoardComputer implements Listener {

    private static final Material boardComputer = Material.ENDER_CHEST;

    @EventHandler
    public void onInteract( PlayerInteractEvent event ) {
        Team team = Team.getTeam( event.getPlayer().getUniqueId() );

        if( event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
            if( event.getClickedBlock().getType() == boardComputer ) {
                if( event.getClickedBlock().getLocation() == Team.getTeam( event.getPlayer().getUniqueId() ).getOtherTeam().getBoardComputer().getLocation() ) {
                    new BoardTask( 15, team, team.getOtherTeam().getBoardComputer().getLocation(), event.getPlayer() ).start();
                }
            }
        }

    }

}
