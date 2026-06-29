package de.itsgraphax.walls.phases;

import de.itsgraphax.walls.WallsPlugin;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public interface PhaseDefinition extends Listener {
    /**
     * Checks if the current phase is the correct one
     */
    boolean isPhase();

    default void tick() {
    }

    default void onStart() {
        for (Player p : WallsPlugin.instance().getServer().getOnlinePlayers()) setGamemode(p);
    }

    @EventHandler
    default void onJoin(PlayerJoinEvent e) {
        if (isPhase()) setGamemode(e.getPlayer());
    }

    default void onEnd() {
    }

    default void setGamemodeIfTeam(Player p, GameMode ifTeam, GameMode elseTeam) {
        if (WallsPlugin.instance().teamsManager().getTeam(p) != null) p.setGameMode(ifTeam);
        else p.setGameMode(elseTeam);
    }

    void setGamemode(Player p);
}
