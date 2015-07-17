package com.muwbi.devathlon.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.material.Stairs;


import java.util.UUID;

/**
 * Created by Muwbi
 */
public class SpaceCannon implements Listener {

    private static final Material BLOCK_MATERIAL = Material.COBBLESTONE_STAIRS;

    private boolean isInUse;
    private UUID uuid;

    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        Player player = event.getPlayer();

        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == BLOCK_MATERIAL ) {
            if ( !isInUse ) {
                Block clickedBlock = event.getClickedBlock();

                isInUse = true;
                uuid = player.getUniqueId();

                player.sendMessage( "Du benutzt nun die SpaceCannon" );

                Stairs stairsData = (Stairs) clickedBlock.getState().getData();
                float yaw = 0;
                switch ( stairsData.getFacing() ) {
                    case NORTH:
                        yaw = 180;
                        break;
                    case EAST:
                        yaw = -90;
                        break;
                    case SOUTH:
                        yaw = 0;
                        break;
                    case WEST:
                        yaw = 90;
                        break;
                }

                Location playerLocation = clickedBlock.getLocation().clone().add( 0, 0.5, 0 );

                playerLocation.setYaw( yaw );
                playerLocation.setPitch( 0 );

                player.teleport( playerLocation );
            }
        } else if ( event.getAction() == Action.RIGHT_CLICK_AIR ) {
            if ( isInUse ) {
                Snowball snowball = (Snowball) player.getWorld().spawnEntity( player.getEyeLocation(), EntityType.SNOWBALL );
                snowball.setVelocity( player.getLocation().getDirection().normalize().multiply( 5 ) );
            }
        }
    }

    @EventHandler
    public void onPlayerMove( PlayerMoveEvent event ) {
        if ( uuid == event.getPlayer().getUniqueId() ) {
            Location from = event.getFrom();
            Location to = event.getTo();

            if ( from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ() ) {
                event.setCancelled( true );
            }
        }
    }

    @EventHandler
    public void onPlayerToggleSneak( PlayerToggleSneakEvent event ) {
        if ( uuid == event.getPlayer().getUniqueId() && event.isSneaking() ) {
            isInUse = false;
            uuid = null;
        }
    }

}
