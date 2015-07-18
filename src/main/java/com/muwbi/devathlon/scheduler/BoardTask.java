package com.muwbi.devathlon.scheduler;

import com.comphenix.protocol.PacketType;
import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.Message;
import com.muwbi.devathlon.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Gelox_.
 */
public class BoardTask implements Runnable {

    private final AtomicInteger countdown;
    private BukkitTask bukkitTask;
    private Team attackerTeam;
    private Location boardComputerLocation;
    private Player attacker;
    private List<Integer> broadcastIntegers = Arrays.asList(15, 10, 5, 4, 3, 2, 1);

    public BoardTask( int seconds, Team attackerTeam, Location boardComputerLocation, Player attacker ) {
        countdown = new AtomicInteger( seconds + 1 );
        this.attackerTeam = attackerTeam;
        this.boardComputerLocation = boardComputerLocation;
        this.attacker = attacker;

    }
    @Override
    public void run() {
        int counter = countdown.decrementAndGet();

        if( counter > 0 ) {
            if (! ( attacker.getLocation().distanceSquared( boardComputerLocation ) > 9) ) {

                if (broadcastIntegers.contains(counter)) {
                    Bukkit.broadcastMessage(Message.NORMAL.getPrefix() + "Der Bordcomputer ist in " + ChatColor.GOLD + counter + ChatColor.RED + " Sekunden entschärft!");
                }

            } else {
                Bukkit.broadcastMessage(Message.ERROR.getPrefix() + "Das Umprogrammieren wurde abgebrochen!" );
                attacker.sendMessage(Message.NORMAL.getPrefix() + "Du musst in einem Radium von 3 Blöcken bleiben!");
                stop();
            }
        } else {
            Bukkit.broadcastMessage(Message.NORMAL.getPrefix() + "Der Bordcomputer wurde umprogrammiert! Team " + attackerTeam.getChatColor() + attackerTeam.getFullName() + ChatColor.DARK_AQUA + "hat gewonnen!");
            new EndGameTask( 10 );
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
