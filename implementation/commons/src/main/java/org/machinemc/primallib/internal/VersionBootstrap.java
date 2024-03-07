package org.machinemc.primallib.internal;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a bootstrap for a version implementation.
 * <p>
 * Is used by different version implementations to register
 * listeners, handlers etc.
 *
 * @see PrimalLibBootstrap
 */
@SuppressWarnings("UnstableApiUsage")
public interface VersionBootstrap {

    /**
     * Called when the instance of the owner plugin is created.
     *
     * @param plugin owner plugin
     * @param context context
     */
    void bootstrap(JavaPlugin plugin, PluginProviderContext context);

    /**
     * Class that selects the correct bootstrap depending on the
     * Minecraft version of the server.
     */
    interface Selector {

        /**
         * Creates the default selector implementation.
         *
         * @return selector
         */
        static Selector create() throws Exception {
            String packageName = VersionBootstrap.class.getPackage().getName();
            return (Selector) Class.forName(packageName + ".SharedBootstrapSelector")
                    .getConstructor()
                    .newInstance();
        }

        /**
         * Returns the version bootstrap for the current version.
         *
         * @return version bootstrap
         */
        VersionBootstrap select();

    }

}
