package de.itsgraphax.walls.misc;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.pdc.PdcData;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.time.Duration;
import java.time.LocalDateTime;

public class Enderpearls implements Listener, HasPlugin {
    @EventHandler
    void onProjectileLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity().getType() != EntityType.ENDER_PEARL) return;
        EnderPearl entity = (EnderPearl) e.getEntity();

        PdcData.enderPearlThrownTime(entity, LocalDateTime.now());
    }

    @EventHandler
    void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity().getType() != EntityType.ENDER_PEARL) return;
        EnderPearl entity = (EnderPearl) e.getEntity();

        LocalDateTime thrownTime = PdcData.enderPearlThrownTime(entity);
        if (Duration.between(thrownTime, LocalDateTime.now()).toSeconds() > 30) {
            entity.remove();
            e.setCancelled(true);
        }
    }
}
