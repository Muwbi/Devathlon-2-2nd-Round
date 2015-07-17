package com.muwbi.devathlon.game;

import lombok.Getter;
import org.bukkit.ChatColor;

/**
 * Created by Gelox_.
 */
public enum Message {

    NORMAL( ChatColor.GRAY + "[" + ChatColor.GOLD + "SpaceFighter" + ChatColor.GRAY + "] " + ChatColor.DARK_AQUA ),
    ERROR( ChatColor.RED + "[" + ChatColor.DARK_RED + "!" + ChatColor.RED + "] " );

    @Getter
    private String prefix;

    private Message( String prefix ) {
        this.prefix = prefix;
    }
}
