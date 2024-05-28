package org.machinemc.primallib.metadata;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.machinemc.primallib.entity.EntityLike;
import org.machinemc.primallib.util.AutoRegisteringService;
import org.machinemc.primallib.version.MinecraftVersion;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Service for updating entity metadata.
 */
@VersionDependant
public abstract class MetadataService extends AutoRegisteringService<MetadataService> {

    /**
     * Returns instance of metadata service for currently running server.
     *
     * @return metadata service
     */
    public static MetadataService get() {
        var provider = Bukkit.getServicesManager().getRegistration(MetadataService.class);
        Preconditions.checkNotNull(provider, "Metadata service is not supported on " + MinecraftVersion.get() + " server");
        return provider.getProvider();
    }

    /**
     * Sends metadata update for an entity for given player.
     * <p>
     * Sent entity data do not need to contain all fields for the entity type.
     * If no value for a field is provided, the old value will be kept and
     * only provided fields will get updated.
     *
     * @param player player to send the changes to
     * @param target targeted entity
     * @param data updated entity data
     */
    public abstract void sendMetadata(Player player, EntityLike target, EntityData data);

    @Override
    public Class<MetadataService> getRegistrationClass() {
        return MetadataService.class;
    }

}
