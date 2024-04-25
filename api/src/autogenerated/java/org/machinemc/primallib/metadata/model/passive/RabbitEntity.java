package org.machinemc.primallib.metadata.model.passive;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link RabbitEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface RabbitEntity extends AnimalEntity {
    /**
     * Metadata field for RABBIT_TYPE state of the entity
     */
    EntityData.Field<Integer> RABBIT_TYPE = Initializer.RABBIT_TYPE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> RABBIT_TYPE;

        static {
            RABBIT_TYPE = new EntityData.Field<>(17, Serializer.INT);
        }
    }
}
