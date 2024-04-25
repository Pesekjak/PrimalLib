package org.machinemc.primallib.metadata.model.mob;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link VexEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface VexEntity extends HostileEntity {
    /**
     * Metadata field for VEX_FLAGS state of the entity
     */
    EntityData.Field<Byte> VEX_FLAGS = Initializer.VEX_FLAGS;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> VEX_FLAGS;

        static {
            VEX_FLAGS = new EntityData.Field<>(16, Serializer.BYTE);
        }
    }
}
