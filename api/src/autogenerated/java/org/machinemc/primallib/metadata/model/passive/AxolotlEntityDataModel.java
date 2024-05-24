package org.machinemc.primallib.metadata.model.passive;

import java.lang.Boolean;
import java.lang.Integer;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.ApiStatus;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

/**
 * Applicable metadata fields for all entities inheriting {@link AxolotlEntityDataModel} type.
 */
@Generated("org.machinemc.primallib.generator.metadata.CodeGenerator")
public interface AxolotlEntityDataModel extends AnimalEntityDataModel {
    /**
     * Metadata field for VARIANT state of the entity
     */
    EntityData.Field<Integer> VARIANT = Initializer.VARIANT;

    /**
     * Metadata field for PLAYING_DEAD state of the entity
     */
    EntityData.Field<Boolean> PLAYING_DEAD = Initializer.PLAYING_DEAD;

    /**
     * Metadata field for FROM_BUCKET state of the entity
     */
    EntityData.Field<Boolean> FROM_BUCKET = Initializer.FROM_BUCKET;

    /**
     * Initializer for entity data fields that supports multiple version implementations.
     */
    @ApiStatus.Internal
    final class Initializer {
        static final EntityData.Field<Integer> VARIANT;

        static final EntityData.Field<Boolean> PLAYING_DEAD;

        static final EntityData.Field<Boolean> FROM_BUCKET;

        static {
            VARIANT = new EntityData.Field<>(17, Serializer.INT);
            PLAYING_DEAD = new EntityData.Field<>(18, Serializer.BOOLEAN);
            FROM_BUCKET = new EntityData.Field<>(19, Serializer.BOOLEAN);
        }
    }
}
