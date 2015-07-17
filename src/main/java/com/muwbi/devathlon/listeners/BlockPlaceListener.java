package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Gelox_.
 */
public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace( BlockPlaceEvent event ) {
        if( SpaceFighter.getInstance().getGameSession().getCurrentGameState() == GameState.INGAME || !event.getPlayer().isOp() ) {
            event.setCancelled( true );
        }
    }
}
