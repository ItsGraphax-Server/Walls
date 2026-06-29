package de.itsgraphax.walls.phases.gearUp;

import de.itsgraphax.walls.GeneralMethods;
import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.phases.Phase;
import de.itsgraphax.walls.phases.PhaseDefinition;
import de.itsgraphax.walls.teams.Team;
import org.bukkit.GameMode;
import org.bukkit.GameRules;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.joml.Vector2d;

import java.util.Set;

public class GearUp implements PhaseDefinition, HasPlugin {
    @Override
    public void setGamemode(Player p) {
        setGamemodeIfTeam(p, GameMode.SURVIVAL, GameMode.CREATIVE);
    }

    @Override
    public boolean isPhase() {
        return plugin.getPhase() == Phase.GEARUP;
    }

    @Override
    public void onStart() {
        GeneralMethods.setGamerules(Set.of(

        ), Set.of(
                GameRules.ALLOW_ENTERING_NETHER_USING_PORTALS,
                GameRules.LOCATOR_BAR,
                GameRules.IMMEDIATE_RESPAWN
        ));
    }

    @EventHandler
    void onPlayerMove(PlayerMoveEvent e) {
        if (!isPhase()) return;

        if (!GeneralMethods.isInTeamArea(e.getTo(), e.getPlayer())) {
            Team team = plugin.teamsManager().getTeam(e.getPlayer());
            assert team != null;

            Vector2d teamMult = GeneralMethods.getTeamLocMult(team.teamId());
            int halfWorldSize = plugin.getConfig().getInt("worldSize", 400) / 2;

            Location tpLoc = new Location(e.getFrom().getWorld(), halfWorldSize * teamMult.x(), 90, halfWorldSize * teamMult.y());
            tpLoc = GeneralMethods.getHighestValidSpawnpoint(tpLoc).add(0, 1, 0);
            e.getPlayer().teleport(tpLoc, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent e) {
        if (!isPhase()) return;

        if (e.isBedSpawn()) return;

        e.setRespawnLocation(GeneralMethods.getHighestValidSpawnpoint(e.getRespawnLocation()).add(0, 1, 0));
    }
}
