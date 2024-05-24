package org.machinemc.primallib.metadata.model.mob;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link BlazeEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface BlazeEntityDataModel extends HostileEntityDataModel {
    /**
     * Metadata field for BLAZE_FLAGS state of the entity
     */
    EntityData.Field<Byte> BLAZE_FLAGS = Initializer.BLAZE_FLAGS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> BLAZE_FLAGS;

        static {
            BLAZE_FLAGS = new EntityData.Field<>(16, Serializer.BYTE);
        }
    }
}
