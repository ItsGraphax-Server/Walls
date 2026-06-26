package de.itsgraphax.walls.phases.gearUp;

import de.itsgraphax.walls.GeneralMethods;
import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.pdc.PdcData;
import de.itsgraphax.walls.phases.Phase;
import de.itsgraphax.walls.phases.PhaseDefinition;
import org.bukkit.GameMode;
import org.bukkit.GameRules;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.joml.Vector2d;

import java.util.Set;

public class GearUp implements PhaseDefinition, HasPlugin {
    @Override
    public void setGamemode(Player p) {
        setGamemodeIfTeam(p, GameMode.SURVIVAL, GameMode.CREATIVE);
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
        if (plugin.getPhase() != Phase.GEARUP) return;

        if (!GeneralMethods.isInTeamArea(e.getTo(), e.getPlayer())) {
            Integer team = PdcData.team(e.getPlayer());
            assert team != null;
            Vector2d teamMult = GeneralMethods.getTeamLocMult(team);
            e.getPlayer().teleport(new Location(e.getFrom().getWorld(), 100 * teamMult.x(), 90, 100 * teamMult.y()),
                    PlayerTeleportEvent.TeleportCause.PLUGIN);

        }
    }
}
