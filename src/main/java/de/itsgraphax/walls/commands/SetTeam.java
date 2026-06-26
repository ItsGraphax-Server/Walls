package de.itsgraphax.walls.commands;

import de.itsgraphax.walls.GeneralMethods;
import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.pdc.PdcData;
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
        PdcData.team(p, team);

        p.displayName(rt.parse(String.format("<%s>%s", GeneralMethods.getTeamColorMMString(-1), p.getName())));

        s.sendMessage("set the team for the player");
    }

    @Executes
    void onExec(CommandSender s, Player p) {
        PdcData.team(p, null);

        p.displayName(rt.parse(String.format("<%s>%s", GeneralMethods.getTeamColorMMString(-1), p.getName())));

        s.sendMessage("removed the team for the player");
    }
}
