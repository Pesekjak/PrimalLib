package org.machinemc.primallib.metadata.model.passive;

import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link GlowSquidEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface GlowSquidEntity extends SquidEntity {
    /**
     * Metadata field for DARK_TICKS_REMAINING state of the entity
     */
    EntityData.Field<Integer> DARK_TICKS_REMAINING = Initializer.DARK_TICKS_REMAINING;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> DARK_TICKS_REMAINING;

        static {
            DARK_TICKS_REMAINING = new EntityData.Field<>(16, Serializer.INT);
        }
    }
}
