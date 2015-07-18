package com.muwbi.devathlon.objects;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.Message;
import com.muwbi.devathlon.game.Team;
import com.muwbi.devathlon.scheduler.BeamTask;
import org.bukkit.ChatColor;
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
    public void onPlayerInteract( PlayerInteractEvent event ) {
        Player player = event.getPlayer();

        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == BLOCK_MATERIAL ) {
            if ( Team.getTeam( player.getUniqueId() ).getOtherTeam().isBorderActive() ) {
                if ( player.getLocation().distanceSquared( Team.getTeam( player.getUniqueId() ).getBeamLocation() ) < 9 ) {
                    event.setCancelled( true );
                    new BeamTask( 5, player, Team.getTeam( player.getUniqueId() ).getBeamLocation() ).start();
                    SpaceFighter.getInstance().getGameSession().sendTeamMessage( Team.getTeam( player.getUniqueId() ).getOtherTeam(), ChatColor.BOLD + Message.ERROR.getPrefix() + "Der Feind startet einen Beamvorgang!" );
                }
            } else {
                player.sendMessage( Message.ERROR.getPrefix() + "Das Schild des feindlichen Schiffes ist noch aktiv! Zerstöre es, bevor du dich beamst!" );
            }
        }

    }

}
