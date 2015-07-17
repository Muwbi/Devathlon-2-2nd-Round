package com.muwbi.devathlon.objects;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;

import com.muwbi.devathlon.SpaceFighter;

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
public class SpaceCannon extends PacketAdapter implements Listener {

    private static final Material BLOCK_MATERIAL = Material.COBBLESTONE_STAIRS;

    private boolean isInUse;
    private UUID uuid;

    public SpaceCannon() {
        super( SpaceFighter.getInstance(), PacketType.Play.Server.ENTITY_METADATA );

        ProtocolLibrary.getProtocolManager().addPacketListener( this );
    }

    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == BLOCK_MATERIAL ) {
            if ( !isInUse ) {
                isInUse = true;
                uuid = event.getPlayer().getUniqueId();

                event.getPlayer().sendMessage( "Du benutzt nun die SpaceCannon" );
            }
        }
    }

    @EventHandler
    public void onPlayerMove( PlayerMoveEvent event ) {
        if ( uuid == event.getPlayer().getUniqueId() ) {
            event.setCancelled( true );
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
