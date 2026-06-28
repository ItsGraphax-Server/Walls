package de.itsgraphax.walls;

import de.itsgraphax.grphxLib.shorthands.OnEnable;
import de.itsgraphax.grphxLib.utils.RichText;
import de.itsgraphax.walls.commands.DebugBrigadier;
import de.itsgraphax.walls.commands.SetPhaseBrigadier;
import de.itsgraphax.walls.commands.SetTeamBrigadier;
import de.itsgraphax.walls.misc.Enderpearls;
import de.itsgraphax.walls.pdc.Namespaces;
import de.itsgraphax.walls.pdc.PdcData;
import de.itsgraphax.walls.phases.Phase;
import de.itsgraphax.walls.phases.eventOver.EventOver;
import de.itsgraphax.walls.phases.finished.Finished;
import de.itsgraphax.walls.phases.gearUp.GearUp;
import de.itsgraphax.walls.phases.general.General;
import de.itsgraphax.walls.phases.notReady.NotReady;
import de.itsgraphax.walls.phases.pvp.Pvp;
import de.itsgraphax.walls.phases.ready.Ready;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class WallsPlugin extends JavaPlugin {
    private static WallsPlugin instance;

    private final Namespaces namespaces = new Namespaces(this);
    private final WallsData data = new WallsData("data.yml", this);
    private final RichText.RichConfigText richText = new RichText.RichConfigText(this);

    private Phase currentPhase;
    private General general;
    private NotReady notReady;
    private Ready ready;
    private GearUp gearUp;
    private Pvp pvp;
    private Finished finished;
    private EventOver eventOver;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        PdcData.updateNamespacesPointer();

        general     = new General();
        notReady    = new NotReady();
        ready       = new Ready();
        gearUp      = new GearUp();
        pvp         = new Pvp();
        finished    = new Finished();
        eventOver   = new EventOver();

        setPhase(data.getPhase());

        OnEnable.registerEvents(Set.of(
                general, notReady, ready, gearUp, pvp, finished, eventOver,
                new Enderpearls()
        ), this);
        OnEnable.registerCommands(Set.of(
                SetPhaseBrigadier::register,
                SetTeamBrigadier::register,
                DebugBrigadier::register
        ), this);
    }

    public static WallsPlugin instance() {
        return instance;
    }

    public Namespaces namespaces() {
        return namespaces;
    }

    public RichText.RichConfigText richText() {
        return richText;
    }

    public void setPhase(@NotNull Phase phase) {
        general.onEnd();
        if (currentPhase != null) currentPhase.getDef().onEnd();

        currentPhase = phase;
        general.onStart();
        currentPhase.getDef().onStart();

        data.setPhase(currentPhase);
    }
    public Phase getPhase() {
        return currentPhase;
    }

    public NotReady notReady() {
        return notReady;
    }
    public Ready ready() {
        return ready;
    }
    public GearUp gearUp() {
        return gearUp;
    }
    public Pvp pvp() {
        return pvp;
    }
    public Finished finished() {
        return finished;
    }
    public EventOver eventOver() {
        return eventOver;
    }
}
