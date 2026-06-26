package de.itsgraphax.walls.phases.notReady;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.phases.Phase;
import de.itsgraphax.walls.phases.PhaseDefinition;
import org.bukkit.entity.Player;

public class NotReady implements PhaseDefinition, HasPlugin {
    @Override
    public boolean isPhase() {
        return plugin.getPhase() == Phase.NOTREADY;
    }

    @Override
    public void onStart() {
        plugin.getServer().setWhitelist(true);
    }

    @Override
    public void onEnd() {
        plugin.getServer().setWhitelist(false);
    }

    @Override
    public void setGamemode(Player p) {

    }
}
