package de.itsgraphax.walls;

import de.itsgraphax.grphxLib.utils.RichText;

public interface HasPlugin {
    WallsPlugin plugin = WallsPlugin.instance();
    RichText.RichConfigText rt = plugin.richText();
}
