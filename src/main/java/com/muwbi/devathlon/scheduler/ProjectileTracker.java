package com.muwbi.devathlon.scheduler;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.events.BorderDestroyedEvent;
import com.muwbi.devathlon.game.Team;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Score;

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
            stop();
        }

        if ( projectile.getLocation().distanceSquared( team.getSpawnLocation() ) < 300 ) {
            Score score = SpaceFighter.getInstance().getGameSession().getScores().get( team );

            if ( score.getScore() <= 0 ) {
                score.setScore( 0 );
                BorderDestroyedEvent event = new BorderDestroyedEvent( team );
                Bukkit.getPluginManager().callEvent( event );
            } else {
                score.setScore( score.getScore() - 25 );
            }

            stop();
        }
    }

    public void start() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously( SpaceFighter.getInstance(), this, 0, 1 );
    }

    public void stop() {
        if ( task == null ) {
            throw new IllegalStateException( "Cannot stop a task which is not running" );
        }

        Bukkit.getScheduler().runTaskLater( SpaceFighter.getInstance(), projectile::remove, 1 );

        task.cancel();
    }

}
