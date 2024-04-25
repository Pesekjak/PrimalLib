package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link AbstractDonkeyEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface AbstractDonkeyEntity extends AbstractHorseEntity {
    /**
     * Metadata field for CHEST state of the entity
     */
    EntityData.Field<Boolean> CHEST = Initializer.CHEST;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> CHEST;

        static {
            CHEST = new EntityData.Field<>(18, Serializer.BOOLEAN);
        }
    }
}
