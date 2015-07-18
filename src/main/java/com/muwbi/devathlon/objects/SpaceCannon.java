package com.muwbi.devathlon.objects;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.Message;
import com.muwbi.devathlon.game.Team;
import com.muwbi.devathlon.scheduler.ProjectileTracker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

    private static final Material BLOCK_MATERIAL = Material.QUARTZ_STAIRS;

    private final Location location;

    private Block barrel;
    private float yaw;

    private boolean isInUse;
    private UUID uuid;

    private long lastShot = 0;

    public SpaceCannon( Location blockLocation ) {
        Bukkit.getPluginManager().registerEvents( this, SpaceFighter.getInstance() );

        location = blockLocation;

        Stairs stairsData = (Stairs) blockLocation.getBlock().getState().getData();
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
            default:
                yaw = 0;
                break;
        }

        barrel = blockLocation.getBlock().getRelative( stairsData.getFacing(), 6 ).getLocation().subtract( 0, 3, 0 ).getBlock();
    }

    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        Player player = event.getPlayer();

        if ( ( event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK ) && event.getClickedBlock().getType() == BLOCK_MATERIAL ) {
            if ( Team.getTeam( player.getUniqueId() ).getSpaceCannonLocation().equals( event.getClickedBlock().getLocation() ) ) {
                if ( !isInUse ) {
                    isInUse = true;
                    uuid = player.getUniqueId();

                    player.sendMessage( Message.NORMAL.getPrefix() + ChatColor.DARK_AQUA + "Du benutzt nun die SpaceCannon. Sneake, um die Kanone zu verlassen." );

                    Location playerLocation = location.add( 0, 0.5, 0 );

                    playerLocation.setYaw( yaw );
                    playerLocation.setPitch( 0 );

                    player.teleport( playerLocation );
                }
            }
        } else if ( event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK ) {
            if ( isInUse && uuid == player.getUniqueId() ) {
                if ( SpaceFighter.getInstance().getGameSession().getScores().get( Team.getTeam( uuid ).getOtherTeam() ).getScore() > 0 ) {
                    if ( System.currentTimeMillis() - lastShot > 250 ) {
                        lastShot = System.currentTimeMillis();

                        Snowball snowball = player.getWorld().spawn( barrel.getLocation().clone().add( 0, 0.5, 0 ), Snowball.class );
                        snowball.setShooter( player );
                        snowball.setVelocity( player.getLocation().getDirection().clone().normalize().multiply( 3 ) );

                        new ProjectileTracker( snowball, Team.getTeam( player.getUniqueId() ).getOtherTeam() ).start();
                    }
                } else {
                    player.sendMessage( Message.ERROR.getPrefix() + ChatColor.DARK_AQUA + "Die Kanonen sind außer Betrieb, da das gegnerisches Schutzschild bereits zerstört wurde" );
                }
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
