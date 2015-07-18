package com.muwbi.devathlon.scheduler;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitTask;

import java.util.Optional;

/**
 * Created by Muwbi
 */
public class LaserPistolTracker implements Runnable {

    private BukkitTask task;
    private int lifetime;

    private final Projectile projectile;
    private final Player player;

    public LaserPistolTracker( Projectile projectile, Player player ) {
        this.projectile = projectile;
        this.player = player;
    }

    @Override
    public void run() {
        lifetime++;

        if ( lifetime > 100 ) {
            stop();
        }

        if ( getNearbyEnemy() != null ) {
            getNearbyEnemy().damage( 6, player );
            stop();
        }
    }

    public void start() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously( SpaceFighter.getInstance(), this, 0, 1 );
    }

    public void stop() {
        task.cancel();

        Bukkit.getScheduler().runTaskLater( SpaceFighter.getInstance(), projectile::remove, 1 );
    }

    private Player getNearbyEnemy() {
        Optional<Entity> optional = projectile.getNearbyEntities( 0.5, 0.5, 0.5 ).stream()
                .filter( entity -> entity.getType() == EntityType.PLAYER &&
                        Team.getTeam( entity.getUniqueId() ).getOtherTeam() == Team.getTeam( player.getUniqueId() ) ).findAny();

        if ( optional.isPresent() ) {
            return (Player) optional.get();
        } else {
            return null;
        }
    }

}
