package com.muwbi.devathlon.game;

import lombok.Getter;
import org.bukkit.ChatColor;

/**
 * Created by Muwbi
 */
public enum Team {

    REBEL( "R", "Rebel", ChatColor.GREEN),
    IMPERIAL( "I", "Imperial", ChatColor.RED);


    @Getter
    private String shortName;
    @Getter
    private String fullName;
    @Getter
    private ChatColor chatColor;

    private Team( String shortName, String fullName, ChatColor chatColor ) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.chatColor = chatColor;
    }

}
