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
public class EndGameTask implements Runnable {

    private final AtomicInteger countdown;
    private BukkitTask bukkitTask;

    public EndGameTask( int seconds ) {
        countdown = new AtomicInteger( seconds + 1 );
    }

    @Override
    public void run() {
        int counter = countdown.decrementAndGet();

        if( counter > 0 ) {
            Bukkit.broadcastMessage(Message.NORMAL.getPrefix() + "Das Spiel stoppt in " + ChatColor.GOLD + ChatColor.DARK_AQUA + "!");
        } else {
            SpaceFighter.getInstance().getGameSession().endGame();
            stop();
        }
    }

    public void start() {
        bukkitTask = Bukkit.getScheduler().runTaskTimer(SpaceFighter.getInstance(), this, 0, 20);
    }

    public void stop() {
        bukkitTask.cancel();
    }
}
