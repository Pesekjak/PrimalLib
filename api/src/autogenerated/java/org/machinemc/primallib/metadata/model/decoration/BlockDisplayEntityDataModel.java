package org.machinemc.primallib.metadata.model.decoration;

import javax.annotation.processing.Generated;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link BlockDisplayEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface BlockDisplayEntityDataModel extends DisplayEntityDataModel {
    /**
     * Metadata field for BLOCK_STATE state of the entity
     */
    EntityData.Field<BlockData> BLOCK_STATE = Initializer.BLOCK_STATE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<BlockData> BLOCK_STATE;

        static {
            BLOCK_STATE = new EntityData.Field<>(23, Serializer.BLOCK_STATE);
        }
    }
}
