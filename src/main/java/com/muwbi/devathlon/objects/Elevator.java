package com.muwbi.devathlon.objects;

import com.muwbi.devathlon.game.Message;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * Created by Muwbi
 */
public class Elevator implements Listener {

    private static final Material BLOCK_MATERIAL = Material.DIAMOND_BLOCK;

    @EventHandler
    public void onJump( PlayerToggleSneakEvent event ) {
        if ( !event.isSneaking() || event.getPlayer().getLocation().clone().subtract( 0, 1, 0 ).getBlock().getType() != BLOCK_MATERIAL ) {
            return;
        }

        Player player = event.getPlayer();

        int xCoordinate = (int) player.getLocation().getX();
        int yCoordinate = (int) player.getLocation().getY();
        int zCoordinate = (int) player.getLocation().getZ();

        Block nextElevatorBlock = null;

        for ( int y = yCoordinate; y < 256; y++ ) {
            Block block = player.getWorld().getBlockAt( xCoordinate, y, zCoordinate );

            if ( block.getType() == BLOCK_MATERIAL ) {
                nextElevatorBlock = block;
            }
        }

        if ( nextElevatorBlock == null ) {
            for ( int y = yCoordinate; y > 0; y-- ) {
                Block block = player.getWorld().getBlockAt( xCoordinate, y, zCoordinate );

                if ( block.getType() == BLOCK_MATERIAL ) {
                    nextElevatorBlock = block;
                }
            }
        }

        if ( nextElevatorBlock != null ) {
            player.teleport( nextElevatorBlock.getLocation().add( 0, 1, 0 ) );
            player.getWorld().playEffect( player.getLocation(), Effect.ENDER_SIGNAL, 3 );
            player.getWorld().playSound( player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 3 );
        } else {
            player.sendMessage( Message.ERROR.getPrefix() + ChatColor.RED + "Aufzug defekt!" );
        }
    }

}
