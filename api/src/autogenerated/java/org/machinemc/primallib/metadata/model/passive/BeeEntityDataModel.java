package org.machinemc.primallib.metadata.model.passive;

import java.lang.Byte;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link BeeEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface BeeEntityDataModel extends AnimalEntityDataModel {
    /**
     * Metadata field for BEE_FLAGS state of the entity
     */
    EntityData.Field<Byte> BEE_FLAGS = Initializer.BEE_FLAGS;

    /**
     * Metadata field for ANGER state of the entity
     */
    EntityData.Field<Integer> ANGER = Initializer.ANGER;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> BEE_FLAGS;

        static final EntityData.Field<Integer> ANGER;

        static {
            BEE_FLAGS = new EntityData.Field<>(17, Serializer.BYTE);
            ANGER = new EntityData.Field<>(18, Serializer.INT);
        }
    }
}
