package de.itsgraphax.walls.commands;

import de.itsgraphax.walls.WallsPlugin;
import de.itsgraphax.walls.teams.Team;
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
        Team team = WallsPlugin.instance().teamsManager().getTeam(p);
        s.sendMessage(team != null ? String.valueOf(team.teamId()) : "none");
    }
}
