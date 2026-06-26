package de.itsgraphax.walls.phases.pvp;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.GeneralMethods;
import de.itsgraphax.walls.phases.Phase;
import de.itsgraphax.walls.phases.PhaseDefinition;
import org.bukkit.GameMode;
import org.bukkit.GameRules;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Set;

public class Pvp implements PhaseDefinition, HasPlugin {
    @Override
    public void setGamemode(Player p) {
        setGamemodeIfTeam(p, GameMode.SURVIVAL, GameMode.SPECTATOR);
    }

    @Override
    public boolean isPhase() {
        return plugin.getPhase() == Phase.PVP;
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
    void onPlayerDeath(PlayerRespawnEvent e) {
        if (!isPhase()) return;

        Location deathLoc = e.getPlayer().getLocation().clone();
        e.setRespawnLocation(deathLoc);
        e.getPlayer().setGameMode(GameMode.SPECTATOR);

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 1);
        }
    }
}
