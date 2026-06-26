package de.itsgraphax.walls.phases.ready;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.phases.Phase;
import de.itsgraphax.walls.phases.PhaseDefinition;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Ready implements PhaseDefinition, HasPlugin {
    @Override
    public boolean isPhase() {
        return plugin.getPhase() == Phase.READY;
    }

    @Override
    public void setGamemode(Player p) {
        setGamemodeIfTeam(p, GameMode.ADVENTURE, GameMode.SPECTATOR);
    }
}
