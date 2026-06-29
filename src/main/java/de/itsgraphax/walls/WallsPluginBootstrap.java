package de.itsgraphax.walls;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@NullMarked
public class WallsPluginBootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext ctx) {
        ctx.getLifecycleManager().registerEventHandler(LifecycleEvents.DATAPACK_DISCOVERY.newHandler(
                event -> {
                    try {
                        URL resource = getClass().getResource("/wallsdp");
                        if (resource == null) throw new RuntimeException("wallsdp not found");
                        URI uri = resource.toURI();
                        event.registrar().discoverPack(uri, "provided");
                    } catch (URISyntaxException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        ));
    }
}
