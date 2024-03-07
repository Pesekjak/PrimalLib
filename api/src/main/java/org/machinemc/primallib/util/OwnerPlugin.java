package org.machinemc.primallib.util;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;

/**
 * Util class containing the instance of the plugin using the library.
 * <p>
 * The owner instance is injected during the plugin creation in bootstrap.
 */
public final class OwnerPlugin {

    private static Plugin owner;

    /**
     * Returns the plugin using the PrimalLib.
     *
     * @return owner plugin
     */
    public static Plugin get() {
        Preconditions.checkNotNull(owner, "PrimalLib has not been initialized");
        return owner;
    }

    /**
     * Initializes the PrimalLib.
     *
     * @param plugin owner plugin
     */
    @ApiStatus.Internal
    public static void initialize(Plugin plugin) {
        Preconditions.checkState(owner == null, "PrimalLib has already been initialized");
        owner = plugin;
    }

}
