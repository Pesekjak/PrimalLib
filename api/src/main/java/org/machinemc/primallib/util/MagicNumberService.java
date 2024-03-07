package org.machinemc.primallib.util;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Fluid;
import org.bukkit.GameEvent;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.machinemc.primallib.version.MinecraftVersion;
import org.machinemc.primallib.version.VersionDependant;

/**
 * Service that can convert objects from different registries to numeric IDs
 * and vice versa.
 * <p>
 * This can be used for example with {@link org.machinemc.primallib.event.configuration.PlayerTagsEvent}.
 */
@VersionDependant
public abstract class MagicNumberService extends AutoRegisteringService<MagicNumberService> {

    /**
     * Returns instance of magic number service for currently running server.
     *
     * @return magic number service
     */
    public static MagicNumberService get() {
        var provider = Bukkit.getServicesManager().getRegistration(MagicNumberService.class);
        Preconditions.checkNotNull(provider, "Magic Numbers service is not supported on " + MinecraftVersion.get() + " server");
        return provider.getProvider();
    }

    /**
     * Returns block data from state ID. This differs from regular Material registry
     * because all block data states are covered and each one has a unique ID.
     *
     * @param id id of the block data
     * @return block data for given ID
     */
    public abstract BlockData getBlockDataFromID(int id);

    /**
     * Returns state ID from given block data. This differs from regular Material registry
     * because all block data states are covered and each one has a unique ID.
     *
     * @param blockData block data
     * @return ID for given block data
     */
    public abstract int getIDFromBlockData(BlockData blockData);

    /**
     * @param id magic id
     * @return fluid registered in the fluid registry under given ID
     */
    public abstract Fluid getFluidFromID(int id);

    /**
     * @param fluid fluid
     * @return ID of the provided fluid in the fluid registry
     */
    public abstract int getIDFromFluid(Fluid fluid);

    /**
     * @param id magic id
     * @return item registered in the item registry under given ID
     */
    public abstract Material getItemFromID(int id);

    /**
     * @param item item material
     * @return ID of the provided item in the item registry
     */
    public abstract int getIDFromItem(Material item);

    /**
     * @param id magic id
     * @return entity type registered in the entity type registry under given ID
     */
    public abstract EntityType getEntityTypeFromID(int id);

    /**
     * @param entityType entity type
     * @return ID of the provided entity type in the entity type registry
     */
    public abstract int getIDFromEntityType(EntityType entityType);

    /**
     * @param id magic id
     * @return game event registered in the block registry under game event ID
     */
    public abstract GameEvent getGameEventFromID(int id);

    /**
     * @param gameEvent game event
     * @return ID of the provided game event in the game event registry
     */
    public abstract int getIDFromGameEvent(GameEvent gameEvent);

    @Override
    public Class<MagicNumberService> getRegistrationClass() {
        return MagicNumberService.class;
    }

}
