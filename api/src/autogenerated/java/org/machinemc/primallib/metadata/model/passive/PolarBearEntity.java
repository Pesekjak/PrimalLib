package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link PolarBearEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PolarBearEntity extends AnimalEntity {
    /**
     * Metadata field for WARNING state of the entity
     */
    EntityData.Field<Boolean> WARNING = Initializer.WARNING;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> WARNING;

        static {
            WARNING = new EntityData.Field<>(17, Serializer.BOOLEAN);
        }
    }
}
