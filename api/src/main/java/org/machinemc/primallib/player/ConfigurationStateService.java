package org.machinemc.primallib.player;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.util.AutoRegisteringService;
import org.machinemc.primallib.version.MinecraftVersion;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Service for managing the player in configuration state.
 * <p>
 * This uses a lot of trickery to work on Paper servers and can cause
 * a lot of unexpected stuff to happen. Use with caution.
 */
@VersionDependant
@ApiStatus.Experimental
public abstract class ConfigurationStateService extends AutoRegisteringService<ConfigurationStateService> {

    /**
     * Returns instance of configuration state service for currently running server.
     *
     * @return configuration state service
     */
    public static ConfigurationStateService get() {
        var provider = Bukkit.getServicesManager().getRegistration(ConfigurationStateService.class);
        Preconditions.checkNotNull(provider, "Configuration State service is not supported on " + MinecraftVersion.get() + " server");
        return provider.getProvider();
    }

    /**
     * Switches player to configuration state.
     * <p>
     * Paper breaks the ability to send player to configuration state,
     * this uses some tricky methods to achieve that and might not
     * work as expected, use with caution.
     *
     * @param player player
     * @see #switchToPlay(Player)
     */
    public abstract void switchToConfiguration(Player player);

    /**
     * Switches the player to play state.
     * <p>
     * Paper breaks the ability to send player to configuration state,
     * this uses some tricky methods to achieve that and might not
     * work as expected, use with caution.
     * <p>
     * Calls {@link org.machinemc.primallib.event.player.PlayerExitReconfigurationEvent}.
     *
     * @param player player
     * @see #switchToConfiguration(Player)
     */
    public abstract void switchToPlay(Player player);

    /**
     * Resends enabled experiment features.
     * <p>
     * @see org.bukkit.FeatureFlag
     *
     * @param player player
     */
    @SuppressWarnings("UnstableApiUsage")
    public abstract void resendEnabledFeatures(Player player);

    /**
     * Resends required server resources to player.
     * <p>
     * This is required for player to accept registries.
     *
     * @param player player
     */
    public abstract void resendServerResources(Player player);

    /**
     * Resends the server registries to the player.
     * <p>
     * It is required to resend server resources to player before
     * resending the registries.
     *
     * @param player player
     * @see #resendServerResources(Player)
     */
    public abstract void resendRegistries(Player player);

    /**
     * Resends tags to the player.
     *
     * @param player player
     */
    public abstract void resendTags(Player player);

    /**
     * Clears chat for the player.
     *
     * @param player player
     */
    public abstract void clearChat(Player player);

    @Override
    public Class<ConfigurationStateService> getRegistrationClass() {
        return ConfigurationStateService.class;
    }

}
