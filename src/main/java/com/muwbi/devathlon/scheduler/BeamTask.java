package com.muwbi.devathlon.scheduler;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.Message;
import com.muwbi.devathlon.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Gelox_.
 */
public class BeamTask implements Runnable {


    private final AtomicInteger countdown;
    private BukkitTask bukkitTask;
    private Player beamer;
    private Location beamLocation;

    public BeamTask( int seconds, Player player, Location beamLocation) {
        countdown = new AtomicInteger( seconds + 1 );
        beamer = player;
        this.beamLocation = beamLocation;
    }

    @Override
    public void run() {
        int counter = countdown.decrementAndGet();

        if( beamer.getLocation().distance( beamLocation ) < 3) {
            if (counter > 0) {
                beamer.sendMessage(Message.NORMAL.getPrefix() + "Du wirst in " + ChatColor.GOLD + counter + ChatColor.DARK_AQUA + " Sekunden auf das gegnerische Schiff gebeamt!");
            } else {
                beamer.teleport(Team.getTeam(beamer.getUniqueId()).getOtherTeam().getBeamLocation());
                beamer.sendMessage(Message.NORMAL.getPrefix() + "Du wurdest teleportiert!");
                stop();
            }
        } else {
            beamer.sendMessage(Message.NORMAL.getPrefix() + "Du musst in einem Radium von 3 Blöcken zum Beamer bleiben!");
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
