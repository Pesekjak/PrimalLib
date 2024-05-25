package org.machinemc.primallib.internal;

import org.machinemc.primallib.v1_20_6.VersionBootstrap_v1_20_6;
import org.machinemc.primallib.version.MinecraftVersion;

/**
 * Class that selects the correct bootstrap depending on the
 * Minecraft version of the server.
 */
public final class SharedBootstrapSelector implements VersionBootstrap.Selector {

    private final MinecraftVersion version = MinecraftVersion.get();

    @Override
    public VersionBootstrap select() {
        return switch (version) {
            case MINECRAFT_1_20_6 -> new VersionBootstrap_v1_20_6();
        };
    }

}
