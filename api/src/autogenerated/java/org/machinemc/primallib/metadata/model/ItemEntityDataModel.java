package org.machinemc.primallib.metadata.model;

import javax.annotation.processing.Generated;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link ItemEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ItemEntityDataModel extends EntityDataModel {
    /**
     * Metadata field for STACK state of the entity
     */
    EntityData.Field<ItemStack> STACK = Initializer.STACK;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<ItemStack> STACK;

        static {
            STACK = new EntityData.Field<>(8, Serializer.SLOT);
        }
    }
}
