package org.machinemc.primallib.metadata.model;

import io.papermc.paper.math.Position;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link FallingBlockEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface FallingBlockEntityDataModel extends EntityDataModel {
    /**
     * Metadata field for BLOCK_POS state of the entity
     */
    EntityData.Field<Position> BLOCK_POS = Initializer.BLOCK_POS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Position> BLOCK_POS;

        static {
            BLOCK_POS = new EntityData.Field<>(8, Serializer.POSITION);
        }
    }
}
