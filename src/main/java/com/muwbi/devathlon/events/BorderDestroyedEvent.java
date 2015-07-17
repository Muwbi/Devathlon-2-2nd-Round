package com.muwbi.devathlon.events;

import com.muwbi.devathlon.game.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Muwbi
 */
@RequiredArgsConstructor
public class BorderDestroyedEvent extends Event {

    @Getter
    private static HandlerList handlerList = new HandlerList();

    @Getter
    private final Team team;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
