package org.machinemc.primallib.metadata.model.passive;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link LlamaEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface LlamaEntity extends AbstractDonkeyEntity {
    /**
     * Metadata field for STRENGTH state of the entity
     */
    EntityData.Field<Integer> STRENGTH = Initializer.STRENGTH;

    /**
     * Metadata field for CARPET_COLOR state of the entity
     */
    EntityData.Field<Integer> CARPET_COLOR = Initializer.CARPET_COLOR;

    /**
     * Metadata field for VARIANT state of the entity
     */
    EntityData.Field<Integer> VARIANT = Initializer.VARIANT;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> STRENGTH;

        static final EntityData.Field<Integer> CARPET_COLOR;

        static final EntityData.Field<Integer> VARIANT;

        static {
            STRENGTH = new EntityData.Field<>(19, Serializer.INT);
            CARPET_COLOR = new EntityData.Field<>(20, Serializer.INT);
            VARIANT = new EntityData.Field<>(21, Serializer.INT);
        }
    }
}
