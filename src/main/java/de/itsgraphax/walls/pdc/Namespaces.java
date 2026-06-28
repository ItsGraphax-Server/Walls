package de.itsgraphax.walls.pdc;

import de.itsgraphax.grphxLib.utils.NamespacesBase;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Namespaces extends NamespacesBase {
    public Namespaces(JavaPlugin plugin) {
        super(plugin);
    }

    public NamespacedKey team() {
        return key("team");
    }
    public NamespacedKey lastJoinedPhase() {
        return key("lastJoinedPhase");
    }
    public NamespacedKey enderPearlThrownTime() {
        return key("enderPearlThrownTime");
    }
}
