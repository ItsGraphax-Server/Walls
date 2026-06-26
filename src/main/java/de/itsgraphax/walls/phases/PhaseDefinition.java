package de.itsgraphax.walls.phases;

import de.itsgraphax.walls.WallsPlugin;
import de.itsgraphax.walls.pdc.PdcData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public interface PhaseDefinition extends Listener {
    default void tick() {
    }

    default void onStart() {
        for (Player p : WallsPlugin.instance().getServer().getOnlinePlayers()) setGamemode(p);
    }

    @EventHandler
    default void onJoin(PlayerJoinEvent e) {
        setGamemode(e.getPlayer());
    }

    default void onEnd() {
    }

    default void setGamemodeIfTeam(Player p, GameMode ifTeam, GameMode elseTeam) {
        if (PdcData.team(p) != null) p.setGameMode(ifTeam);
        else p.setGameMode(elseTeam);
    }

    default void setGamemode(Player p) {
    }
}
