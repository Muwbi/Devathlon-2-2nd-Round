package com.muwbi.devathlon.objects;

import com.muwbi.devathlon.game.Message;
import com.muwbi.devathlon.game.Team;
import com.muwbi.devathlon.scheduler.BoardComputerTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Gelox_.
 */
public class BoardComputer implements Listener {

    private static final Material BLOCK_MATERIAL = Material.ENDER_CHEST;

    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
            if ( event.getClickedBlock().getType() == BLOCK_MATERIAL ) {
                event.setCancelled( true );

                Player player = event.getPlayer();
                Team team = Team.getTeam( player.getUniqueId() );
                Block clickedBlock = event.getClickedBlock();

                if ( clickedBlock.getLocation().equals( team.getOtherTeam().getBoardComputer().getLocation() ) ) {
                    if ( player.getLocation().distanceSquared( clickedBlock.getLocation() ) > 9 ) {
                        player.sendMessage( Message.ERROR.getPrefix() + ChatColor.RED + "Du musst dich näher als 3 Blöcke am Boardcomputer befinden" );
                    } else {
                        new BoardComputerTask( 15, team, team.getOtherTeam().getBoardComputer().getLocation(), event.getPlayer() ).start();
                    }
                }
            }
        }

    }

}
