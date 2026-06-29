package de.itsgraphax.walls.teams;

import org.bukkit.entity.Player;

public class Team {
    private final int teamId;
    private final org.bukkit.scoreboard.Team bukkitTeam;

    protected Team(TeamsManager teamsManager, int teamId) {
        org.bukkit.scoreboard.Team tempBukkitTeam;
        this.teamId = teamId;

        tempBukkitTeam = teamsManager.scoreboard.getTeam(String.valueOf(teamId));
        if (tempBukkitTeam == null) {
            tempBukkitTeam = teamsManager.scoreboard.registerNewTeam(String.valueOf(teamId));
        }

        this.bukkitTeam = tempBukkitTeam;
        bukkitTeam.color(TeamsManager.getTeamTextColor(teamId));
    }



    public int teamId() {
        return teamId;
    }

    protected void addPlayer(Player player) {
        bukkitTeam.addPlayer(player);
    }

    protected void rmPlayer(Player player) {
        bukkitTeam.removePlayer(player);
    }
}
