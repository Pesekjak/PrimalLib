package org.machinemc.primallib.metadata.model.passive;

import java.lang.Byte;
import java.lang.Integer;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link FoxEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface FoxEntityDataModel extends AnimalEntityDataModel {
    /**
     * Metadata field for TYPE state of the entity
     */
    EntityData.Field<Integer> TYPE = Initializer.TYPE;

    /**
     * Metadata field for FOX_FLAGS state of the entity
     */
    EntityData.Field<Byte> FOX_FLAGS = Initializer.FOX_FLAGS;

    /**
     * Metadata field for OWNER state of the entity
     */
    EntityData.Field<Optional<UUID>> OWNER = Initializer.OWNER;

    /**
     * Metadata field for OTHER_TRUSTED state of the entity
     */
    EntityData.Field<Optional<UUID>> OTHER_TRUSTED = Initializer.OTHER_TRUSTED;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> TYPE;

        static final EntityData.Field<Byte> FOX_FLAGS;

        static final EntityData.Field<Optional<UUID>> OWNER;

        static final EntityData.Field<Optional<UUID>> OTHER_TRUSTED;

        static {
            TYPE = new EntityData.Field<>(17, Serializer.INT);
            FOX_FLAGS = new EntityData.Field<>(18, Serializer.BYTE);
            OWNER = new EntityData.Field<>(19, Serializer.OPTIONAL_UUID);
            OTHER_TRUSTED = new EntityData.Field<>(20, Serializer.OPTIONAL_UUID);
        }
    }
}
