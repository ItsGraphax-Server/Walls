package de.itsgraphax.walls.pdc;

import de.itsgraphax.grphxLib.utils.PdcDataBase;
import de.itsgraphax.walls.WallsPlugin;

public class PdcData extends PdcDataBase {
    private static Namespaces namespaces; // #TODO make that cleaner

    public static void updateNamespacesPointer() {
        namespaces = WallsPlugin.instance().namespaces();
    }
}
