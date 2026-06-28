package de.itsgraphax.walls.commands;

import de.itsgraphax.walls.HasPlugin;
import de.itsgraphax.walls.phases.Phase;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.RequiresOP;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

@Command("setphasecountdown")
@RequiresOP
public class PhaseCountdown implements HasPlugin {
    private static @Nullable Integer tickCountdown;
    private static @Nullable Integer totalTicks;
    private static @Nullable Phase nextPhase;
    private static @Nullable BossBar bossBar;

    @Executes
    void onExec(CommandSender s, String phaseString, int ticks) {
        if (tickCountdown != null) {
            s.sendMessage("there is already an countdown!");
            return;
        }

        Phase phase;
        try {
            phase = Phase.valueOf(phaseString);
        } catch (IllegalArgumentException e) {
            s.sendMessage("that phasestring wasnt found");
            return;
        }

        bossBar = plugin.getServer().createBossBar(
                phase.name(),
                BarColor.GREEN,
                BarStyle.SEGMENTED_20);

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
        bossBar.setProgress((double) 1 / ticks);
        nextPhase = phase;
        tickCountdown = ticks;
        totalTicks = ticks;
        s.sendMessage("started the bossbar countdown successfully");
    }

    public static void tick(BukkitTask ignoredTask) {
        if (tickCountdown == null) return;
        if (
                bossBar == null ||
                totalTicks == null
        ) {
            plugin.getComponentLogger().error("some variables are null!");
            return;
        }

        tickCountdown--;
        bossBar.setProgress((double) tickCountdown / totalTicks);
        if (tickCountdown == 0) {

            if (nextPhase == null) {
                plugin.getComponentLogger().error("nextPhase is empty, couldn't change phase...");
                return;
            }
            plugin.setPhase(nextPhase);
            bossBar.removeAll();

            tickCountdown = null;
            bossBar = null;
            totalTicks = null;
            nextPhase = null;

            plugin.getComponentLogger().info("changed the phase successfully using phase countdown");
        }
    }
}
