package org.machinemc.primallib.metadata.model.passive;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link HorseEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface HorseEntityDataModel extends AbstractHorseEntityDataModel {
    /**
     * Metadata field for VARIANT state of the entity
     */
    EntityData.Field<Integer> VARIANT = Initializer.VARIANT;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> VARIANT;

        static {
            VARIANT = new EntityData.Field<>(18, Serializer.INT);
        }
    }
}
