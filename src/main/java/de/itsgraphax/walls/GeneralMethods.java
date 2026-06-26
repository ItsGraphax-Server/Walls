package de.itsgraphax.walls;

import de.itsgraphax.grphxLib.utils.LocationUtils;
import de.itsgraphax.walls.pdc.PdcData;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import org.joml.Vector2d;

import java.util.Collection;

public class GeneralMethods {
    public static void setDefaultWorldborder() {
        for (World w : WallsPlugin.instance().getServer().getWorlds()) {
            w.getWorldBorder().setSize(WallsPlugin.instance().getConfig().getInt("worldSize", 400) * 2);
        }
    }

    public static void setGamerules(Collection<GameRule<Boolean>> enable, Collection<GameRule<Boolean>> disable) {
        WallsPlugin plugin = WallsPlugin.instance();

        for (World w : plugin.getServer().getWorlds()) {
            for (GameRule<Boolean> gr : enable) w.setGameRule(gr, true);
            for (GameRule<Boolean> gr : disable) w.setGameRule(gr, false);
        }
    }

    private static int getWorldSize(WallsPlugin plugin) {
        return plugin.getConfig().getInt("worldSize", 400);
    }

    private static void buildWall(boolean xAxis, WallsPlugin plugin, Material type) {
        int worldSize = getWorldSize(plugin);
        World world = plugin.getServer().getRespawnWorld();

        for (int x = -worldSize; x < worldSize; x++) {
            for (int y = -63; y < 320; y++) {
                world.getBlockAt(xAxis ? x : 0, y, xAxis ? 0 : x).setType(type);
            }
        }
    }

    public static void buildWall(Material type) {
        WallsPlugin plugin = WallsPlugin.instance();

        BukkitScheduler s = plugin.getServer().getScheduler();
        s.runTaskLater(plugin, _ -> buildWall(true, plugin, type), 1);
        s.runTaskLater(plugin, _ -> buildWall(false, plugin, type), 2);
    }

    public static Vector2d getTeamLocMult(int teamI) {
        switch (teamI) {
            case 0 -> {
                return new Vector2d(1, 1);
            }
            case 1 -> {
                return new Vector2d(1, -1);
            }
            case 2 -> {
                return new Vector2d(-1, 1);
            }
            case 3 -> {
                return new Vector2d(-1, -1);
            }
            default -> throw new IllegalArgumentException();
        }
    }

    public static String getTeamColorMMString(int teamI) {
        return switch (teamI) {
            case 0 -> "red";
            case 1 -> "blue";
            case 2 -> "yellow";
            case 3 -> "green";
            default -> "dark_gray";
        };
    }

    public static boolean isInTeamArea(Vector v, Player p) {
        Integer team = PdcData.team(p);
        if (team == null) return true;

        int worldSize = getWorldSize(WallsPlugin.instance());

        Vector2d locMult = getTeamLocMult(team);
        Vector multipliedCoords = v.multiply(new Vector(locMult.x(), 1, locMult.y()));

        return multipliedCoords.isInAABB(
                new Vector(0, -999, 0),
                new Vector(worldSize + 1, 999, worldSize + 1)
        );
    }

    public static boolean isInTeamArea(Location l, Player p) {
        if (l.getWorld() != WallsPlugin.instance().getServer().getRespawnWorld())
            return true; // it is always true in the non-respawnworld
        return isInTeamArea(LocationUtils.locationToVector(p.getLocation()), p);
    }

    public static Location getHighestValidSpawnpoint(Location l) {
        int airabove = 0;
        for (int y = 319; y > -63; y--) {
            Location orr = l.clone();
            orr.setY(y);

            if (orr.getBlock().isSolid() && airabove >= 2) return orr;
            if (!orr.getBlock().isCollidable()) airabove += 1;

            else airabove = 0;
        }
        return l;
    }
}
