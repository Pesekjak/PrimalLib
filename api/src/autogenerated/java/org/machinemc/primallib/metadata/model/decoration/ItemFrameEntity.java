package org.machinemc.primallib.metadata.model.decoration;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link ItemFrameEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ItemFrameEntity extends AbstractDecorationEntity {
    /**
     * Metadata field for ITEM_STACK state of the entity
     */
    EntityData.Field<ItemStack> ITEM_STACK = Initializer.ITEM_STACK;

    /**
     * Metadata field for ROTATION state of the entity
     */
    EntityData.Field<Integer> ROTATION = Initializer.ROTATION;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<ItemStack> ITEM_STACK;

        static final EntityData.Field<Integer> ROTATION;

        static {
            ITEM_STACK = new EntityData.Field<>(8, Serializer.SLOT);
            ROTATION = new EntityData.Field<>(9, Serializer.INT);
        }
    }
}
