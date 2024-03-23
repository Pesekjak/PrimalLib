package org.machinemc.primallib.util;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;

/**
 * Util class containing the instance of the plugin using the library.
 * <p>
 * The owner instance is injected during the plugin creation in bootstrap.
 */
public final class OwnerPlugin {

    static Plugin owner;

    private OwnerPlugin() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the plugin using the PrimalLib.
     *
     * @return owner plugin
     */
    public static Plugin get() {
        Preconditions.checkNotNull(owner, "PrimalLib has not been initialized");
        return owner;
    }

}
