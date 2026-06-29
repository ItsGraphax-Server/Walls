package de.itsgraphax.walls.commands;

import de.itsgraphax.walls.HasPlugin;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.arguments.IntArg;
import net.strokkur.commands.paper.RequiresOP;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("setteam")
@RequiresOP
public class SetTeam implements HasPlugin {
    @Executes
    void onExec(CommandSender s, Player p, @IntArg(min=0,max=3) int team) {
        plugin.teamsManager().rmTeam(p);
        plugin.teamsManager().setTeam(p, team);

        plugin.getPhase().getDef().setGamemode(p);

        s.sendMessage("set the team for the player");
    }

    @Executes
    void onExec(CommandSender s, Player p) {
        plugin.teamsManager().rmTeam(p);

        plugin.getPhase().getDef().setGamemode(p);

        s.sendMessage("removed the team for the player");
    }
}
