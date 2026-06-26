package de.itsgraphax.walls.phases.pvp;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.GeneralMethods;
import de.itsgraphax.walls.phases.Phase;
import de.itsgraphax.walls.phases.PhaseDefinition;
import org.bukkit.GameMode;
import org.bukkit.GameRules;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Set;

public class Pvp implements PhaseDefinition, HasPlugin {
    @Override
    public void setGamemode(Player p) {
        setGamemodeIfTeam(p, GameMode.SURVIVAL, GameMode.SPECTATOR);
    }

    @Override
    public void onStart() {
        GeneralMethods.setGamerules(Set.of(
                GameRules.ALLOW_ENTERING_NETHER_USING_PORTALS,
                GameRules.LOCATOR_BAR,
                GameRules.IMMEDIATE_RESPAWN
        ), Set.of(

        ));
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent e) {
        if (plugin.getPhase() != Phase.PVP) return;

        Location deathLoc = e.getPlayer().getLocation().clone();
        plugin.getServer().getScheduler().runTaskLater(plugin, _ ->
                e.getPlayer().teleport(deathLoc, PlayerTeleportEvent.TeleportCause.PLUGIN), 1);
        e.getPlayer().setGameMode(GameMode.SPECTATOR);

        // TODO: Play wither death sound
    }
}
