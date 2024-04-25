package org.machinemc.primallib.metadata.model.passive;

import java.lang.String;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link MooshroomEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface MooshroomEntity extends CowEntity {
    /**
     * Metadata field for TYPE state of the entity
     */
    EntityData.Field<String> TYPE = Initializer.TYPE;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<String> TYPE;

        static {
            TYPE = new EntityData.Field<>(17, Serializer.STRING);
        }
    }
}
