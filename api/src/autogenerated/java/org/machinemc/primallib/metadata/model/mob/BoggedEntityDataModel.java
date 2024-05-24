package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link BoggedEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface BoggedEntityDataModel extends AbstractSkeletonEntityDataModel {
    /**
     * Metadata field for SHEARED state of the entity
     */
    EntityData.Field<Boolean> SHEARED = Initializer.SHEARED;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> SHEARED;

        static {
            SHEARED = new EntityData.Field<>(16, Serializer.BOOLEAN);
        }
    }
}
