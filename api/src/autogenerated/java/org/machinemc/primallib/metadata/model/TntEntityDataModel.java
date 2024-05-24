package org.machinemc.primallib.metadata.model;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link TntEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface TntEntityDataModel extends EntityDataModel {
    /**
     * Metadata field for FUSE state of the entity
     */
    EntityData.Field<Integer> FUSE = Initializer.FUSE;

    /**
     * Metadata field for BLOCK_STATE state of the entity
     */
    EntityData.Field<BlockData> BLOCK_STATE = Initializer.BLOCK_STATE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> FUSE;

        static final EntityData.Field<BlockData> BLOCK_STATE;

        static {
            FUSE = new EntityData.Field<>(8, Serializer.INT);
            BLOCK_STATE = new EntityData.Field<>(9, Serializer.BLOCK_STATE);
        }
    }
}
