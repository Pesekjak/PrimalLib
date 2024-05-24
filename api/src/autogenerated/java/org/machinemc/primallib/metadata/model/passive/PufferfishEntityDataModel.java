package org.machinemc.primallib.metadata.model.passive;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link PufferfishEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PufferfishEntityDataModel extends FishEntityDataModel {
    /**
     * Metadata field for PUFF_STATE state of the entity
     */
    EntityData.Field<Integer> PUFF_STATE = Initializer.PUFF_STATE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> PUFF_STATE;

        static {
            PUFF_STATE = new EntityData.Field<>(17, Serializer.INT);
        }
    }
}
