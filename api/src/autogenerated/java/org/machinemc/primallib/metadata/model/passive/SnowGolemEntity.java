package org.machinemc.primallib.metadata.model.passive;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link SnowGolemEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface SnowGolemEntity extends GolemEntity {
    /**
     * Metadata field for SNOW_GOLEM_FLAGS state of the entity
     */
    EntityData.Field<Byte> SNOW_GOLEM_FLAGS = Initializer.SNOW_GOLEM_FLAGS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> SNOW_GOLEM_FLAGS;

        static {
            SNOW_GOLEM_FLAGS = new EntityData.Field<>(16, Serializer.BYTE);
        }
    }
}
