package org.machinemc.primallib.metadata.model.decoration;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link ItemDisplayEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ItemDisplayEntityDataModel extends DisplayEntityDataModel {
    /**
     * Metadata field for ITEM state of the entity
     */
    EntityData.Field<ItemStack> ITEM = Initializer.ITEM;

    /**
     * Metadata field for ITEM_DISPLAY state of the entity
     */
    EntityData.Field<Byte> ITEM_DISPLAY = Initializer.ITEM_DISPLAY;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<ItemStack> ITEM;

        static final EntityData.Field<Byte> ITEM_DISPLAY;

        static {
            ITEM = new EntityData.Field<>(23, Serializer.SLOT);
            ITEM_DISPLAY = new EntityData.Field<>(24, Serializer.BYTE);
        }
    }
}
