package com.muwbi.devathlon.objects;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.scheduler.LaserPistolTracker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Muwbi
 */
public class LaserPistol implements Listener {

    private static final Material ITEM_MATERIAL = Material.STONE_HOE;

    private static Map<UUID, LaserPistol> BY_UUID = new HashMap<>();

    private final UUID holder;

    private long lastShot = 0;

    private LaserPistol( UUID holder) {
        this.holder = holder;

        Bukkit.getPluginManager().registerEvents( this, SpaceFighter.getInstance() );
    }

    public ItemStack getItemStack() {
        return new ItemStack( ITEM_MATERIAL, 1 );
    }

    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        Player player = event.getPlayer();

        if ( event.hasItem() && ( event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR ) ) {
            if ( event.getItem().getType() == ITEM_MATERIAL ) {
                if ( holder == player.getUniqueId() ) {
                    if ( System.currentTimeMillis() - lastShot > 1000 ) {
                        lastShot = System.currentTimeMillis();

                        Egg egg = player.launchProjectile( Egg.class, player.getLocation().getDirection().normalize().multiply( 2 ) );

                        new LaserPistolTracker( egg, player ).start();
                    }
                }
            }
        }
    }

    public static LaserPistol getByUUID( UUID uuid ) {
        if ( !BY_UUID.containsKey( uuid ) ) {
            BY_UUID.put( uuid, new LaserPistol( uuid ) );
        }

        return BY_UUID.get( uuid );
    }

}
