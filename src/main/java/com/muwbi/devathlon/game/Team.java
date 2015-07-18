package com.muwbi.devathlon.game;

import com.muwbi.devathlon.objects.SpaceCannon;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Muwbi
 */
public enum Team {

    REBEL( "R",
            "Rebellen",
            ChatColor.GREEN,
            3000,
            false,
            true,
            new Location( Bukkit.getWorld( "Spacefighter" ), 44, 58, -224 ),
            new Location( Bukkit.getWorld( "Spacefighter" ), 24, 50, -223 ),
            Bukkit.getWorld( "Spacefighter" ).getBlockAt( 35, 57, -219 ),
            new Location( Bukkit.getWorld( "Spacefighter" ), 17, 49, -91 ) ),

    IMPERIAL( "I",
            "Imperium",
            ChatColor.RED,
            3000,
            false,
            true,
            new Location( Bukkit.getWorld( "Spacefighter" ), 0, 57, -75 ),
            new Location( Bukkit.getWorld( "Spacefighter" ), 19, 49, -77 ),
            Bukkit.getWorld( "Spacefighter" ).getBlockAt( 8, 56, -81 ),
            new Location( Bukkit.getWorld( "Spacefighter" ), 26, 50, -209 ) );

    @Getter
    private String shortName;

    @Getter
    private String fullName;

    @Getter
    private ChatColor chatColor;

    @Getter
    private int hitPoints;

    @Getter
    private boolean attackingBoardComputer;

    @Getter
    private boolean borderActive;

    @Getter
    private Location spawnLocation;

    @Getter
    private Location beamLocation;

    @Getter
    private Block boardComputer;

    @Getter
    private Location spaceCannonLocation;

    @Getter
    private final List<UUID> members = new ArrayList<>();

    private Team( String shortName, String fullName, ChatColor chatColor, int hitPoints, boolean attackingBoardComputer, boolean borderActive, Location spawnLocation, Location beamLocation, Block boardComputer, Location spaceCannonLocation ) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.chatColor = chatColor;
        this.hitPoints = hitPoints;
        this.attackingBoardComputer = attackingBoardComputer;
        this.borderActive = borderActive;
        this.spawnLocation = spawnLocation;
        this.beamLocation = beamLocation;
        this.boardComputer = boardComputer;
        this.spaceCannonLocation = spaceCannonLocation;
    }

    public static Team getTeam( UUID uuid ) {
        return Team.IMPERIAL.getMembers().contains( uuid ) ? Team.IMPERIAL : Team.REBEL.getMembers().contains( uuid ) ? Team.REBEL : null;
    }

    public static Team getLessPopulatedTeam() {
        return Team.REBEL.getMembers().size() < Team.IMPERIAL.getMembers().size() ? Team.REBEL : Team.IMPERIAL;
    }

    public static boolean hasTeam( UUID uuid ) {
        return Team.IMPERIAL.getMembers().contains( uuid ) || Team.REBEL.getMembers().contains( uuid );
    }

    public boolean addMember( UUID uuid ) {
        if ( members.contains( uuid ) ) {
            return false;
        }

        members.add( uuid );

        return true;
    }

    public Team getOtherTeam() {
        return this == Team.IMPERIAL ? Team.REBEL : Team.IMPERIAL;
    }

    public boolean removeMember( UUID uuid ) {
        if ( !members.contains( uuid ) ) {
            return false;
        }

        members.remove( uuid );

        return true;
    }

}
