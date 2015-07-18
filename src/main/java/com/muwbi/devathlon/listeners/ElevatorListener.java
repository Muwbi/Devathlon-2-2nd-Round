package com.muwbi.devathlon.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * Created by Gelox_.
 */
public class ElevatorListener implements Listener {

    @EventHandler
    public void onJump( PlayerToggleFlightEvent event ) {
        Player player = event.getPlayer();
        int yCoordinate = (int) player.getLocation().getY();
        int pitch = (int) player.getLocation().getPitch();

        if( pitch < 0 ) {
            for (int newY = yCoordinate; yCoordinate < 256; yCoordinate++) {
                Block block = player.getWorld().getBlockAt((int) player.getLocation().getX(), newY, (int) player.getLocation().getZ());

                if (block.getType() == Material.DIAMOND_BLOCK) {
                    player.teleport(block.getLocation().add(0, 1, 0));
                    player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 3);
                    player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 3);
                }
            }
        } else {
            for (int newY = yCoordinate; newY > 0; newY--) {
                Block block = player.getWorld().getBlockAt((int) player.getLocation().getX(), newY, (int) player.getLocation().getZ());

                if (block.getType() == Material.DIAMOND_BLOCK) {
                    player.teleport(block.getLocation().add(0, 1, 0));
                    player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 3);
                    player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 3);
                }
            }
        }

    }

}
