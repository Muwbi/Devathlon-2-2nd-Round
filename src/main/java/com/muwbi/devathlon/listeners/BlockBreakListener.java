package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Gelox_.
 */
public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak( BlockBreakEvent event ) {
        if( SpaceFighter.getInstance().getGameSession().getCurrentGameState() == GameState.INGAME || !event.getPlayer().isOp() ) {
            event.setCancelled( true );
        }
    }

}
