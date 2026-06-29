package de.itsgraphax.walls.teams;

import de.itsgraphax.walls.HasPlugin;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TeamsManager implements Listener, HasPlugin {
    protected final Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
    private final List<Team> teams = new ArrayList<>();

    public TeamsManager() {
        for (int i=0; i< 4; i ++) {
            teams.add(
                    new Team(this, i)
            );
        }
    }

    public void setTeam(Player player, int teamId) {
       setTeam(player, teams.get(teamId));
    }

    public void setTeam(Player player, Team team) {
        if (getTeam(player) != null) {
            plugin.getComponentLogger().error("player {} is already in a team!", player.getName());
            return;
        }
        plugin.dataManager().setTeam(player, team);
        team.addPlayer(player);
    }

    public void rmTeam(Player player) {
        Team team = getTeam(player);
        if (team == null) {
            plugin.getComponentLogger().warn("player {} isn't in a team!", player.getName());
            return;
        }
        team.rmPlayer(player);
        plugin.dataManager().rmTeam(player, team);
    }

    public @Nullable Team getTeam(Player player) {
        Integer teamId = plugin.dataManager().getTeamId(player);
        if (teamId == null) return null;
        return teams.get(teamId);
    }

    public static NamedTextColor getTeamTextColor(int teamI) {
        return switch (teamI) {
            case 0 -> NamedTextColor.RED;
            case 1 -> NamedTextColor.BLUE;
            case 2 -> NamedTextColor.YELLOW;
            case 3 -> NamedTextColor.GREEN;
            default -> null;
        };
    }


}
