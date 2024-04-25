package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import java.lang.Long;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link CamelEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface CamelEntity extends AbstractHorseEntity {
    /**
     * Metadata field for DASHING state of the entity
     */
    EntityData.Field<Boolean> DASHING = Initializer.DASHING;

    /**
     * Metadata field for LAST_POSE_TICK state of the entity
     */
    EntityData.Field<Long> LAST_POSE_TICK = Initializer.LAST_POSE_TICK;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> DASHING;

        static final EntityData.Field<Long> LAST_POSE_TICK;

        static {
            DASHING = new EntityData.Field<>(18, Serializer.BOOLEAN);
            LAST_POSE_TICK = new EntityData.Field<>(19, Serializer.LONG);
        }
    }
}
