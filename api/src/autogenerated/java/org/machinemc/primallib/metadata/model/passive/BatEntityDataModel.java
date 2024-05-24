package org.machinemc.primallib.metadata.model.passive;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.metadata.model.mob.AmbientEntityDataModel;

/**
 * Applicable metadata fields for all entities inheriting {@link BatEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface BatEntityDataModel extends AmbientEntityDataModel {
    /**
     * Metadata field for BAT_FLAGS state of the entity
     */
    EntityData.Field<Byte> BAT_FLAGS = Initializer.BAT_FLAGS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> BAT_FLAGS;

        static {
            BAT_FLAGS = new EntityData.Field<>(16, Serializer.BYTE);
        }
    }
}
