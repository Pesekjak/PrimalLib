package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.mob.PathAwareEntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link AllayEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface AllayEntityDataModel extends PathAwareEntityDataModel {
    /**
     * Metadata field for DANCING state of the entity
     */
    EntityData.Field<Boolean> DANCING = Initializer.DANCING;

    /**
     * Metadata field for CAN_DUPLICATE state of the entity
     */
    EntityData.Field<Boolean> CAN_DUPLICATE = Initializer.CAN_DUPLICATE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> DANCING;

        static final EntityData.Field<Boolean> CAN_DUPLICATE;

        static {
            DANCING = new EntityData.Field<>(16, Serializer.BOOLEAN);
            CAN_DUPLICATE = new EntityData.Field<>(17, Serializer.BOOLEAN);
        }
    }
}
