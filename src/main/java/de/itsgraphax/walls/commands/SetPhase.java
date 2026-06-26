package de.itsgraphax.walls.commands;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.phases.Phase;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.RequiresOP;
import org.bukkit.command.CommandSender;

@Command("setphase")
@RequiresOP
public class SetPhase implements HasPlugin {
    @Executes
    void onExec(CommandSender s, String phaseString) {
        Phase phase = null;
        try {
            phase = Phase.valueOf(phaseString);
        } catch (IllegalArgumentException e) {
            s.sendMessage("that phasestring wasnt found");
        }

        assert phase != null;
        plugin.setPhase(phase);

        s.sendMessage("set the phase successfully");
    }
}
