package com.muwbi.devathlon.commands;

import com.muwbi.devathlon.SpaceFighter;
import com.muwbi.devathlon.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Gelox_.
 */
public class GameStateChangeCommand implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] arguments ) {

        if( arguments.length == 1 ) {
            SpaceFighter.getInstance().getGameSession().changeGameState(GameState.valueOf(arguments[0]));
        } else {
            return true;
        }

        return true;
    }
}
