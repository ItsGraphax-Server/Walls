package de.itsgraphax.walls;

import de.itsgraphax.grphxLib.utils.DataManager;
import de.itsgraphax.walls.phases.Phase;
import org.bukkit.plugin.java.JavaPlugin;

public class WallsData extends DataManager {
    public WallsData(String path, JavaPlugin plugin) {
        super(path, plugin);
    }

    public Phase getPhase() {
        return Phase.valueOf(data.getString("phase"));
    }

    public boolean inPhase(Phase phase) {
        return getPhase() == phase;
    }

    public void setPhase(Phase phase) {
        data.set("phase", phase.name());
        save();
    }
}
