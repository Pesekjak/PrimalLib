package org.machinemc.primallib.metadata.model.passive;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link SheepEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface SheepEntity extends AnimalEntity {
    /**
     * Metadata field for COLOR state of the entity
     */
    EntityData.Field<Byte> COLOR = Initializer.COLOR;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> COLOR;

        static {
            COLOR = new EntityData.Field<>(17, Serializer.BYTE);
        }
    }
}
