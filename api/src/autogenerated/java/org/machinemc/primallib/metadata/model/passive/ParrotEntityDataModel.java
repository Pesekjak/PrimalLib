package org.machinemc.primallib.metadata.model.passive;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link ParrotEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ParrotEntityDataModel extends TameableShoulderEntityDataModel {
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
            VARIANT = new EntityData.Field<>(19, Serializer.INT);
        }
    }
}
