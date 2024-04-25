package org.machinemc.primallib.metadata.model.mob;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link GuardianEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface GuardianEntity extends HostileEntity {
    /**
     * Metadata field for SPIKES_RETRACTED state of the entity
     */
    EntityData.Field<Boolean> SPIKES_RETRACTED = Initializer.SPIKES_RETRACTED;

    /**
     * Metadata field for BEAM_TARGET_ID state of the entity
     */
    EntityData.Field<Integer> BEAM_TARGET_ID = Initializer.BEAM_TARGET_ID;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Boolean> SPIKES_RETRACTED;

        static final EntityData.Field<Integer> BEAM_TARGET_ID;

        static {
            SPIKES_RETRACTED = new EntityData.Field<>(16, Serializer.BOOLEAN);
            BEAM_TARGET_ID = new EntityData.Field<>(17, Serializer.INT);
        }
    }
}
