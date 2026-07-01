package de.itsgraphax.walls.pdc;

import de.itsgraphax.grphxLib.utils.NamespacesBase;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Namespaces extends NamespacesBase {
    public Namespaces(JavaPlugin plugin) {
        super(plugin);
    }

    public NamespacedKey enderPearlThrownTime() {
        return key("enderPearlThrownTime");
    }

    public NamespacedKey reactorCoreStructure(int id) {
        return key(String.format("reactorcore/%s", id));
    }

    public NamespacedKey reactorCore() {
        return key("reactorCore");
    }
}
