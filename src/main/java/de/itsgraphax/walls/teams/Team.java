package de.itsgraphax.walls.teams;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class Team {
    private final int teamId;
    private final org.bukkit.scoreboard.Team bukkitTeam;
    private final NamedTextColor teamColor;

    protected Team(TeamsManager teamsManager, int teamId) {
        org.bukkit.scoreboard.Team tempBukkitTeam;
        this.teamId = teamId;

        tempBukkitTeam = teamsManager.scoreboard.getTeam(String.valueOf(teamId));
        if (tempBukkitTeam == null) {
            tempBukkitTeam = teamsManager.scoreboard.registerNewTeam(String.valueOf(teamId));
        }

        this.bukkitTeam = tempBukkitTeam;
        this.teamColor = TeamsManager.getTeamTextColor(teamId);
        bukkitTeam.color(teamColor);
    }



    public int teamId() {
        return teamId;
    }

    public NamedTextColor teamColor() {
        return teamColor;
    }

    protected void addPlayer(Player player) {
        bukkitTeam.addPlayer(player);
    }

    protected void rmPlayer(Player player) {
        bukkitTeam.removePlayer(player);
    }
}
