package org.machinemc.primallib.metadata.model.passive;

import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.entity.ArmadilloState;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link ArmadilloEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface ArmadilloEntityDataModel extends AnimalEntityDataModel {
    /**
     * Metadata field for STATE state of the entity
     */
    EntityData.Field<ArmadilloState> STATE = Initializer.STATE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<ArmadilloState> STATE;

        static {
            STATE = new EntityData.Field<>(17, Serializer.ARMADILLO_STATE);
        }
    }
}
