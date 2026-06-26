package de.itsgraphax.walls.phases.finished;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.phases.Phase;
import de.itsgraphax.walls.phases.PhaseDefinition;
import org.bukkit.entity.Player;

public class Finished implements PhaseDefinition, HasPlugin {
    @Override
    public boolean isPhase() {
        return plugin.getPhase() == Phase.FINISHED;
    }

    @Override
    public void setGamemode(Player p) {

    }
}
