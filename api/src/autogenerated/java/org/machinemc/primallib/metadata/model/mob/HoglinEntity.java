package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.passive.AnimalEntity;

/**
 * Applicable metadata fields for all entities inheriting {@link HoglinEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface HoglinEntity extends AnimalEntity {
    /**
     * Metadata field for BABY state of the entity
     */
    EntityData.Field<Boolean> BABY = Initializer.BABY;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> BABY;

        static {
            BABY = new EntityData.Field<>(17, Serializer.BOOLEAN);
        }
    }
}
