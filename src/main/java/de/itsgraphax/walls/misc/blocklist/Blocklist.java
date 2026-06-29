package de.itsgraphax.walls.misc.blocklist;

import de.itsgraphax.walls.HasPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Blocklist implements Listener, HasPlugin {
    private boolean isNotBlocked(@NotNull ItemStack i) {
        List<String> blocklist = plugin.getConfig().getStringList("blocklist");
        Material resultType = i.getType();
        return !blocklist.contains(resultType.name());
    }

    @EventHandler
    void onCraftItem(@NotNull CraftItemEvent e) {
        if (isNotBlocked(e.getRecipe().getResult())) return;
        
        e.setCancelled(true);
        e.getView().getPlayer().sendMessage(rt.fromConfig("blocklist.craftNotAllowed"));
    }

    @EventHandler
    void onCrafterCraft(@NotNull CrafterCraftEvent e) {
        if (isNotBlocked(e.getRecipe().getResult())) return;

        e.setCancelled(true);
    }

    @EventHandler
    void onItemSpawn(@NotNull ItemSpawnEvent e) {
        if (isNotBlocked(e.getEntity().getItemStack())) return;

        e.setCancelled(true);
    }
}
