package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link GhastEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface GhastEntity extends FlyingEntity {
    /**
     * Metadata field for SHOOTING state of the entity
     */
    EntityData.Field<Boolean> SHOOTING = Initializer.SHOOTING;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> SHOOTING;

        static {
            SHOOTING = new EntityData.Field<>(16, Serializer.BOOLEAN);
        }
    }
}
