package org.machinemc.primallib.metadata.model.vehicle;

import java.lang.Boolean;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link FurnaceMinecartEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface FurnaceMinecartEntity extends AbstractMinecartEntity {
    /**
     * Metadata field for LIT state of the entity
     */
    EntityData.Field<Boolean> LIT = Initializer.LIT;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> LIT;

        static {
            LIT = new EntityData.Field<>(14, Serializer.BOOLEAN);
        }
    }
}
