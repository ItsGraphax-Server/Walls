package de.itsgraphax.walls.teams;

import de.itsgraphax.walls.HasPlugin;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TeamsManager implements Listener, HasPlugin {
    protected final Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
    private final List<Team> teams = new ArrayList<>();

    public TeamsManager() {
        for (int i = 0; i <= 3; i++) {
            teams.add(
                    new Team(this, i)
            );
        }
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(scoreboard);
        Team team = getTeam(event.getPlayer());
        if (team == null) return;
        event.getPlayer().displayName(
                rt.parse(String.format(
                        "<%s>%s",
                        team.teamColor().toString().toLowerCase(),
                        event.getPlayer().getName()
                ))
        );
    }

    public void setTeam(Player player, int teamId) {
        setTeam(player, teams.get(teamId));
    }

    public void setTeam(Player player, Team team) {
        if (getTeam(player) != null) {
            plugin.getComponentLogger().error("player {} is already in a team!", player.getName());
            return;
        }
        rmTeam(player);
        plugin.dataManager().setTeam(player, team);
        team.addPlayer(player);

        player.displayName(
                rt.parse(String.format(
                        "<%s>%s",
                        team.teamColor().toString().toLowerCase(),
                        player.getName()
                ))
        );
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
            case 2 -> NamedTextColor.GREEN;
            case 3 -> NamedTextColor.YELLOW;
            default -> null;
        };
    }


}
