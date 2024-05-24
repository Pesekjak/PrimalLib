package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.mob.PathAwareEntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link PassiveEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface PassiveEntityDataModel extends PathAwareEntityDataModel {
    /**
     * Metadata field for CHILD state of the entity
     */
    EntityData.Field<Boolean> CHILD = Initializer.CHILD;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> CHILD;

        static {
            CHILD = new EntityData.Field<>(16, Serializer.BOOLEAN);
        }
    }
}
