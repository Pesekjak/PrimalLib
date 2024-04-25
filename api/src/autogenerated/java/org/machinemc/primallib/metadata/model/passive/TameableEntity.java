package org.machinemc.primallib.metadata.model.passive;

import java.lang.Byte;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link TameableEntity} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface TameableEntity extends AnimalEntity {
    /**
     * Metadata field for TAMEABLE_FLAGS state of the entity
     */
    EntityData.Field<Byte> TAMEABLE_FLAGS = Initializer.TAMEABLE_FLAGS;

    /**
     * Metadata field for OWNER_UUID state of the entity
     */
    EntityData.Field<Optional<UUID>> OWNER_UUID = Initializer.OWNER_UUID;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Byte> TAMEABLE_FLAGS;

        static final EntityData.Field<Optional<UUID>> OWNER_UUID;

        static {
            TAMEABLE_FLAGS = new EntityData.Field<>(17, Serializer.BYTE);
            OWNER_UUID = new EntityData.Field<>(18, Serializer.OPTIONAL_UUID);
        }
    }
}
