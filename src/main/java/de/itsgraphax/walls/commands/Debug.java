package de.itsgraphax.walls.commands;

import de.itsgraphax.walls.WallsPlugin;
import de.itsgraphax.walls.pdc.PdcData;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.RequiresOP;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("wallsdebug")
@RequiresOP
public class Debug {
    @Executes("currentphase")
    void currentPhase(CommandSender s) {
        s.sendMessage(WallsPlugin.instance().getPhase().name());
    }

    @Executes("getteam")
    void getTeam(CommandSender s, Player p) {
        Integer team = PdcData.team(p);
        s.sendMessage(team != null ? String.valueOf(team) : "none");
    }
}
