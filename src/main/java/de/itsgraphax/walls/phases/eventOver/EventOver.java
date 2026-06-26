package de.itsgraphax.walls.phases.eventOver;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.phases.PhaseDefinition;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class EventOver implements PhaseDefinition, HasPlugin {
    @Override
    public void setGamemode(Player p) {
        p.setGameMode(GameMode.SPECTATOR);
    }
}
