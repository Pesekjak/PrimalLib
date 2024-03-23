package org.machinemc.primallib.util;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;

/**
 * Class for accessing the owner plugin of this PrimalLib instance.
 */
public final class OwnerPluginAccessor {

    private OwnerPluginAccessor() {
        throw new UnsupportedOperationException();
    }

    /**
     * Injects new plugin instance as the owner plugin.
     *
     * @param plugin owner plugin
     */
    public static void inject(Plugin plugin) {
        Preconditions.checkState(OwnerPlugin.owner == null, "Owner plugin has already been initialized");
        OwnerPlugin.owner = Preconditions.checkNotNull(plugin, "Plugin can not be null");
    }

}
