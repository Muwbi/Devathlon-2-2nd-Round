package com.muwbi.devathlon.scheduler;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.events.GameStateChangeEvent;
import com.muwbi.devathlon.game.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Gelox_.
 */
public class CountdownTask implements Runnable {

    private final AtomicInteger countdown;
    private BukkitTask bukkitTask;

    public CountdownTask( int seconds ) {
        countdown = new AtomicInteger( seconds + 1 );
    }
    @Override
    public void run() {
        int counter = countdown.decrementAndGet();

        if( counter > 0 ) {
            Bukkit.broadcastMessage( Message.NORMAL.getPrefix() + "Das Spiel startet in " + ChatColor.GOLD + counter + ChatColor.DARK_AQUA + " Sekunden" );
        } else {
            Bukkit.broadcastMessage( Message.NORMAL.getPrefix() + "Das Spiel startet!");
            SpaceFighter.getInstance().getGameSession().nextGameState();
            SpaceFighter.getInstance().getGameSession().startGame();
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
