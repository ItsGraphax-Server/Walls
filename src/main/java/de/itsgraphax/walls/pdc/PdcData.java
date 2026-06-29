package de.itsgraphax.walls.pdc;

import de.itsgraphax.grphxLib.utils.PdcDataBase;
import de.itsgraphax.walls.WallsPlugin;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public class PdcData extends PdcDataBase {
    private static Namespaces namespaces;

    public static void updateNamespacesPointer() {
        namespaces = WallsPlugin.instance().namespaces();
    }

    public static void team(@NotNull PersistentDataHolder holder, @Nullable Integer value) {
        if (value != null) pdc(holder).set(namespaces.team(), PersistentDataType.INTEGER, value);
        else pdc(holder).remove(namespaces.team());
    }

    public static @Nullable Integer team(@NotNull PersistentDataHolder holder) {
        return pdc(holder).get(namespaces.team(), PersistentDataType.INTEGER);
    }

    public static void enderPearlThrownTime(@NotNull PersistentDataHolder holder, @NotNull LocalDateTime time) {
        pdc(holder).set(namespaces.enderPearlThrownTime(), PersistentDataType.STRING, time.toString());
    }

    public static LocalDateTime enderPearlThrownTime(@NotNull PersistentDataHolder holder) {
        return LocalDateTime.parse(
                pdc(holder).getOrDefault(namespaces.enderPearlThrownTime(), PersistentDataType.STRING, ""));
    }
}
