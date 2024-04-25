package org.machinemc.primallib.metadata.model.passive;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link AbstractHorseEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface AbstractHorseEntity extends AnimalEntity {
    /**
     * Metadata field for HORSE_FLAGS state of the entity
     */
    EntityData.Field<Byte> HORSE_FLAGS = Initializer.HORSE_FLAGS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> HORSE_FLAGS;

        static {
            HORSE_FLAGS = new EntityData.Field<>(17, Serializer.BYTE);
        }
    }
}
