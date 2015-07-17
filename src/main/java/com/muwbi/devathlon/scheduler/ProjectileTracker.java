package com.muwbi.devathlon.scheduler;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.Team;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Muwbi
 */
@RequiredArgsConstructor
public class ProjectileTracker implements Runnable {

    private BukkitTask task;
    private int lifetime;

    private final Projectile projectile;
    private final Team team;

    @Override
    public void run() {
        lifetime++;

        if ( lifetime > 200 ) {
            projectile.remove();
            stop();
        }

        if ( projectile.getLocation().distanceSquared( team.getSpawnLocation() ) < 5000 /* TODO: Radius */ ) {

        }
    }

    public void start() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously( SpaceFighter.getInstance(), this, 0, 1 );
    }

    public void stop() {
        if ( task == null ) {
            throw new IllegalStateException( "Cannot stop a task which is not running" );
        }

        task.cancel();
    }

}
