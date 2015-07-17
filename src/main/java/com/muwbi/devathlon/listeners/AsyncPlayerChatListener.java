package com.muwbi.devathlon.listeners;

        import com.muwbi.devathlon.SpaceFighter;
        import com.muwbi.devathlon.game.Team;
        import org.bukkit.Bukkit;
        import org.bukkit.ChatColor;
        import org.bukkit.event.EventHandler;
        import org.bukkit.event.Listener;
        import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Gelox_.
 */
public class AsyncPlayerChatListener implements Listener {


    @EventHandler
    public void onChat( AsyncPlayerChatEvent event ) {
        Team team = Team.getTeam( event.getPlayer().getUniqueId() );
        String internFormat = ChatColor.GRAY + "[" + team.getChatColor() + "Intern" + ChatColor.GRAY + "] " + ChatColor.GOLD + event.getPlayer().getName() + ChatColor.GRAY + ": " + ChatColor.WHITE;
        String globalFormat = ChatColor.GRAY + "[" + team.getChatColor() + "Global" + ChatColor.GRAY + "] " + ChatColor.GOLD + event.getPlayer().getName() + ChatColor.GRAY + ": " + ChatColor.WHITE;
        if( team != null ) {
            if( !event.getMessage().startsWith( "@a" ) ) {
                event.setCancelled( true );
                sendTeamMessage( team, internFormat + event.getMessage() );
            } else {
                event.setFormat( globalFormat + event.getMessage().trim().substring(2, event.getMessage().length()).trim() );
            }
        }
    }

    private void sendTeamMessage( Team team, String message ) {
        team.getMembers().forEach( uuid -> Bukkit.getPlayer( uuid ).sendMessage( message ) );
    }

}
