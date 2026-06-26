package de.itsgraphax.walls.phases;

import de.itsgraphax.walls.HasPlugin;

public enum Phase implements HasPlugin {
    /**
     * Before the server is open (closed)
     */
    NOTREADY(plugin.notReady()),
    /**
     * Before the event has started (lobby-esque)
     */
    READY(plugin.ready()),
    /**
     * Gearup phase (ingame)
     */
    GEARUP(plugin.gearUp()),
    /**
     * Pvp Phase (ingame)
     */
    PVP(plugin.pvp()),
    /**
     * Event is over and winner is shown (awards)
     */
    FINISHED(plugin.finished()),
    /**
     * The event has finished (lobby-esque)
     */
    EVENTOVER(plugin.eventOver());

    private final PhaseDefinition def;

    Phase(PhaseDefinition def) {
        this.def = def;
    }

    public PhaseDefinition getDef() {
        return def;
    }
}
