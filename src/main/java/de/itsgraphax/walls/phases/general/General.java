package de.itsgraphax.walls.phases.general;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.GeneralMethods;
import de.itsgraphax.walls.phases.Phase;
import de.itsgraphax.walls.phases.PhaseDefinition;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.GameMode;
import org.bukkit.GameRules;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.Set;

public class General implements PhaseDefinition, HasPlugin {
    @Override
    public void onStart() {
        Phase currentPhase = plugin.getPhase();

        // (Not) ingame
        if (Set.of(Phase.NOTREADY,
                Phase.READY,
                Phase.FINISHED,
                Phase.EVENTOVER).contains(currentPhase)) {
            GeneralMethods.setGamerules(Set.of(

            ), Set.of(
                    GameRules.ADVANCE_TIME,
                    GameRules.ADVANCE_WEATHER,
                    GameRules.SPAWN_MOBS,
                    GameRules.LOCATOR_BAR,
                    GameRules.PVP
            ));
        }
        else {
            GeneralMethods.setGamerules(Set.of(
                    GameRules.ADVANCE_TIME,
                    GameRules.ADVANCE_WEATHER,
                    GameRules.SPAWN_MOBS,
                    GameRules.PVP
            ), Set.of(

            ));
        }

        if (currentPhase != Phase.PVP) {
            GeneralMethods.setDefaultWorldborder();
            GeneralMethods.buildWall(Material.BEDROCK);
        }
        else {
            GeneralMethods.buildWall(Material.AIR);
        }
    }

    @EventHandler
    void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.SPECTATOR) return;

        String root = e.getMessage().split(" ")[0];
        List<String> blockeds = plugin.getConfig().getStringList("blockedSpectatorCommands");

        if (blockeds.contains(root)) e.setCancelled(true);
    }

    @EventHandler
    void onChat(AsyncChatEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.SPECTATOR) return;

        e.setCancelled(true);
    }
}
