package com.muwbi.devathlon.objects;

import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;


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
        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == BLOCK_MATERIAL ) {
            if ( !isInUse ) {
                isInUse = true;
                uuid = event.getPlayer().getUniqueId();

                event.getPlayer().sendMessage( "Du benutzt nun die SpaceCannon" );

                event.getPlayer().getLocation().setPitch( 0 );
            }
        }
    }

    @EventHandler
    public void onPlayerMove( PlayerMoveEvent event ) {
        if ( uuid == event.getPlayer().getUniqueId() ) {
            event.setTo( event.getFrom() );
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
