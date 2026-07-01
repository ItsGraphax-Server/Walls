package de.itsgraphax.walls.misc.reactorCore;

import de.itsgraphax.walls.HasPlugin;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.*;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
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
    private static final Random random = new Random();

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
        int structureId = random.nextInt(1, 9);

        location.getBlock().setType(Material.AIR);

        Structure structure = plugin.getServer().getStructureManager().getStructure(plugin.namespaces().reactorCoreStructure(structureId));
        if (structure == null) {
            plugin.getComponentLogger().error("structure not found, is the datapack loaded?");
            return;
        }
        structure.place(location, true, StructureRotation.NONE, Mirror.NONE, -1, 0.75F, Random.from(RandomGenerator.getDefault()));
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent e) {
        if (!e.getItemInHand().equals(itemStack)) return;

        Location location = e.getBlock().getLocation().toCenterLocation();
        World world = location.getWorld();

        if (Math.abs(location.x()) < 10 || Math.abs(location.z()) < 10) {
            e.setCancelled(true);
            return;
        }

        world.spawnParticle(Particle.OMINOUS_SPAWNING, location, 255, 3, 1, 3);
        world.spawnParticle(Particle.PORTAL, location, 1000, 3, 1, 3);

        world.playSound(location, Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 1, 1);

        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                _ -> placeStructure(e.getBlock().getLocation().subtract(3, 4, 3)),
                50
        );
        plugin.getComponentLogger().info("nether reactor core placed by {} generated a structure.", e.getPlayer().name());
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasDiscoveredRecipe(plugin.namespaces().reactorCore())) {
            event.getPlayer().discoverRecipe(plugin.namespaces().reactorCore());
        }
    }

    @EventHandler
    void onBlockGrow(BlockGrowEvent e) {
        if (e.getBlock().getType() == Material.NETHER_WART) e.setCancelled(true);
    }


    static {
        itemStack = new ItemStack(Material.GRASS_BLOCK);
        itemStack.setData(DataComponentTypes.ITEM_NAME, rt.parse("Nether Reactor Core"));
        itemStack.setData(DataComponentTypes.ITEM_MODEL, Material.LIGHT.getKey());
        itemStack.setData(DataComponentTypes.MAX_STACK_SIZE, 1);

        for (int i = 0; i <= 8; i++) {
            plugin.getServer().getStructureManager().loadStructure(plugin.namespaces().reactorCoreStructure(i));
        }
    }
}
