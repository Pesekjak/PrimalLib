package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link PillagerEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PillagerEntityDataModel extends IllagerEntityDataModel {
    /**
     * Metadata field for CHARGING state of the entity
     */
    EntityData.Field<Boolean> CHARGING = Initializer.CHARGING;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> CHARGING;

        static {
            CHARGING = new EntityData.Field<>(17, Serializer.BOOLEAN);
        }
    }
}
