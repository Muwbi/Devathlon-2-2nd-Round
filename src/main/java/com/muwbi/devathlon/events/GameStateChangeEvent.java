package com.muwbi.devathlon.events;

import com.muwbi.devathlon.game.GameState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Muwbi
 */
@RequiredArgsConstructor
@Getter
public class GameStateChangeEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private final GameState previousGameState;
    private final GameState newGameState;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
