package com.muwbi.devathlon.scheduler;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Gelox_.
 */
public class CountdownTask implements Runnable {

    private int countdown;
    private BukkitTask bukkitTask;

    public CountdownTask( int seconds ) {
        countdown = seconds + 1;
    }

    @Override
    public void run() {
        countdown--;

        if ( countdown > 0 ) {
            Bukkit.broadcastMessage( Message.NORMAL.getPrefix() + "Das Spiel startet in " + ChatColor.GOLD + countdown + " Sekunden" );
        } else {
            Bukkit.broadcastMessage( Message.NORMAL.getPrefix() + "Das Spiel startet!" );
            SpaceFighter.getInstance().getGameSession().nextGameState();
            stop();
        }
    }

    public void start() {
        bukkitTask = Bukkit.getScheduler().runTaskTimer( SpaceFighter.getInstance(), this, 0, 20 );
    }

    public void stop() {
        bukkitTask.cancel();
    }
}
