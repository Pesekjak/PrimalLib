package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link OcelotEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface OcelotEntityDataModel extends AnimalEntityDataModel {
    /**
     * Metadata field for TRUSTING state of the entity
     */
    EntityData.Field<Boolean> TRUSTING = Initializer.TRUSTING;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> TRUSTING;

        static {
            TRUSTING = new EntityData.Field<>(17, Serializer.BOOLEAN);
        }
    }
}
