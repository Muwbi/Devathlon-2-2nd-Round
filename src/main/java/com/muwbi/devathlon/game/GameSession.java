package com.muwbi.devathlon.game;

import com.muwbi.devathlon.events.GameStateChangeEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Muwbi
 */
public class GameSession {

    @Getter
    private GameState currentGameState;

    @Getter
    private Scoreboard scoreboard;

    @Getter
    private Map<Team, Score> scores = new HashMap<>();

    public GameSession() {
        currentGameState = GameState.LOBBY;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective scoreObjective = scoreboard.registerNewObjective( "score", "dummy" );
        scoreObjective.setDisplayName( ChatColor.DARK_RED + "Kraftpunkte" );
        scoreObjective.setDisplaySlot( DisplaySlot.SIDEBAR );

        Score imperialScore = scoreObjective.getScore( ChatColor.RED + "Imperium" );
        Score rebelScore = scoreObjective.getScore( ChatColor.GREEN + "Rebellen" );

        imperialScore.setScore( 10000 );
        rebelScore.setScore( 10000 );

        scores.put( Team.IMPERIAL, imperialScore );
        scores.put( Team.REBEL, rebelScore );
    }

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

    public void startGame() {
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            Team team = Team.getLessPopulatedTeam();
            team.addMember( player.getUniqueId() );

            player.sendMessage( Message.NORMAL.getPrefix() + ChatColor.DARK_AQUA + "Du bist " + ( team == Team.IMPERIAL ? "dem " : "den " ) + team.getChatColor() + team.getFullName() + ChatColor.DARK_AQUA + " beigetreten" );

            player.teleport( team.getSpawnLocation() );
        }
    }

    public void endGame() {

        for ( Player player : Bukkit.getOnlinePlayers() ) {
            player.getInventory().setArmorContents( null );
            player.getInventory().clear();
            player.kickPlayer( Message.ERROR.getPrefix() + "Der Server fährt nun herunter!" );
        }

        Bukkit.shutdown();
    }

    public void sendTeamMessage( Team team, String message ) {
        team.getMembers().forEach( uuid -> Bukkit.getPlayer( uuid ).sendMessage( message ) );
    }

}
