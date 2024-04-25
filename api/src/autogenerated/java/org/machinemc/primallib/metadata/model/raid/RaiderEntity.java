package org.machinemc.primallib.metadata.model.raid;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.mob.PatrolEntity;

/**
 * Applicable metadata fields for all entities inheriting {@link RaiderEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface RaiderEntity extends PatrolEntity {
    /**
     * Metadata field for CELEBRATING state of the entity
     */
    EntityData.Field<Boolean> CELEBRATING = Initializer.CELEBRATING;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> CELEBRATING;

        static {
            CELEBRATING = new EntityData.Field<>(16, Serializer.BOOLEAN);
        }
    }
}
