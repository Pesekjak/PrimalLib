package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link AbstractPiglinEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface AbstractPiglinEntityDataModel extends HostileEntityDataModel {
    /**
     * Metadata field for IMMUNE_TO_ZOMBIFICATION state of the entity
     */
    EntityData.Field<Boolean> IMMUNE_TO_ZOMBIFICATION = Initializer.IMMUNE_TO_ZOMBIFICATION;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> IMMUNE_TO_ZOMBIFICATION;

        static {
            IMMUNE_TO_ZOMBIFICATION = new EntityData.Field<>(16, Serializer.BOOLEAN);
        }
    }
}
