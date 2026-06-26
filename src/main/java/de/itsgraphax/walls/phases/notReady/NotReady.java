package de.itsgraphax.walls.phases.notReady;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.phases.PhaseDefinition;

public class NotReady implements PhaseDefinition, HasPlugin {
    @Override
    public void onStart() {
        plugin.getServer().setWhitelist(true);
    }

    @Override
    public void onEnd() {
        plugin.getServer().setWhitelist(false);
    }
}
