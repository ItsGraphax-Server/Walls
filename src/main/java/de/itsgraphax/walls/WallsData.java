package de.itsgraphax.walls;

import de.itsgraphax.grphxLib.utils.DataManager;
import de.itsgraphax.walls.phases.Phase;
import de.itsgraphax.walls.teams.Team;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class WallsData extends DataManager {
    public WallsData(String path, JavaPlugin plugin) {
        super(path, plugin);
    }

    public Phase getPhase() {
        return Phase.valueOf(data.getString("phase"));
    }

    public void setPhase(Phase phase) {
        data.set("phase", phase.name());
        save();
    }

    public void setTeam(Player player, Team team) {
        try {
            @SuppressWarnings("unchecked") List<String> oldTeam = (List<String>) data.getList(String.format("team.%s",  team.teamId()));
            assert oldTeam != null;
            oldTeam.add(player.getUniqueId().toString());
            data.set(String.format("team.%s", team.teamId()), oldTeam);
            save();
        } catch (ClassCastException | AssertionError e) {
            plugin.getComponentLogger().error("the data for team {} is incorrect", team.teamId());
        }
    }

    public void rmTeam(Player player, Team team) {
        try {
            @SuppressWarnings("unchecked") List<String> oldTeam = (List<String>) data.getList(String.format("team.%s",  team.teamId()));
            assert oldTeam != null;
            oldTeam.remove(player.getUniqueId().toString());
            data.set(String.format("team.%s", team.teamId()), oldTeam);
            save();
        } catch (ClassCastException | AssertionError e) {
            plugin.getComponentLogger().error("the data for team {} is incorrect", team.teamId());
        }
    }


    public @Nullable Integer getTeamId(Player player) {
        String uuidString = player.getUniqueId().toString();
        try {
            for (int i = 0; i <= 3; i++) {
                @SuppressWarnings("unchecked")
                List<String> teamList = (List<String>) data.getList(String.format("team.%s", i));
                if (teamList == null) {
                    plugin.getComponentLogger().warn("Team {} is null", i);
                    continue;
                }
                for (String teamPlayerUuid : teamList) {
                    if (Objects.equals(uuidString, teamPlayerUuid)) {
                        return i;
                    }
                }
            }
        } catch (ClassCastException | AssertionError e) {
            plugin.getComponentLogger().error("the data for a team is incorrect");
        }
        return null;
    }
}
