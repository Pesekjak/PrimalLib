package org.machinemc.primallib.metadata.model.mob;

import java.lang.Byte;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link SpellcastingIllagerEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface SpellcastingIllagerEntity extends IllagerEntity {
    /**
     * Metadata field for SPELL state of the entity
     */
    EntityData.Field<Byte> SPELL = Initializer.SPELL;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> SPELL;

        static {
            SPELL = new EntityData.Field<>(17, Serializer.BYTE);
        }
    }
}
