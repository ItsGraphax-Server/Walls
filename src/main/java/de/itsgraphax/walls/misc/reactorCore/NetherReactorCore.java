package de.itsgraphax.walls.misc.reactorCore;

import de.itsgraphax.walls.HasPlugin;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.structure.Structure;

import java.util.Random;
import java.util.random.RandomGenerator;


@SuppressWarnings("UnstableApiUsage")
public class NetherReactorCore implements Listener, HasPlugin {
    private static final ItemStack itemStack;

    public NetherReactorCore() {}

    public static ItemStack getItemStack() {
        return itemStack.clone();
    }

    public static void registerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(plugin.namespaces().reactorCore(), getItemStack());
        recipe.shape("ogo", "oeo", "ooo");
        recipe.setIngredient('o', Material.CRYING_OBSIDIAN);
        recipe.setIngredient('g', Material.GOLD_BLOCK);
        recipe.setIngredient('e', Material.ENDER_PEARL);

        plugin.getServer().addRecipe(recipe);
    }

    private void placeStructure(Location location) {
        Structure structure = plugin.getServer().getStructureManager().getStructure(plugin.namespaces().reactorCoreStructure(0));
        if (structure == null) {
            plugin.getComponentLogger().error("structure not found, is the datapack loaded?");
            return;
        }
        structure.place(location, true, StructureRotation.NONE, Mirror.NONE, -1, 0.75F, Random.from(RandomGenerator.getDefault()));
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getItemInHand().equals(itemStack)) return;
        plugin.getServer().getRespawnWorld().spawnParticle(Particle.OMINOUS_SPAWNING, event.getBlock().getLocation().toCenterLocation(), 255, 3, 1 ,3);
        plugin.getServer().getRespawnWorld().spawnParticle(Particle.PORTAL, event.getBlock().getLocation().toCenterLocation(), 1000, 3, 1,3);


        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                _ -> placeStructure(event.getBlock().getLocation().subtract(3, 5, 3)),
                50
        );
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasDiscoveredRecipe(plugin.namespaces().reactorCore())) {
            event.getPlayer().discoverRecipe(plugin.namespaces().reactorCore());
        }
    }



    static {
        itemStack = new ItemStack(Material.GRASS_BLOCK);
        itemStack.setData(DataComponentTypes.ITEM_NAME, rt.parse("Nether Reactor Core"));
        itemStack.setData(DataComponentTypes.ITEM_MODEL, Material.LIGHT.getKey());
        itemStack.setData(DataComponentTypes.MAX_STACK_SIZE, 1);

        plugin.getServer().getStructureManager().loadStructure(plugin.namespaces().reactorCoreStructure(0));
    }
}
