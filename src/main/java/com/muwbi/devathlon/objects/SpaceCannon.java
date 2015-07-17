package com.muwbi.devathlon.objects;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.util.WrapperPlayServerEntityMetadata;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.spigotmc.event.entity.EntityDismountEvent;

/**
 * Created by Muwbi
 */
public class SpaceCannon extends PacketAdapter implements Listener {

    private static final Material BLOCK_MATERIAL = Material.COBBLESTONE_STAIRS;

    private int entityId;
    private boolean isInUse;

    public SpaceCannon() {
        super( SpaceFighter.getInstance(), PacketType.Play.Server.ENTITY_METADATA );

        ProtocolLibrary.getProtocolManager().addPacketListener( this );
    }

    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
            makePlayerSitOnBlock( event.getPlayer(), event.getClickedBlock() );
        }
    }

    @EventHandler
    public void onEntityDismount( EntityDismountEvent event ) {
        if ( event.getDismounted().getType() == EntityType.ARROW ) {
            event.getDismounted().remove();
        }
    }

    @Override
    public void onPacketSending( PacketEvent event ) {
        if ( event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA ) {
            Bukkit.broadcastMessage( "Sending Entity Metadata packet" );

            WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata( event.getPacket() );

            Bukkit.broadcastMessage( "" + packet.getEntityId() );
            Bukkit.broadcastMessage( "" + entityId );

            if ( packet.getEntity( Bukkit.getWorlds().get( 0 ) ).getType() == EntityType.ARROW ) {
                WrappedDataWatcher dataWatcher = new WrappedDataWatcher( packet.getEntityMetadata() );

                Byte flag = dataWatcher.getByte( 0 );

                if ( flag != null ) {
                    Bukkit.broadcastMessage( "Modifying entity metadata for entity #" + packet.getEntityId() );

                    packet = new WrapperPlayServerEntityMetadata( packet.getHandle().deepClone() );
                    dataWatcher = new WrappedDataWatcher( packet.getEntityMetadata() );
                    dataWatcher.setObject( 0, (byte) ( flag | 0x20 ) );

                    event.setPacket( packet.getHandle() );
                }
            }
        }
    }

    private void makePlayerSitOnBlock( Player player, Block block ) {
        Bukkit.broadcastMessage( player.getName() + " sat down" );

        Entity entity = player.getWorld().spawnEntity( block.getLocation(), EntityType.ARROW );
        entity.setPassenger( player );

        entityId = entity.getEntityId();
    }

}
