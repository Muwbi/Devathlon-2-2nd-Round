package com.muwbi.devathlon.listeners;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.events.GameStateChangeEvent;
import com.muwbi.devathlon.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Muwbi
 */
public class GameStateChangeListener implements Listener {

    @EventHandler
    public void onGameStateChange( GameStateChangeEvent event ) {
        if ( event.getPreviousGameState() == GameState.LOBBY && event.getNewGameState() == GameState.WARMUP ) {
            Bukkit.getOnlinePlayers().forEach( player -> player.setScoreboard( SpaceFighter.getInstance().getGameSession().getScoreboard() ) );
            SpaceFighter.getInstance().getGameSession().startGame();
        }
    }

}
