package com.muwbi.devathlon;

import com.google.common.reflect.ClassPath;
import com.muwbi.devathlon.commands.GameStateChangeCommand;
import com.muwbi.devathlon.game.GameSession;
import com.muwbi.devathlon.objects.Beamer;
import com.muwbi.devathlon.objects.BoardComputer;
import com.muwbi.devathlon.objects.SpaceCannon;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Created by Muwbi
 */
public class SpaceFighter extends JavaPlugin {

    @Getter
    private static SpaceFighter instance;

    @Getter
    private GameSession gameSession;

    @Override
    public void onEnable() {
        instance = this;

        gameSession = new GameSession();

        registerListeners();
        registerCommands();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        try {
            for ( ClassPath.ClassInfo classInfo : ClassPath.from( getClassLoader() ).getTopLevelClasses( "com.muwbi.devathlon.listeners" ) ) {
                Class clazz = Class.forName( classInfo.getName() );

                if ( Listener.class.isAssignableFrom( clazz ) ) {
                    pluginManager.registerEvents( (Listener) clazz.newInstance(), this );
                }
            }
        } catch ( IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e ) {
            e.printStackTrace();
        }

        pluginManager.registerEvents( new SpaceCannon(), this );
        pluginManager.registerEvents( new Beamer(), this );
        pluginManager.registerEvents( new BoardComputer(), this);
    }

    private void registerCommands() {
        getCommand( "gamestate" ).setExecutor( new GameStateChangeCommand() );
    }

}
