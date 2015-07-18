package com.muwbi.devathlon.scheduler;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.Message;
import com.muwbi.devathlon.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Gelox_.
 */
public class BoardComputerTask implements Runnable {

    private int countdown;
    private BukkitTask bukkitTask;
    private Team attackerTeam;
    private Location boardComputerLocation;
    private Player attacker;

    public BoardComputerTask( int seconds, Team attackerTeam, Location boardComputerLocation, Player attacker ) {
        countdown = seconds + 1;
        this.attackerTeam = attackerTeam;
        this.boardComputerLocation = boardComputerLocation;
        this.attacker = attacker;
    }

    @Override
    public void run() {
        countdown--;

        if ( countdown > 0 ) {
            if ( !( attacker.getLocation().distanceSquared( boardComputerLocation ) > 9 ) ) {
                if ( countdown % 5 == 0 || countdown < 5 ) {
                    attacker.sendMessage( Message.NORMAL.getPrefix() + ChatColor.RED + "Der Bordcomputer ist in " + ChatColor.GOLD + countdown + ChatColor.RED + " Sekunden entschärft!" );
                }
            } else {
                Bukkit.broadcastMessage( Message.ERROR.getPrefix() + "Das Umprogrammieren wurde abgebrochen!" );
                attacker.sendMessage( Message.NORMAL.getPrefix() + "Du musst in einem Radius von 3 Blöcken bleiben!" );
                stop();
            }
        } else {
            Bukkit.broadcastMessage( Message.NORMAL.getPrefix() + "Der Bordcomputer wurde umprogrammiert.\n " + ( attackerTeam == Team.IMPERIAL ? "Das " : "Die " ) + attackerTeam.getChatColor() + attackerTeam.getFullName() + ChatColor.DARK_AQUA + " hat gewonnen!" );
            new EndGameTask( 10 ).start();
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
