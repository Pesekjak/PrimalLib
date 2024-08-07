package org.machinemc.primallib.metadata.model.projectile.thrown;

import javax.annotation.processing.Generated;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link ThrownItemEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ThrownItemEntityDataModel extends ThrownEntityDataModel {
    /**
     * Metadata field for ITEM state of the entity
     */
    EntityData.Field<ItemStack> ITEM = Initializer.ITEM;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<ItemStack> ITEM;

        static {
            ITEM = new EntityData.Field<>(8, Serializer.SLOT);
        }
    }
}
