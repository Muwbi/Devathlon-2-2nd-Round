package com.muwbi.devathlon.game;

import com.muwbi.devathlon.events.GameStateChangeEvent;
import lombok.Getter;
import org.bukkit.Bukkit;

/**
 * Created by Muwbi
 */
public class GameSession {

    @Getter
    private GameState currentGameState;

    public void nextGameState() {
        int index = 0;
        for ( int i = 0; i < GameState.values().length; i++ ) {
            if ( GameState.values()[i] == currentGameState ) {
                index = i;
            }
        }

        GameState nextGameState;
        if ( index >= GameState.values().length ) {
            index = 0;
        } else {
            index++;
        }

        changeGameState( GameState.values()[index] );
    }

    public void changeGameState( GameState gameState ) {
        Bukkit.getPluginManager().callEvent( new GameStateChangeEvent( currentGameState, gameState ) );

        currentGameState = gameState;
    }

}
