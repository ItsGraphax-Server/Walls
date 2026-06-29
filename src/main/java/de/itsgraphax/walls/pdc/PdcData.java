package de.itsgraphax.walls.pdc;

import de.itsgraphax.grphxLib.utils.PdcDataBase;
import de.itsgraphax.walls.WallsPlugin;

import java.time.LocalDateTime;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PdcData extends PdcDataBase {
    private static Namespaces namespaces;

    public static void updateNamespacesPointer() {
        namespaces = WallsPlugin.instance().namespaces();
    }

    public static void enderPearlThrownTime(@NotNull PersistentDataHolder holder, @NotNull LocalDateTime time) {
        pdc(holder).set(namespaces.enderPearlThrownTime(), PersistentDataType.STRING, time.toString());
    }

    public static LocalDateTime enderPearlThrownTime(@NotNull PersistentDataHolder holder) {
        return LocalDateTime.parse(
                pdc(holder).getOrDefault(namespaces.enderPearlThrownTime(), PersistentDataType.STRING, ""));
    }
}
